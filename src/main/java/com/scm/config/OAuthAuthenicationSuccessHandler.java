package com.scm.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entities.Provider;
import com.scm.entities.User;
import com.scm.helpers.AppConstants;
import com.scm.repositires.Userrepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);
    
    @Autowired
    private Userrepo userrepo;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request, 
        HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {

            logger.info("OAuthAuthenicationSuccessHandler");

            DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
            logger.info(user.getName());

            user.getAttributes().forEach((key, value) -> {

                logger.info("{} => {}", key, value);
            });

            logger.info(user.getAuthorities().toString());

            // Data Saved in DataBase...

            
            String email = user.getAttribute("email").toString();
           
            String name = user.getAttribute("name").toString();
            
            String picture = user.getAttribute("picture").toString();

            // Create user and save to database..

            User user1 = new User();
            user1.setEmail(email);
            user1.setName(name);
            user1.setProfilePic(picture);
            user1.setPassword(picture);
            user1.setUserId(UUID.randomUUID().toString());
            user1.setProvider(Provider.GOOGLE);
            user1.setEnabled(true);

            user1.setEmailVarified(true);
            user1.setProviderUserId(user.getName());
            user1.setRoleList(List.of(AppConstants.ROLE_USER));
            user1.setAbout("This is creating by Google");

            User user2 = userrepo.findByEmail(email).orElse(null); // Create the user in database for user2...

            if(user2 == null) {
                userrepo.save(user1);
                logger.info("User Saved:" + email);// print the saved user along with the email id..
            }


            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
