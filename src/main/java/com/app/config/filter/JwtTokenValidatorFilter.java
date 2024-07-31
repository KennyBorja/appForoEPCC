package com.app.config.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.resources.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidatorFilter extends OncePerRequestFilter{

  private JwtUtil jwtUtil;

  public JwtTokenValidatorFilter (JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String tokenRequest = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (tokenRequest != null) {
      System.out.println("entro al filtro");
      tokenRequest = tokenRequest.substring(7);
      System.out.println("el token es"+tokenRequest);

      DecodedJWT decodedJWT = jwtUtil.validateJWT(tokenRequest);

      String username = jwtUtil.extractUsername(decodedJWT);

      String authoritiesString = jwtUtil.getSpecificClaim(decodedJWT, "authorities").asString();
      System.out.println("22");
      Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesString);

      SecurityContext context = SecurityContextHolder.getContext();
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);

      context.setAuthentication(authentication);
      SecurityContextHolder.setContext(context);
      System.out.println("33");
    }
    filterChain.doFilter(request, response);
    
  }

}