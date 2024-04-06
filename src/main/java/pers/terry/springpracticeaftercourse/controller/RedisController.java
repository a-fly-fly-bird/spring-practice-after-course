package pers.terry.springpracticeaftercourse.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

  private final RedisTemplate<String, String> redisTemplate;

  // Just for test
  @GetMapping("/save")
  public String save(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
    return "saved";
  }
}
