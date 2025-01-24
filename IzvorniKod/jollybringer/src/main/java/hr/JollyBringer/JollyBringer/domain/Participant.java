package hr.JollyBringer.JollyBringer.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Participant  {
    @Id
    @GeneratedValue
    private Long id;


    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role_id;

    public Participant(String username,  String email, Role role) {
        Assert.hasText(username, "Username must have text");
        Assert.notNull(email, "E-mail must be set");
        Assert.notNull(email, "Role must be set");
        this.username = username;
        this.email = email;
        this.role_id= role;
        //this.role = new Role();
    }

    public Participant() {

    }

    public Role getRole() {
        return role_id;
    }

    public void setRole(Role role) {
        this.role_id = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPresident() {
        return role_id.getId() == 2;
    }

    public boolean isAdmin() {
        return role_id.getId() == 3;
    }
/*
    @Override
    public Map<String, Object> getAttributes() {
         Map<String, Object> attributes;


           attributes = new HashMap<>();
            attributes.put("id", this.getId());
            attributes.put("username", this.getUsername());
           attributes.put("email", this.getEmail());
            attributes.put("role_id", this.getRole().getId());

        return attributes;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch ((int) role_id.getId()) {
            case 1:
                return AuthorityUtils.createAuthorityList("ROLE_PARTICIPANT");
            case 2:
                return AuthorityUtils.createAuthorityList("ROLE_LEAD");
            case 3:
                return AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        }

        return AuthorityUtils.createAuthorityList("ROLE_PARTICIPANT");
    }

    @Override
    public String getName() {
        return "";
    }
    */

}
