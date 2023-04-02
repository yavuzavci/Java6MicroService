package com.yavuz.utility;

import com.yavuz.manager.IElasticServiceManager;
import com.yavuz.repository.entity.UserProfile;
import com.yavuz.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestAndRun {
    private final UserProfileService userProfileService;
    private final IElasticServiceManager elasticServiceManager;
    @PostConstruct
    public void init(){
        /**
         * Bu kısım kullanılacak ise, zorunlu durumlar için işiniz bitince
         * bu kodu yorum satırına almak doğru olackatır.
         * Çalışması sistemi etkilemeyen durumlarda thread içinde çalıştırmak doğru olacaktır.
         */
        /*
        new Thread(() -> {
            run();
        });
        */
        //run();
    }

    public void run(){
        List<UserProfile> list = userProfileService.findAll();
        list.forEach(x -> {
            elasticServiceManager.addUser(x);
        });
    }
}
