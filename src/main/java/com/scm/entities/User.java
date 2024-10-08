package com.scm.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "user")
@Table(name = "users")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User implements UserDetails{

    @Id
    private String userId;

    @Column(name = "user_name" , nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    // @UniqueElements
    private String email;

    @Getter(value = AccessLevel.NONE)
    private String password;

    @Column(length = 1000)  
    private String about;
    private String profile;
    // @UniqueElements
    private String phoneNumber;

    // Information
    @Getter(value = AccessLevel.NONE)
    private boolean enabled = false;
    private boolean emailVarified = false;
    private boolean phoneNumberVarified = false;

    // SELF , GOOGLE, TWITTER, GITHUB
    @Enumerated(value = EnumType.STRING)
    private Provider provider = Provider.SELF;
    private String providerUserId;
    // private Provider provider2 = Provider.GOOGLE;
    // private Provider provider3 = Provider.GITHUB;

    // Add more filed if needed..

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true)
    private List <Contacts> contact = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    // This is for token generating verfication
    private String emailToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

         Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
     }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String getPassword() {
        return this.password;
     }

}
