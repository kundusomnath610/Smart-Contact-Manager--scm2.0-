package com.scm.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scm.services.impl.SecurityCustomDetailsServices;

@Configuration
public class SecurityConfig {

// User Creat and login using java code 

    // @Bean
    // public UserDetailsService userDetailsService() {

    //     UserDetails user1 = User
    //     .withDefaultPasswordEncoder()
    //     .username("somnath")
    //     .password("somnath_1234")
    //     .roles("ADMIN", "USER")
    //     .build();


    //    var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1);
    //    return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomDetailsServices userDetailsServices;

    @Bean
    public DaoAuthenticationProvider authenticationProvider () {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user Details Services object invoked here
        daoAuthenticationProvider.setUserDetailsService(userDetailsServices);
        // password encoder Object invoked here 
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }


}
