package com.fkhr.thingsorganizer.thing.service;

import com.fkhr.thingsorganizer.common.exeptionhandling.CustomError;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomExeption;
import com.fkhr.thingsorganizer.common.util.EntityType;
import com.fkhr.thingsorganizer.thing.Enum.CacheLabel;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.model.Thing;
import com.fkhr.thingsorganizer.thing.proxy.ContentProxy;
import com.fkhr.thingsorganizer.thing.repository.ThingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThingServiceImpl implements ThingService {
    final private ThingRepository thingRepository;
    final private ContainerService containerService;
    final private ContentProxy contentProxy;
    @Autowired
    public ThingServiceImpl(ThingRepository thingRepository,
                            @Lazy ContainerService containerService,
                            ContentProxy contentProxy) {
        this.thingRepository = thingRepository;
        this.containerService = containerService;
        this.contentProxy = contentProxy;
    }

    @Override
    public Thing save(Thing thing) {
        Container container = null;
        if(thing.getContainer() != null && thing.getContainer().getId() != null) {
            container = containerService.load(thing.getContainer().getId());

        }
        Thing result = null;
        if(thing.getId() == null){
            result = thingRepository.save(thing);
        }
        else{
            Thing existingThing = this.load(thing.getId());
            if(existingThing == null){
                throw new CustomExeption(CustomError.THING_NOT_FOUND, HttpStatus.ACCEPTED);
            }
            else {
                result = thingRepository.save(thing);
            }
        }
        if(result != null && container != null){
            result.setContainer(container);
        }
        return result;
    }

    @Override
    @Transactional
    @Caching(
            evict = {@CacheEvict(cacheNames = "com.fkhr.thingsorganizer.thing.model.thing", key = "#id"),
                    @CacheEvict(cacheNames = "com.fkhr.thingsorganizer.thing.model.thingList", allEntries = true)
            }
    )
    public void delete(Long id){
        Thing thing = this.load(id);
        if(thing != null) {
            ResponseEntity deleteContentResponse = contentProxy.deleteContentByOwner(EntityType.THING, id.toString());

            if(deleteContentResponse.getStatusCode().value() == 200 ||
                    deleteContentResponse.getStatusCode().value() == 204) {
                thingRepository.delete(thing);
            }
            else{
                throw new CustomExeption(CustomError.THING_DELETE_CONTENT_NOT_DELETED, HttpStatus.ACCEPTED);
            }
        }
        else
            throw new CustomExeption(CustomError.THING_NOT_FOUND, HttpStatus.ACCEPTED);
    }

    @Override
    @Cacheable(value = "com.fkhr.thingsorganizer.thing.model.thing", key = "#id")
    public Thing load(Long id) {
        Optional<Thing> result = thingRepository.findById(id);
        if(result.equals(Optional.empty())){
            throw new CustomExeption(CustomError.THING_NOT_FOUND, HttpStatus.ACCEPTED);
        }else{
            return result.get();
        }

    }

    @Override
    @Cacheable(value = "com.fkhr.thingsorganizer.thing.model.thingList")
    public List<Thing> findAll(){
        return thingRepository.findAll();
    }
    @Override
    public List<Thing> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return thingRepository.findAll(pageable).toList();
    }

    @Override
    public boolean exists(Long id){
        Thing thing = load(id);
        if(thing != null)
            return true;
        else
            return false;
    }

    public List<Thing> search(Thing thingFilters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Thing> things = thingRepository.searchThings(
                thingFilters.getTitle(), thingFilters.getWeight(),
                thingFilters.getContainer() != null ? thingFilters.getContainer().getId() : null,
                thingFilters.getContainer() != null ? thingFilters.getContainer().getTitle() : null, pageable);
        return things;
    }
    @Transactional
    @Caching(
            evict = {@CacheEvict(cacheNames = "com.fkhr.thingsorganizer.thing.model.thing", allEntries = true),
                    @CacheEvict(cacheNames = "com.fkhr.thingsorganizer.thing.model.thingList", allEntries = true)
            }
    )
    public Integer updateContainer(Long oldContainerId, Long newContainerId){
        if(newContainerId == null || containerService.exists(newContainerId)) {
            return thingRepository.updateContainer(oldContainerId, newContainerId);
        }
        else{
            throw new CustomExeption(CustomError.CONTAINER_NOT_FOUND, HttpStatus.ACCEPTED);
        }
    }
}
