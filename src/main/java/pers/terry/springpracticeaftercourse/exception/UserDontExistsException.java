package pers.terry.springpracticeaftercourse.exception;

public class UserDontExistsException extends Exception {
  private String message;

  public UserDontExistsException() {
    this.message = "用户不存在";
  }

  public UserDontExistsException(String msg) {
    this.message = msg;
  }

  public String getMessage() {
    return message;
  }
}
