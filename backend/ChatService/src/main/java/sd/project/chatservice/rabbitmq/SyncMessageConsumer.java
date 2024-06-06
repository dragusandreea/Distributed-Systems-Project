package sd.project.chatservice.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import sd.project.chatservice.dtos.SyncDto;
import sd.project.chatservice.dtos.UserDto;
import sd.project.chatservice.entities.User;
import sd.project.chatservice.services.UserService;

import static sd.project.chatservice.utils.SyncMessages.*;


@Service
public class SyncMessageConsumer {
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public SyncMessageConsumer(ObjectMapper objectMapper, ModelMapper modelMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @RabbitListener(queues = {"${rabbitmq.sync.queue.name}"})
    public void consume(String message) throws JsonProcessingException {
        String syncMessage = messageProcessing(message);
        System.out.println(syncMessage);
    }

    private String messageProcessing(String message) throws JsonProcessingException {
        SyncDto syncDto = objectMapper.readValue(message, SyncDto.class);
        UserDto userDto = syncDto.getUserDto();
        String methodType = syncDto.getMethodType();
        if(methodType.equals("CREATE")) {
            User user = modelMapper.map(userDto, User.class);
            User savedUser = userService.add(user);
            return CREATE_MESSAGE.formatted(savedUser);
        }
        if(methodType.equals("UPDATE")) {
            User user = modelMapper.map(userDto, User.class);
            User updatedUser = userService.update(user);
            return UPDATE_MESSAGE.formatted(updatedUser);
        }
        if(methodType.equals("DELETE_BY_ID")) {
            User user = modelMapper.map(userDto, User.class);
            userService.deleteById(user.getId());
            return DELETE_BY_ID_MESSAGE.formatted(user.getId());
        }
        return null;
    }
}
