package pers.terry.springpracticeaftercourse.exception;

public class UserExistsException extends Exception {
  private String message;

  public UserExistsException() {
    this.message = "用户已经存在";
  }

  public UserExistsException(String msg) {
    this.message = msg;
  }

  public String getMessage() {
    return message;
  }
}
