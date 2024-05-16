package pers.terry.springpracticeaftercourse.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.terry.springpracticeaftercourse.constants.SecurityConstants;
import pers.terry.springpracticeaftercourse.controller.AuthenticationController;
import pers.terry.springpracticeaftercourse.service.JwtAuthService;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private static final String AUTH_HEADER = "Authorization";
  private static final String AUTH_HEADER_TYPE = "Bearer ";

  private final JwtAuthService jwtAuthService;
  private final UserDetailsService userDetailsService;

  private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    logger.info("进入Filter");
    final String authHeader = request.getHeader(AUTH_HEADER);

    // 如果没有Token,直接放行到PasswordFilter校验失败
    if (Objects.isNull(authHeader) || !authHeader.startsWith(AUTH_HEADER_TYPE)) {
      logger.info("没有Token");
      filterChain.doFilter(request, response);
      return;
    }

    String requestURI = request.getRequestURI();
    // 如果请求的URL在白名单中，直接跳过过滤器
    if (isWhiteListed(requestURI)) {
      filterChain.doFilter(request, response);
      return;
    }

    // 进行校验
    String token = authHeader.substring(7);
    String userEmail = null;
    try {
      userEmail = jwtAuthService.extractUsername(token);
    } catch (ExpiredJwtException e) {
      logger.error("JWT已过期", e);
      // 这里可以根据需求返回特定的错误响应，也可以选择放行
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT已过期");
      return;
    }

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      try {
        logger.info("JwtAuthFilter开始进行认证");
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        if (jwtAuthService.isTokenValid(token, userDetails)) {
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails.getUsername(),
                  userDetails.getPassword(),
                  userDetails.getAuthorities());

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      } catch (ExpiredJwtException e) {
        logger.error("JWT已过期", e);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT已过期");
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private boolean isWhiteListed(String requestURI) {
    for (String pattern : SecurityConstants.WHITE_LIST_URL) {
      if (pathMatcher.match(pattern, requestURI)) {
        return true;
      }
    }
    return false;
  }
}
