package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.services.ContactServices;
import com.scm.services.UserServices;

import jakarta.security.auth.message.config.AuthConfig;

@Controller
@RequestMapping("/user/contacts")
public class contactController {

    @Autowired
    private ContactServices contactServices;

    @Autowired
    private UserServices userServices;

    private User userByEmail;

    private User user;

    @RequestMapping("/add")
    public String addContactView(Model model) {

        ContactForm contactForm = new ContactForm();
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForm contactForm, Authentication authentication) {

        //Process the form
        String username = Helper.getEmailOfLoggedInUser(authentication);

        // form -> contacts...
        User user = userServices.getUserByEmail(username);

        Contacts contact = new Contacts();

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone_Number(contactForm.getPhone_Number());
        contact.setFavorite(contactForm.isFavorite());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setUser(user);
        contactServices.save(contact);

        // Passing the Add Contact Data
        System.out.println(contactForm);

        return "redirect:/user/contacts/add";
    }

}
