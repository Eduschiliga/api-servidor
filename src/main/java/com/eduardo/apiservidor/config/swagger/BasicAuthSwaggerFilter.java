package com.eduardo.apiservidor.config.swagger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Base64;

@Configuration
public class BasicAuthSwaggerFilter implements HandlerInterceptor {
  private static final String AUTH_HEADER = "Authorization";
  private static final String REALM = "Swagger API Docs";

  @Value("${swagger.username}")
  private String username;

  @Value("${swagger.password}")
  private String password;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String authHeader = request.getHeader(AUTH_HEADER);

    if (authHeader != null && authHeader.startsWith("Basic ")) {
      String base64Credentials = authHeader.substring(6);
      String credentials = new String(Base64.getDecoder().decode(base64Credentials));
      String[] values = credentials.split(":", 2);

      if (username != null && password != null && username.equals(values[0]) && password.equals(values[1])) {
        return true;
      }
    }

    response.setHeader("WWW-Authenticate", "Basic realm=\"" + REALM + "\"");
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    return false;
  }
}
