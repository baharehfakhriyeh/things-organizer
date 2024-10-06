package com.fkhr.thingsorganizer.thing.proxy;

import com.fkhr.thingsorganizer.common.util.EntityType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "content", url = "http://localhost:9092/content/api")
public interface ContentProxy {
    @DeleteMapping(value = "/content/ownertype/{ownertype}/ownerid/{ownerid}")
    ResponseEntity deleteContentByOwner(@PathVariable("ownertype") EntityType ownerType,
                                               @PathVariable("ownerid") String ownerId);
}