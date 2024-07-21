package com.scm.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactServices;
import com.scm.services.ImageServices;
import com.scm.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class contactController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(contactController.class);

    @Autowired
    private ContactServices contactServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private ImageServices imageServices;

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
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result, 
        Authentication authentication, HttpSession session) {

        // Validation form.
         if (result.hasErrors()) {

            //result.getAllErrors().forEach(error -> logger.info(error.toString()));

            //result.getAllErrors().forEach(error -> logger.info(error.toString()));
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }



        //Process the form
        String username = Helper.getEmailOfLoggedInUser(authentication);

        // form -> contacts...
        User user = userServices.getUserByEmail(username);

        //logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename());

        //Image File upload..
       String fileUrl = imageServices.uploadimage(contactForm.getContactImage()); 

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
        contact.setPicture(fileUrl);
        //contactServices.save(contact);

        // Passing the Add Contact Data
        System.out.println(contactForm);

        //Contact Picture URL..

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts/add";
    }

}
