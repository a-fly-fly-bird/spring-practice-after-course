package pers.terry.springpracticeaftercourse.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.terry.springpracticeaftercourse.dto.UserQueryDTO;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
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

  // TODO 接口不能设计为根据Token来判断用户，因为ADMIN会有权限查看其他用户
  @GetMapping("/")
  public UserReponseDto findUserInfo(@RequestBody @Validated UserQueryDTO userQueryDTO) {
    logger.info("查找用户中");
    return this.userService
        .getUserByAccount(userQueryDTO.account())
        .map(UserReponseDto::from)
        .orElse(null);
  }

  @DeleteMapping("/")
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
}
