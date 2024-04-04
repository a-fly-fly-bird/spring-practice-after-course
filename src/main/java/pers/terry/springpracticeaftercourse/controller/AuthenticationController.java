package pers.terry.springpracticeaftercourse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.*;
import pers.terry.springpracticeaftercourse.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
  final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public UserReponseDto addUser(@RequestBody UserDto userDto) {
    logger.info("new user registered");
    return this.authenticationService.register(userDto);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(this.authenticationService.authenticate(request));
  }

  @GetMapping("/decode-token")
  public ResponseEntity<String> decodeToken(String token) {
    return ResponseEntity.ok(this.authenticationService.decodeToken(token));
  }

  @PutMapping("/reset")
  public ResponseEntity<String> resetPassword(@RequestBody PasswordDto password){
    return ResponseEntity.ok(this.authenticationService.resetPassword(password.getPassword()));
  }
}
