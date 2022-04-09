package com.example.demo.config;

import org.springframework.security.core.GrantedAuthority;

/**
 * An enum that implements the GrantedAuthority interface for
 * use of roles
 */
public enum Role implements GrantedAuthority {
  ADMIN("ROLE_ADMIN"),
  USER("ROLE_CUSTOMER");

  private final String role;

  Role(String s) {
    role = s;
  }

  @Override
  public String getAuthority() {
    return role;
  }
}
