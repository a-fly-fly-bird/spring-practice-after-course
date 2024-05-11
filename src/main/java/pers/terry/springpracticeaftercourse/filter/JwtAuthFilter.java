package pers.terry.springpracticeaftercourse.filter;

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
import org.springframework.web.filter.OncePerRequestFilter;
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

  @SuppressWarnings("null")
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
    // 进行校验
    String token = authHeader.substring(7);
    String userEmail = jwtAuthService.extractUsername(token);
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      logger.info("JwtAuthFilter开始进行认证");
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      if (jwtAuthService.isTokenValid(token, userDetails)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
