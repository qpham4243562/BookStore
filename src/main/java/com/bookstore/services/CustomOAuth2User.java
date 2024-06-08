package com.bookstore.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final Map<String, Object> attributes;
    private final String role;

    public CustomOAuth2User(Map<String, Object> attributes, String role) {
        this.attributes = attributes;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + role);
    }
}