package pers.terry.springpracticeaftercourse.service;

import org.springframework.stereotype.Service;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.entity.User;
import pers.terry.springpracticeaftercourse.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserReponseDto addUser(UserDto userDto) {
        User user = this.toUser(userDto);
        this.userRepository.save(user);
        return toUserResponseDto(user);
    }

    private UserReponseDto toUserResponseDto(User user) {
        return UserReponseDto.builder().name(user.getName()).email(user.getEmail()).build();
    }

    private User toUser(UserDto userDto) {
        return User.builder().name(userDto.name()).email(userDto.email()).age(userDto.age()).password(userDto.password()).build();
    }
}
