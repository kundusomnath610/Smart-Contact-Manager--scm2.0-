package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user")
public class UserController {

    //User Dashbaord Page
    @RequestMapping(value = "/dashboard")
    public String userDashbaord() {
        System.out.println("dashboard");
        return "user/dashboard";
    }

    // User Profile page
    @RequestMapping(value = "/profile")
    public String userProfile() {
        System.out.println("profile::");
        return "user/profile";
    }
    


    

}
