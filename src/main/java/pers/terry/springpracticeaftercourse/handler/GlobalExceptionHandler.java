package pers.terry.springpracticeaftercourse.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import pers.terry.springpracticeaftercourse.entity.Result;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = NullPointerException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public Result<String> nullPointerHandler(
      HttpServletRequest httpServletRequest, NullPointerException exception) {
    logger.error("发生空指针异常，原因是：" + exception.getMessage());
    return Result.error("发生空指针异常");
  }

  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Result<String> exceptionHandler(HttpServletRequest req, Exception e) {
    logger.error("未知异常！原因是:", e.fillInStackTrace());
    return Result.error("未知错误" + e.fillInStackTrace());
  }
}
