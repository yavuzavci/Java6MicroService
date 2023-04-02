package com.yavuz.manager;

import com.yavuz.repository.entity.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.yavuz.constants.EndPoints.SAVE;

@FeignClient(name = "elastic-service",url = "http://localhost:9101/elastic/user",decode404 = true)
public interface IElasticServiceManager {
    @PostMapping(SAVE)
    ResponseEntity<Void> addUser(@RequestBody UserProfile dto);
}
