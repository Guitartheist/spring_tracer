package com.example.demo.config;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.services.AuthService;

@Configurable
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Autowired
  private AuthService authService;

  @Autowired
  private JwtTokenFilter jwtTokenFilter;

  /**
   * Configures the authentication manager with the correct provider
   * @param auth allows us to configure the authentication manager with the correct userdetails
   * @throws Exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(authService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Enable CORS and disable CSRF
    http = http.cors().and().csrf().disable();
    http.headers().frameOptions().disable();

    // Set session management to stateless
    http =
      http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and();

    // Set unauthorized requests exception handler
    http =
      http
        .exceptionHandling()
        .authenticationEntryPoint(
          (request, response, ex) -> {
            response.sendError(
              HttpServletResponse.SC_UNAUTHORIZED,
              ex.getMessage()
            );
          }
        )
        .and();

    // Set permissions on endpoints
    http
      .authorizeRequests()
      // Public endpoints
      .antMatchers(
        "/h2/**",
        "/user/login",
        "/user/register",
        "/user/profile/**",
        "/image/*"
      )
      .permitAll()
      .anyRequest()
      .authenticated();

    // Add JWT token filter
    http.addFilterBefore(
      jwtTokenFilter,
      UsernamePasswordAuthenticationFilter.class
    );

    // Enable iframe rendering for H2 console
    http.headers().frameOptions().sameOrigin();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
