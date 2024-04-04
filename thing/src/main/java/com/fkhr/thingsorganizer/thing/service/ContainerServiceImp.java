package com.fkhr.thingsorganizer.thing.service;

import com.fkhr.thingsorganizer.common.exeptionhandling.CustomError;
import com.fkhr.thingsorganizer.common.exeptionhandling.CustomExeption;
import com.fkhr.thingsorganizer.thing.model.Container;
import com.fkhr.thingsorganizer.thing.repository.ContainerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class ContainerServiceImp implements ContainerService{

    private final ContainerRepository containerRepository;
    private final ThingService thingService;
    @Autowired
    public ContainerServiceImp(ContainerRepository containerRepository, ThingService thingService) {
        this.containerRepository = containerRepository;
        this.thingService = thingService;
    }

    @Override
    public Container save(Container container) {
        Container parentContainer = null;
        if(container.getParent() != null && container.getParent().getId() != null) {
            parentContainer = loadParent(container.getParent().getId());
        }
        Container result = null;
        if(container.getId() == null){
            result = containerRepository.save(container);
        }
        else {
            if (!exists(container.getId())) {
                throw new CustomExeption(CustomError.CONTAINER_NOT_FOUND, HttpStatus.NOT_FOUND);
            } else {
                result = containerRepository.save(container);
            }
        }
        if (result != null && parentContainer != null) {
            result.setParent(parentContainer);
        }
        return result;
    }

    @Override
    public void delete(Long id){
        Container container = load(id);
        if(container == null){
            throw new CustomExeption(CustomError.CONTAINER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        else{
            thingService.updateContainer(container.getId(), null);
            containerRepository.delete(container);
        }
    }

    @Override
    public Container load(Long id){
        Optional<Container> result = containerRepository.findById(id);
        if(result.equals(Optional.empty())) {
            throw new CustomExeption(CustomError.CONTAINER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        else {
            return result.get();
        }
    }

    private Container loadParent(Long parentId){
        Container result = containerRepository.findById(parentId).get();
        if(result == null){
            throw new  CustomExeption(CustomError.PARENT_CONTAINER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        else{
            return result;
        }
    }

    @Override
    public List<Container> findAll(){
        return containerRepository.findAll();
    }

    public List<Container> search(Container container, int page, int size){
        return null;
    }

    public boolean exists(Long id){
        Container container = load(id);
        if(container != null)
            return true;
        else
            return false;
    }
}
