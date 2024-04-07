package pers.terry.springpracticeaftercourse.handler;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomUnauthorizedHandler implements AuthenticationEntryPoint {
  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {
    response.setContentType("application/json;charset=utf-8");
    response.setStatus(HttpStatus.HTTP_ACCEPTED);
    Map<String, String> resultData = new HashMap<>();
    PrintWriter out = response.getWriter();
    // 直接提示前端认证错误
    resultData.put("msg", "自定义认证：认证错误");
    out.write(JSONUtil.toJsonStr(resultData));
    out.flush();
    out.close();
  }
}
