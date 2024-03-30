package pers.terry.springpracticeaftercourse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.entity.User;
import pers.terry.springpracticeaftercourse.entity.UserRole;
import pers.terry.springpracticeaftercourse.enums.UserRoleEnum;
import pers.terry.springpracticeaftercourse.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @SuppressWarnings("null")
    public UserReponseDto addUser(UserDto userDto) {
        User user = this.toUser(userDto);
        System.out.println(user);
        user = this.userRepository.save(user);
        // System.out.println(user.toString());
        return toUserResponseDto(user);
    }

    private UserReponseDto toUserResponseDto(User user) {
        return UserReponseDto.builder().name(user.getName()).email(user.getEmail()).build();
    }

    private User toUser(UserDto userDto) {
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.builder().role(UserRoleEnum.USER).build());
        return User.builder().name(userDto.name()).email(userDto.email()).age(userDto.age())
                .password(userDto.password()).userRoles(userRoles).build();
    }

    public UserReponseDto getUserById(UUID uuid) {
        Optional<User> user = this.userRepository.findById(uuid);
        return user.map(this::toUserResponseDto).orElse(null);
    }
}
