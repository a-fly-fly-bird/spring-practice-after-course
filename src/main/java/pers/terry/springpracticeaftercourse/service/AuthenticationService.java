package pers.terry.springpracticeaftercourse.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.AuthenticationRequest;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final JwtAuthService jwtAuthService;
  private final UserService userService;

  public UserReponseDto register(UserDto userDto) {
    return this.userService.addUser(userDto);
  }

  public String authenticate(AuthenticationRequest request) {
    System.out.println("request is " + request.toString());
    // /如果认证失败会抛出异常
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.account(),
        request.password()));
    if (authentication.isAuthenticated()) {
      var token = jwtAuthService.generateToken(User.builder().username(request.account()).build());
      return token;
    } else {
      throw new UsernameNotFoundException("invalid user request..!!");
    }
  }
}
