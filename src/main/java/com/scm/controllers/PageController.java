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
}
