package com.scm.config;

import java.io.IOException;
import java.util.UUID;

import org.apache.catalina.authenticator.jaspic.PersistentProviderRegistrations.Providers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
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

            // Identufy the provider..
            var oauth2AuthenicationToken = (OAuth2AuthenticationToken) authentication;
            String authorizedClientRegistrationId = oauth2AuthenicationToken.getAuthorizedClientRegistrationId();

            logger.info(authorizedClientRegistrationId);

           var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();

           oauthUser.getAttributes().forEach((key, value) -> {
            logger.info(key + " : " + value);
        });

            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setRoleList(List.of(AppConstants.ROLE_USER));
            user.setEmailVarified(true);
            user.setEnabled(true);
            user.setPassword("dummy");

            if(authorizedClientRegistrationId.equalsIgnoreCase("google")) {
                // Google
                // Google Attribute

                user.setEmail(oauthUser.getAttribute("email").toString());
                user.setProfilePic(oauthUser.getAttribute("picture").toString());
                user.setName(oauthUser.getAttribute("name").toString());
                user.setProviderUserId(oauthUser.getName());
                user.setProvider(Provider.GOOGLE);
                user.setAbout("This account is created using google.");


            } else if(authorizedClientRegistrationId.equalsIgnoreCase("github")) {
                // Github

                String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                : oauthUser.getAttribute("login").toString() + "@gmail.com";
                String picture = oauthUser.getAttribute("avatar_url").toString();
                String name = oauthUser.getAttribute("login").toString();
                String providerUserId = oauthUser.getName();

                user.setEmail(email);
                user.setProfilePic(picture);
                user.setName(name);
                user.setProviderUserId(providerUserId);
                user.setProvider(Provider.GITHUB);

                user.setAbout("This account is created using github");

                
            } else if(authorizedClientRegistrationId.equalsIgnoreCase("facebook")) {
                // Facebook..
            } else {
                logger.info("OAuthAuthenicationSuccessHandler: Unknown Provider");
            }

            

            

            

            /* 
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
            */

            User user2 = userrepo.findByEmail(user.getEmail()).orElse(null); // Create the user in database for user2...

            if(user2 == null) {
                userrepo.save(user);
                System.out.println("User saved: " + user.getEmail());                
            }

            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }

}
