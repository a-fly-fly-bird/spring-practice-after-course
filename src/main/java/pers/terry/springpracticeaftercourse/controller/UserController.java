package pers.terry.springpracticeaftercourse.controller;

import org.springframework.web.bind.annotation.*;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
import pers.terry.springpracticeaftercourse.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserReponseDto addUser(@RequestBody UserDto userDto){
        return this.userService.addUser(userDto);
    }

    @GetMapping("/{uuid}")
    public UserReponseDto addUser(@PathVariable UUID uuid){
        return this.userService.getUserById(uuid);
    }
}
