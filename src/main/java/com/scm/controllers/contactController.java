package com.scm.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.AppConstants;
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
        String filename = UUID.randomUUID().toString();

       String fileUrl = imageServices.uploadimage(contactForm.getContactImage(), filename); 

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
        contact.setCloudinaryImagePublicId(fileUrl);
        contactServices.save(contact);

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

    // View Contacts
    @RequestMapping
    public String viewContacts(
     @RequestParam(value = "page", defaultValue = "0") int page,
     @RequestParam(value = "size", defaultValue = "AppConstants.PAGE_SIZE") int size,
     @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
     @RequestParam(value = "direction", defaultValue = "asc") String direction, 
     Model model, Authentication authentication) {

        // Load the all Contacts 
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userServices.getUserByEmail(username);

        Page <Contacts> pageContacts = contactServices.getByUser(user,page,size,sortBy,direction);
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/contacts";
    }

}
