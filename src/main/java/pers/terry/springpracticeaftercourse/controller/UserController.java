package pers.terry.springpracticeaftercourse.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{uuid}")
    public UserReponseDto addUser(@PathVariable UUID uuid) {
        return this.userService.getUserById(uuid);
    }

    @GetMapping("/UserByEmail")
    public String findUserByEmail(String email) {
        return this.userService.findUserByEmail(email);
    }
}
