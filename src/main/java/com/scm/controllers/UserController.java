package com.scm.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserServices;



@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServices userServices;


    //Method ...
    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {

        System.out.println("Adding Logged In to the user");

        String username = Helper.getEmailOfLoggedInUser(authentication);

     
        logger.info("User Logged in: {}", username);

       User user =  userServices.getUserByEmail(username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("loggedInUser", user);
    }

    //User Dashbaord Page
    @RequestMapping(value = "/dashboard")
    public String userDashbaord() {
       

        System.out.println("dashboard");
        return "user/dashboard";
    }

    // User Profile page
    @RequestMapping(value = "/profile")
    public String userProfile(Model model, Authentication authentication) {

       

        return "user/profile";
    }
    


    

}
