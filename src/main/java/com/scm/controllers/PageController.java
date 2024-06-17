package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.forms.UserForm;

import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(Model model) {
        // Sending Data to view....
        model.addAttribute("name", "Substring");
        model.addAttribute("somnath", "kundu");
        model.addAttribute("GithubRepo", "https://github.com/kundusomnath610");
        System.out.println("Home page handeler");
        return "home";
    }

    // About Mapping
    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isUserLogin", true);
        System.out.println("This is the about page");
        return "about";
    }

    // Services Page
    @RequestMapping("/services")
    public String servicePage(Model model) {
        System.out.println("This is the service page");
        return "service";
    }

    // Contact Page.. 
    @GetMapping("/contact")
    public String contact() {
        System.out.println("This is contact page");
        return new String("contact");
    }

    //Login Page
    @GetMapping("/login")
    public String login() {
        System.out.println("This is log in page");
        return new String("login");
    }

    //register..
    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        // userForm.setName("Somnath");
        // userForm.setAbout("this is about by myself and others");
        // userForm.setPhoneNumber("123456");
        return "register";
    }

    // Processing regiester request...
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute UserForm userForm) {
        System.out.println("Processing registration");
        // fetch form data
        // UserForm
        System.out.println(userForm);
        return "redirect:/register";
    }



}
