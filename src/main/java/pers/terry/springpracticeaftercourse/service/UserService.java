package pers.terry.springpracticeaftercourse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.controller.AuthenticationController;
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
    final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
    public UserReponseDto addUser(UserDto userDto) {
        if(userRepository.existsByEmail(userDto.email())){
            logger.warn("用户已经存在了，不可以重复注册");
            return null;
        }
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
        Optional<User> user = this.userRepository.findByEmail(username);
        // 终于找到你了🥹。花了一下午
        // 代码不规范，亲人两行泪啊
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("没有找到该用户");
        }
        return user.get();
    }

    @Cacheable(value = "db0", key = "email")
    public String findUserByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.get().getEmail();
    }

    public void resetPassword(String username, String encryptedPassword) {
        Optional<User> userOptional = this.userRepository.findByEmail(username);
        if (userOptional.isPresent()){
            var user = userOptional.get();
            user.setPassword(encryptedPassword);
            this.userRepository.save(user);
        }
    }
}