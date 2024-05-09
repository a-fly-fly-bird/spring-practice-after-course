package pers.terry.springpracticeaftercourse.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.AuthenticationRequest;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.entity.User;
import pers.terry.springpracticeaftercourse.exception.UserDontExistsException;
import pers.terry.springpracticeaftercourse.exception.UserExistsException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtAuthService jwtAuthService;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  public UserReponseDto register(UserDto userDto) throws UserExistsException {
    return this.userService.addUser(userDto);
  }

  public User authenticate(AuthenticationRequest request) throws AuthenticationException {
    // /如果认证失败会抛出异常
    var authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.account(), request.password()));
    if (authentication.isAuthenticated()) {
      var user = User.builder()
          .username(request.account())
          .email(request.account())
          .password(request.password())
          .build();
      user.setToken(this.jwtAuthService.generateToken(user));
      return user;
    } else {
      throw new BadCredentialsException("null");
    }
  }

  public User resetPassword(String password) throws UserDontExistsException {
    var encryptedPassword = this.passwordEncoder.encode(password);
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return this.userService.resetPassword(username, encryptedPassword);
  }
}
