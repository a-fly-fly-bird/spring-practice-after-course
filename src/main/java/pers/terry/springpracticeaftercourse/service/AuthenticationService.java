package pers.terry.springpracticeaftercourse.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.AuthenticationRequest;
import pers.terry.springpracticeaftercourse.dto.AuthenticationResponseDto;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.entity.User;

import java.util.Optional;

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

  public Optional<AuthenticationResponseDto> authenticate(AuthenticationRequest request) {
    // /如果认证失败会抛出异常
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.account(),
            request.password()));
    if (authentication.isAuthenticated()) {
      var token = jwtAuthService.generateToken(User.builder().username(request.account()).email(
              request.account()).build());
      return Optional.of(AuthenticationResponseDto.builder().token(token).build());
    } else {
      return Optional.empty();
    }
  }

  public String decodeToken(String token) {
    return this.jwtAuthService.extractAllClaims(token).toString();
  }

  public String resetPassword(String password) {
    var encryptedPassword = this.passwordEncoder.encode(password);
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    this.userService.resetPassword(username, encryptedPassword);
    return "success";
  }
}
