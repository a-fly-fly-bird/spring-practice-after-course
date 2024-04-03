package pers.terry.springpracticeaftercourse.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pers.terry.springpracticeaftercourse.dto.AuthenticationRequest;
import pers.terry.springpracticeaftercourse.dto.AuthenticationResponseDto;
import pers.terry.springpracticeaftercourse.dto.UserDto;
import pers.terry.springpracticeaftercourse.dto.UserReponseDto;
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
  public ResponseEntity<String> resetPassword(@RequestBody String password){
    return ResponseEntity.ok(this.authenticationService.resetPassword(password));
  }
}
