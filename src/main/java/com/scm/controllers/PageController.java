package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/service")
    public String servicePage(Model model) {
        System.out.println("This is the service page");
        return "service";
    }

}
