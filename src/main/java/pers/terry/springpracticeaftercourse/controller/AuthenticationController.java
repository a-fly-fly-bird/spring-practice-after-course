package pers.terry.springpracticeaftercourse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.AuthenticationRequest;
import pers.terry.springpracticeaftercourse.dto.AuthenticationResponseDto;
import pers.terry.springpracticeaftercourse.dto.PasswordDto;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.entity.Result;
import pers.terry.springpracticeaftercourse.exception.UserDontExistsException;
import pers.terry.springpracticeaftercourse.exception.UserExistsException;
import pers.terry.springpracticeaftercourse.service.AuthenticationService;
import pers.terry.springpracticeaftercourse.service.JwtAuthService;
import pers.terry.springpracticeaftercourse.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "校验", description = "权限控制")
public class AuthenticationController {
  private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
  private final AuthenticationService authenticationService;
  private final JwtAuthService jwtAuthService;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

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
  public Result<AuthenticationResponseDto> authenticate(
      @RequestBody @Validated AuthenticationRequest request) {
    try {
      var user = this.authenticationService.authenticate(request);
      return Result.ok(AuthenticationResponseDto.builder().token(user.getToken()).build());
    } catch (UsernameNotFoundException e) {
      return Result.error("用户不存在");
    } catch (AuthenticationException e) {
      return Result.error("校验失败");
    }
  }

  @PutMapping("/reset")
  @ResponseBody
  @Operation(summary = "重置密码", security = @SecurityRequirement(name = "authScheme"))
  public Result<String> resetPassword(@RequestBody @Validated PasswordDto password) {
    try {
      this.authenticationService.resetPassword(password.getPassword());
      return Result.ok("密码重置成功");
    } catch (UserDontExistsException e) {
      e.printStackTrace();
      return Result.error("用户不存在");
    }
  }
}
