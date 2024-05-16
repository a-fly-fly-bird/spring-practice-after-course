package pers.terry.springpracticeaftercourse.constants;

public class SecurityConstants {
  public static final String[] WHITE_LIST_URL = {
    "/auth/**",
    "/swagger-ui/**",
    "/webjars/**",
    "/swagger-ui.html",
    "/swagger-ui-custom.html",
    "/api-docs/**"
  };
}
