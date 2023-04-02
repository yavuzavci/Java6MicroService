package com.yavuz.manager;

import com.yavuz.dto.request.UserProfileSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.yavuz.constants.EndPoints.SAVE;

/**
 * Her feignclient için benzersiz isim vermelisiniz.
 * Burada kullanılan url, istek atılacak olan sınıfın requestmapping adresi olmalı.
 */
@FeignClient(name = "user-profile-manager",
             url = "http://localhost:9093/user",
             decode404 = true)
public interface IUserProfileManager {
    @PostMapping(SAVE)
    ResponseEntity<Boolean> save(@RequestBody UserProfileSaveRequestDto dto);
}
