package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.entities.Contacts;
import com.scm.services.ContactServices;

@RestController
@RequestMapping("/api")
public class ApiController {

    // get contact
    @Autowired
    private ContactServices contactServices;

    @GetMapping("/contacts/{contactId}") 
    public Contacts getContact(@PathVariable String contactId) {
        return contactServices.getById(contactId); 
    }

   

}
