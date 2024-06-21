package com.scm.services.impl;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.entities.User.UserBuilder;
import com.scm.helpers.AppConstants;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositires.Userrepo;
import com.scm.services.UserServices;

@Service

public class UserServicesImpl implements UserServices {

    @Autowired
    private Userrepo userrepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User savaUser(User user) {
        // User ID Genarate Automatically
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        // Password Encoder..

        //user.setPassword(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set the user Role...
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        logger.info(user.getProvider().toString());

        return userrepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userrepo.findById(id);
    } 

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userrepo.findById(user.getUserId()).orElseThrow(()->
            new ResourceNotFoundException()
        );
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setAbout(user.getAbout());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVarified(user.isEmailVarified());
        user2.setPhoneNumberVarified(user.isPhoneNumberVarified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        // Save the user in database...

        User save = userrepo.save(user2);

        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userrepo.findById(id).orElseThrow(()->
        new ResourceNotFoundException()
        );
        userrepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userrepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByUserName(String email) {
        User user = userrepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUser() {
        return userrepo.findAll();
    }
    


}