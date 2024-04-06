package pers.terry.springpracticeaftercourse.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.terry.springpracticeaftercourse.dto.*;
import pers.terry.springpracticeaftercourse.entity.User;
import pers.terry.springpracticeaftercourse.service.AuthenticationService;
import pers.terry.springpracticeaftercourse.service.JwtAuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
  final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
  private final AuthenticationService authenticationService;
  private final JwtAuthService jwtAuthService;

  /**
   * 注册新用户
   *
   * @param userDto
   * @return 注册结果
   */
  @PostMapping("/register")
  public ResponseEntity<String> addUser(@RequestBody @Validated UserDto userDto) {
    Optional<UserReponseDto> userReponseDtoOptional = this.authenticationService.register(userDto);
    String result = userReponseDtoOptional.map(value -> "创建成功").orElse("已经存在");
    return ResponseEntity.ok(result);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> authenticate(
      @RequestBody @Validated AuthenticationRequest request) {
    //    return
    // this.authenticationService.authenticate(request).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    return this.authenticationService
        .authenticate(request)
        .map(
            user -> {
              var token =
                  jwtAuthService.generateToken(
                      User.builder().username(request.account()).email(request.account()).build());
              return ResponseEntity.ok(AuthenticationResponseDto.builder().token(token).build());
            })
        .orElse(ResponseEntity.badRequest().build());
  }

  @PutMapping("/reset")
  public ResponseEntity<String> resetPassword(@RequestBody @Validated PasswordDto password) {
    return this.authenticationService
        .resetPassword(password.getPassword())
        .map(user -> ResponseEntity.ok("重置成功"))
        .orElse(ResponseEntity.badRequest().build());
  }
}
