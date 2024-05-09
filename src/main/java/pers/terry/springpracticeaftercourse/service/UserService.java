package pers.terry.springpracticeaftercourse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
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
import pers.terry.springpracticeaftercourse.exception.UserDontExistsException;
import pers.terry.springpracticeaftercourse.exception.UserExistsException;
import pers.terry.springpracticeaftercourse.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final JwtAuthService jwtAuthService;
  final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

  public UserReponseDto addUser(UserDto userDto) throws UserExistsException {
    if (userRepository.existsByEmail(userDto.email())) {
      logger.warn("用户已经存在了，不可以重复注册");
      throw new UserExistsException();
    }
    User user = this.toUser(userDto);
    var password = new BCryptPasswordEncoder().encode(userDto.password());
    user.setPassword(password);
    user.setToken(this.jwtAuthService.generateToken(user));
    user = this.userRepository.save(user);
    return toUserResponseDto(user);
  }

  private UserReponseDto toUserResponseDto(User user) {
    return UserReponseDto.builder().name(user.getUsername()).email(user.getEmail()).build();
  }

  private User toUser(UserDto userDto) {
    User user = User.builder()
        .username(userDto.name())
        .email(userDto.email())
        .age(userDto.age())
        .password(userDto.password())
        .build();
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

  public User resetPassword(String username, String encryptedPassword) throws UserDontExistsException {
    Optional<User> userOptional = this.userRepository.findByEmail(username);
    userOptional.ifPresent(
        user -> {
          user.setPassword(encryptedPassword);
          this.userRepository.save(user);
        });
    return userOptional.orElseThrow(() -> new UserDontExistsException());
  }

  public Optional<User> getUserByAccount(String account) {
    return this.userRepository.findByEmail(account);
  }

  public Optional<User> deleteUser(String account) {
    Optional<User> userOptional = this.userRepository.findByEmail(account);
    return userOptional
        .map(
            user -> {
              user.setIsDeleted(true);
              return user;
            })
        .map(this.userRepository::saveAndFlush);
  }

  public Optional<User> updateAccount(UserDto userDto) {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    var user = this.userRepository.findByEmail(username);
    return user.map(user1 -> {
      if (StringUtils.isNotBlank(userDto.email())) {
        user1.setEmail(userDto.email());
      }
      if (StringUtils.isNotBlank(userDto.name())) {
        user1.setUsername(userDto.name());
      }
      return user1;
    });
  }
}
