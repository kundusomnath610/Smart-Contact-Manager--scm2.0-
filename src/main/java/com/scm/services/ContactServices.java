package com.scm.services;


import java.util.List;

import com.scm.entities.Contacts;
import com.scm.entities.User;

public interface ContactServices {

    // Save Contact
    Contacts save(Contacts contacts);

    // Update Contact
    Contacts update(Contacts contacts);

    //List of Contacts
    List<Contacts> getAll();

    // Contact by id
    Contacts getById(String id);

    // Delete Contact
    void delete(String id);

    // Search Contact
    List<Contacts> search(String name, String email, String phone_Number);

    // get contact byUserId
    List<Contacts> getByUserId(String userId);

    //Contacts Services getByUser
    List<Contacts> getByUser(User user);
    
}
