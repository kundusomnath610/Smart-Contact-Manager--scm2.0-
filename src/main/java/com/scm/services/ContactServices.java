package com.scm.services;


import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;



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
    Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order);
    Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order);
    Page<Contacts> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order);

    // get contact byUserId
    List<Contacts> getByUserId(String userId);

    //Contacts Services getByUser
    Page <Contacts> getByUser(User user, int page , int size, String sortField, String sortDirection);

    
}
