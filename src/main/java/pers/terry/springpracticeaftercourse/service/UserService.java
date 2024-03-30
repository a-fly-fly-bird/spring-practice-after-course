package pers.terry.springpracticeaftercourse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.entity.User;
import pers.terry.springpracticeaftercourse.entity.UserRole;
import pers.terry.springpracticeaftercourse.enums.UserRoleEnum;
import pers.terry.springpracticeaftercourse.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserReponseDto addUser(UserDto userDto) {
        User user = this.toUser(userDto);
        var password = new BCryptPasswordEncoder().encode(userDto.password());
        user.setPassword(password);
        user = this.userRepository.save(user);
        return toUserResponseDto(user);
    }

    private UserReponseDto toUserResponseDto(User user) {
        return UserReponseDto.builder().name(user.getUsername()).email(user.getEmail()).build();
    }

    private User toUser(UserDto userDto) {
        User user = User.builder().username(userDto.name()).email(userDto.email()).age(userDto.age())
                .password(userDto.password()).build();
        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.builder().role(UserRoleEnum.USER).user(user).build());
        user.setUserRoles(userRoles);
        return user;
    }

    public UserReponseDto getUserById(UUID uuid) {
        Optional<User> user = this.userRepository.findById(uuid);
        return user.map(this::toUserResponseDto).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username is " + username);
        Optional<User> user = this.userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("没有找到该用户");
        }
        return user.get();
    }
}
