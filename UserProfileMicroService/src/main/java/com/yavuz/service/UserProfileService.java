package com.yavuz.service;

import com.yavuz.dto.request.UserProfileSaveRequestDto;
import com.yavuz.manager.IElasticServiceManager;
import com.yavuz.mapper.IUserProfileMapper;
import com.yavuz.rabbitmq.model.SaveAuthModel;
import com.yavuz.repository.IUserProfileRepository;
import com.yavuz.repository.entity.UserProfile;
import com.yavuz.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {
    private final IUserProfileRepository repository;
    private final IElasticServiceManager elasticServiceManager;

    public UserProfileService(IUserProfileRepository repository,
                              IElasticServiceManager elasticServiceManager) {
        super(repository);
        this.repository = repository;
        this.elasticServiceManager = elasticServiceManager;
    }

    public Boolean saveDto(UserProfileSaveRequestDto dto) {
        save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
        return true;
    }

    public void saveRabbit(SaveAuthModel model){
        UserProfile profile = IUserProfileMapper.INSTANCE.toUserProfile(model);
        save(profile);
        //elasticServiceManager.addUser(profile);
    }

    /**
     * Bu uzun süren bir işlemi simüle etmek için Thread kullanılarak
     * bekletilmiş bir method tur.
     * @param name
     * @return
     */

    @Cacheable(value = "getUpperName")
    public String getUpper(String name){
        try {
            Thread.sleep(3000L);
        } catch (Exception e){

        }
        return name.toUpperCase();
    }

    @CacheEvict(value = "getUpperName", allEntries = true)
    public void clearCache(){
        System.out.println("Tüm kayıtları temizledik");
    }
}
