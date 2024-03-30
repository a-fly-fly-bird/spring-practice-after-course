package pers.terry.springpracticeaftercourse.handler;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomUnauthorizedHandler implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {
    response.setContentType("application/json;charset=utf-8");
    PrintWriter out = response.getWriter();
    // 直接提示前端认证错误
    out.write("自定义认证：认证错误");
    out.flush();
    out.close();
  }
}