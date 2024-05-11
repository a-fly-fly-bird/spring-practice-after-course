package pers.terry.springpracticeaftercourse.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    logger.info("开始校验");
    String username = String.valueOf(authentication.getPrincipal());
    String password = String.valueOf(authentication.getCredentials());

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    try {
      if (passwordEncoder.matches(password, userDetails.getPassword())) {
        logger.info("校验成功");
        return new UsernamePasswordAuthenticationToken(username, password,
            userDetails.getAuthorities());
      }
    } catch (BadCredentialsException e) {
      logger.info("校验失败");
      throw new BadCredentialsException("BadCredentialsException Error!!");
    } catch (Exception e) {
      logger.info("校验失败");
      System.out.println(e.getMessage());
      throw e;
    }
    return authentication;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.equals(authentication);
  }
}