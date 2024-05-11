package pers.terry.springpracticeaftercourse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserQueryDTO;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.service.JwtAuthService;
import pers.terry.springpracticeaftercourse.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
/**
 * @@see get：获取用户信息 post：创建用户信息 put：更新用户信息 delete：删除用户信息
 */
public class UserController {
  final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;
  private final JwtAuthService jwtAuthService;

  @GetMapping("/current")
  @Operation(summary = "查找用户", security = @SecurityRequirement(name = "authScheme"))
  public UserReponseDto findCurrentUserInfo() {
    logger.info("查找用户中");
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return this.userService.getUserByAccount(username).map(UserReponseDto::from).orElse(null);
  }

  // TODO 接口不能设计为根据Token来判断用户，因为ADMIN会有权限查看其他用户
  @GetMapping("/")
  @Operation(summary = "查找用户", security = @SecurityRequirement(name = "authScheme"))
  public UserReponseDto findUserInfo(@RequestBody UserQueryDTO userQueryDTO) {
    logger.info("查找用户中");
    var username = userQueryDTO.account();
    return this.userService.getUserByAccount(username).map(UserReponseDto::from).orElse(null);
  }

  @GetMapping("/detail")
  @Operation(summary = "查看用户细节", security = @SecurityRequirement(name = "authScheme"))
  public UserReponseDto findUserDetailInfo(@RequestBody UserQueryDTO userQueryDTO) {
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    var user = this.userService.getUserByAccount(username);
    user.ifPresent(
        user1 -> {
          var token = this.jwtAuthService.generateToken(user1);
          user1.setToken(token);
        });
    return this.userService.getUserByAccount(username).map(UserReponseDto::from).orElse(null);
  }

  @DeleteMapping("/")
  @Operation(summary = "删除用户", security = @SecurityRequirement(name = "authScheme"))
  public ResponseEntity<UserReponseDto> deleteAccount(
      @RequestBody @Validated UserQueryDTO userQueryDTO) {
    logger.info("删除用户中");
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (username.equals(userQueryDTO.account())) {
      return this.userService
          .deleteUser(userQueryDTO.account())
          .map(UserReponseDto::from)
          .map(ResponseEntity::ok)
          .orElse(null);
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/")
  @Operation(summary = "更新用户", security = @SecurityRequirement(name = "authScheme"))
  public UserReponseDto updateAccount(@RequestBody @Validated UserDto userDto) {
    return this.userService.updateAccount(userDto).map(UserReponseDto::from).orElse(null);
  }
}
