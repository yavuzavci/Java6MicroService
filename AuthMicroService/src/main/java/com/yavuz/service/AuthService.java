package com.yavuz.service;

import com.yavuz.dto.request.DoLoginRequestDto;
import com.yavuz.dto.request.RegisterRequestDto;
import com.yavuz.dto.request.UserProfileSaveRequestDto;
import com.yavuz.exception.AuthServiceException;
import com.yavuz.exception.EErrorType;
import com.yavuz.manager.IUserProfileManager;
import com.yavuz.mapper.IAuthMapper;
import com.yavuz.rabbitmq.model.SaveAuthModel;
import com.yavuz.rabbitmq.producer.CreateUserProducer;
import com.yavuz.repository.IAuthRepository;
import com.yavuz.repository.entity.Auth;
import com.yavuz.utility.JwtTokenManager;
import com.yavuz.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository repository;
    private final JwtTokenManager tokenManager;
    private final CreateUserProducer createUserProducer;
    private final IUserProfileManager iUserProfileManager;
    public AuthService(IAuthRepository repository, JwtTokenManager tokenManager,
                       IUserProfileManager iUserProfileManager, CreateUserProducer createUserProducer) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.iUserProfileManager = iUserProfileManager;
        this.createUserProducer = createUserProducer;
    }

    public Optional<Auth> findOptionalByUsernameAndPassword(String username, String password){
        return repository.findOptionalByUsernameAndPassword(username,password);
    }

    public Auth register(RegisterRequestDto dto){
        if(repository.isUsername(dto.getUsername()))
            throw new AuthServiceException(EErrorType.REGISTER_ERROR_USERNAME);
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        /**
         * Repo -> repository.save(auth) bu bana kaydettiği entity i döner
         * Service -> save(auth) bana kaydettiği entity i döner
         * direkt -> bir şekilde kayıt edilen entity nin bilgileri işlenir ve bunu döner
         */
        save(auth);
        createUserProducer.convertAndSend(SaveAuthModel.builder()
                        .authid(auth.getId())
                        .email(auth.getEmail())
                        .username(auth.getUsername())
                .build());
        //iUserProfileManager.save(IAuthMapper.INSTANCE.fromAuth(auth));
        return auth;
    }

    /**
     * Kullanıcıyı doğrulayacak ve kullanıcının sistem içinde hareket edebilmesi için
     * ona özel bir kimlik verecek.
     * Token -> JWT Token
     * @param dto
     * @return
     */
    public String doLogin(DoLoginRequestDto dto) {
        Optional<Auth> auth = repository.findOptionalByUsernameAndPassword(dto.getUsername(),dto.getPassword());
        if(auth.isEmpty()) throw new AuthServiceException(EErrorType.LOGIN_ERROR_USERNAME_PASSWORD);
        return tokenManager.createToken(auth.get().getId()).get();
    }

    public List<Auth> findAll(String token){
        Optional<Long> id = tokenManager.getByIdFromToken(token);
        if(id.isEmpty())
            throw new AuthServiceException(EErrorType.INVALID_TOKEN);
        if(findById(id.get()).isEmpty())
            throw new AuthServiceException(EErrorType.INVALID_TOKEN);
        return findAll();
    }
}
