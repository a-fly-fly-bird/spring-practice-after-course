package pers.terry.springpracticeaftercourse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.AuthenticationRequest;
import pers.terry.springpracticeaftercourse.dto.AuthenticationResponseDto;
import pers.terry.springpracticeaftercourse.dto.PasswordDto;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.entity.Result;
import pers.terry.springpracticeaftercourse.entity.User;
import pers.terry.springpracticeaftercourse.exception.UserDontExistsException;
import pers.terry.springpracticeaftercourse.exception.UserExistsException;
import pers.terry.springpracticeaftercourse.service.AuthenticationService;
import pers.terry.springpracticeaftercourse.service.JwtAuthService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "校验", description = "权限控制")
public class AuthenticationController {
  private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
  private final AuthenticationService authenticationService;
  private final JwtAuthService jwtAuthService;

  /**
   * 注册新用户
   *
   * @return 注册结果
   */
  @PostMapping("/register")
  @ResponseBody
  public Result<UserReponseDto> addUser(@RequestBody @Validated UserDto userDto) {
    try {
      UserReponseDto userReponseDto = this.authenticationService.register(userDto);
      return Result.ok(userReponseDto);
    } catch (UserExistsException userExistsException) {
      return Result.error("用户已经存在，不能重复注册");
    }
  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<AuthenticationResponseDto> authenticate(
      @RequestBody @Validated AuthenticationRequest request) {
    try {
      var token = jwtAuthService.generateToken(
          User.builder().username(request.account()).email(request.account()).build());
      return ResponseEntity.ok(AuthenticationResponseDto.builder().token(token).build());
    } catch (AuthenticationException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/reset")
  @ResponseBody
  public Result<String> resetPassword(@RequestBody @Validated PasswordDto password) {
    try {
      this.authenticationService.resetPassword(password.getPassword());
      return Result.ok("Success");
    } catch (UserDontExistsException e) {
      e.printStackTrace();
      return Result.error("Failed");
    }

  }

  // The @Hidden annotation on exception handler methods, is considered when
  // building generic
  // (error) responses from @ControllerAdvice exception handlers.
  @Operation(summary = "空指针测试")
  @GetMapping("/nullPointerTest")
  public String nullPointerTest() {
    throw new NullPointerException("空指针错误测试");
  }

  @GetMapping("/exceptionTest")
  public String exceptionTest() {
    throw new NullPointerException("错误测试");
  }
}
