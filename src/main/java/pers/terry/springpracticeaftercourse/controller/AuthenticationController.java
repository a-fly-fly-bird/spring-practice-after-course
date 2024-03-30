package pers.terry.springpracticeaftercourse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  public UserReponseDto addUser(@RequestBody UserDto userDto) {
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

}
