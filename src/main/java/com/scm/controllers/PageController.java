package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class PageController {

    @Autowired
    private UserServices userServices;

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
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        System.out.println("Processing registration");
        // fetch form data
        // UserForm
        System.out.println(userForm);

        //Validate the Form..
        if(rBindingResult.hasErrors()) {
            return "register";
        }

        //Save the data user

        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic(
        //     "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Fprofile%2520picture%2F&psig=AOvVaw1cp80haAljDtmpbwnZTC5F&ust=1718792946299000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIDdnOv45IYDFQAAAAAdAAAAABAE")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setAbout(userForm.getAbout());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(true);
        user.setProfilePic(
            "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pexels.com%2Fsearch%2Fprofile%2520picture%2F&psig=AOvVaw1cp80haAljDtmpbwnZTC5F&ust=1718792946299000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCIDdnOv45IYDFQAAAAAdAAAAABAE");

        User savedUser = userServices.savaUser(user);

        System.out.println("User saved");

        // Add the Message..
        Message message = Message.builder().content("Registration Successful").type(MessageType.blue).build();
        session.setAttribute("message", message);

        System.out.println(userForm);
        return "redirect:/register";
    }



}
