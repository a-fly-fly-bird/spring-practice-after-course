package pers.terry.springpracticeaftercourse.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
/**
 * @@see
 * get：获取用户信息
 * post：创建用户信息
 * put：更新用户信息
 * delete：删除用户信息
 */
public class UserController {
    final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping("/{uuid}")
    public UserReponseDto addUser(@PathVariable UUID uuid) {
        return this.userService.getUserById(uuid);
    }

    @GetMapping("/UserByEmail")
    public String findUserByEmail(String email) {
        logger.info("hello world");
        return this.userService.findUserByEmail(email);
    }
}
