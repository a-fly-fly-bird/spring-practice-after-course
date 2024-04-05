package pers.terry.springpracticeaftercourse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.*;
import pers.terry.springpracticeaftercourse.service.AuthenticationService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
  final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
  private final AuthenticationService authenticationService;


  /**
   * 注册新用户
   * @param userDto
   * @return 注册结果
   */
  @PostMapping("/register")
  public ResponseEntity<String> addUser(@RequestBody @Validated UserDto userDto) {
    Optional<UserReponseDto> userReponseDtoOptional = this.authenticationService.register(userDto);
    String result = userReponseDtoOptional.map(value -> "创建成功")
            .orElse("已经存在");
    return ResponseEntity.ok(result);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody @Validated AuthenticationRequest request) {
    return this.authenticationService.authenticate(request).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
  }

  @GetMapping("/decode-token")
  public ResponseEntity<String> decodeToken(String token) {
    return ResponseEntity.ok(this.authenticationService.decodeToken(token));
  }

  @PutMapping("/reset")
  public ResponseEntity<String> resetPassword(@RequestBody @Validated PasswordDto password){
    return ResponseEntity.ok(this.authenticationService.resetPassword(password.getPassword()));
  }

}
