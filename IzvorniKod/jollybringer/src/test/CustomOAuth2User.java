package hr.JollyBringer.JollyBringer.rest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {
    private final OAuth2User delegate;
    private final Collection<GrantedAuthority> authorities;

    public CustomOAuth2User(OAuth2User delegate, Collection<GrantedAuthority> authorities) {
        this.delegate = delegate;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return delegate.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return delegate.getAttribute("name");
    }
}

