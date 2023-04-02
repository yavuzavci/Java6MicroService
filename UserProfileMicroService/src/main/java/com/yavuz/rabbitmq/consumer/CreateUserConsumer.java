package com.yavuz.rabbitmq.consumer;

import com.yavuz.rabbitmq.model.SaveAuthModel;
import com.yavuz.repository.entity.UserProfile;
import com.yavuz.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserConsumer {
    private final UserProfileService userProfileService;
    @RabbitListener(queues = "queue-auth")
    public void createUserFromHandleQueue(SaveAuthModel model){
        System.out.println("Gelen Data...: " + model.getUsername());
        userProfileService.saveRabbit(model);
    }
}
