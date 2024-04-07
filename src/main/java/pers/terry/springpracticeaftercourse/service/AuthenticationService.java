package pers.terry.springpracticeaftercourse.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pers.terry.springpracticeaftercourse.dto.AuthenticationRequest;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.entity.User;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtAuthService jwtAuthService;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public Optional<UserReponseDto> register(UserDto userDto) {
    return this.userService.addUser(userDto);
  }

  public Optional<User> authenticate(AuthenticationRequest request) {
    // /如果认证失败会抛出异常
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.account(), request.password()));
    if (authentication.isAuthenticated()) {
      var user =
          User.builder()
              .username(request.account())
              .email(request.account())
              .password(request.password())
              .build();
      user.setToken(this.jwtAuthService.generateToken(user));
      return Optional.of(user);
    } else {
      return Optional.empty();
    }
  }

  public Optional<User> resetPassword(String password) {
    var encryptedPassword = this.passwordEncoder.encode(password);
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return this.userService.resetPassword(username, encryptedPassword);
  }
}
