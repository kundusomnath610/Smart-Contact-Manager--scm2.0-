package com.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactsSearchForm;
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
//@CrossOrigin("*")
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

    private Page<Contacts> searchByName;

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

        Contacts contact = new Contacts();

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setFavorite(contactForm.isFavorite());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setUser(user);

        //Image File upload..
        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileUrl = imageServices.uploadimage(contactForm.getContactImage(), filename); 
            contact.setPicture(fileUrl);
            contact.setCloudinaryImagePublicId(fileUrl);
        }

        contactServices.save(contact); // Save the contactin DataBase
        // Passing the Add Contact Data
        System.out.println(contactForm);

        //Message to successfully add the contact
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
     @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
     @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
     @RequestParam(value = "direction", defaultValue = "asc") String direction, 
     Model model, Authentication authentication) {

        // Load the all Contacts 
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userServices.getUserByEmail(username);

        Page <Contacts> pageContacts = contactServices.getByUser(user,page,size,sortBy,direction);
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactsSearchForm", new ContactsSearchForm());

        return "user/contacts";
    }

    // For search option
    @RequestMapping("/search")
    public String searchHandler(
        @ModelAttribute ContactsSearchForm contactsSearchForm,
        @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model,
        Authentication authentication
) {
    logger.info("Searching by field: {} with keyword: {}", contactsSearchForm.getField(), contactsSearchForm.getValue());

    var user = userServices.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

    Page<Contacts> pageContacts = null;

    switch (contactsSearchForm.getField().toLowerCase()) {
        case "name":
            pageContacts = contactServices.searchByName(contactsSearchForm.getValue(), size, page, sortBy, direction, user);
            break;
        case "email":
            pageContacts = contactServices.searchByEmail(contactsSearchForm.getValue(), size, page, sortBy, direction, user);
            break;
        case "phone":
            pageContacts = contactServices.searchByPhoneNumber(contactsSearchForm.getValue(), size, page, sortBy, direction, user);
            break;
        default:
            // //Handle invalid search field case
            // model.addAttribute("message", Message.builder()
            //         .content("Invalid search field")
            //         .type(MessageType.red)
            //         .build());
            return "user/search";
    }

    // if (pageContacts == null || pageContacts.isEmpty()) {
    //     model.addAttribute("message", Message.builder()
    //             .content("No contacts found for the given search criteria")
    //             .type(MessageType.yellow)
    //             .build());
    // } else {
        
    // }
    model.addAttribute("pageContacts", pageContacts);
    model.addAttribute("contactsSearchForm", contactsSearchForm);
    model.addAttribute("pageSize", AppConstants.PAGE_SIZE);


    return "user/search";
    }


    // delete Contact Handler
    @RequestMapping("/delete/{contactId}")
    public String deleteContact( 
            @PathVariable("contactId") String contactId) {

        contactServices.delete(contactId);
        logger.info("contactId {} deleted", contactId);
        return "redirect:/user/contacts";
    }

    // View Contact Handler
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
        @PathVariable("contactId") String contactId, 
        Model model) {

            var contact = contactServices.getById(contactId);
            ContactForm contactForm = new ContactForm();
            contactForm.setName(contact.getName());
            contactForm.setEmail(contact.getEmail());
            contactForm.setPhoneNumber(contact.getPhoneNumber());
            contactForm.setAddress(contact.getAddress());
            contactForm.setDescription(contact.getDescription());
            contactForm.setFavorite(contact.isFavorite());
            contactForm.setWebsiteLink(contact.getWebsiteLink());
            contactForm.setLinkedinLink(contact.getLinkedinLink());
            contactForm.setPicture(contact.getPicture());

            model.addAttribute("contactForm", contactForm);
            model.addAttribute("contactId", contactId);

        return "user/update_contact_view"; 

    }


    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(    
    @PathVariable("contactId")String contactId,
    @Valid @ModelAttribute ContactForm contactForm,
    BindingResult bindingResult,
    Model model
    ) {

        // Update the contact

        if(bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        var con = contactServices.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedinLink(contactForm.getLinkedinLink());

        //con.setPicture(contactForm.getPicture());
        //contactServices.update(null);

        // Image process 
        if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file is not empty");
            String filename = UUID.randomUUID().toString();
            String imageUrl = imageServices.uploadimage(contactForm.getContactImage(), filename);
            con.setCloudinaryImagePublicId(filename);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        } else {
            logger.info("file is empty");
        }


        var updateCon = contactServices.update(con);
        logger.info("update Contact {}", updateCon);

        model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());

        return "redirect:/user/contacts/view/" + contactId;
    }
}