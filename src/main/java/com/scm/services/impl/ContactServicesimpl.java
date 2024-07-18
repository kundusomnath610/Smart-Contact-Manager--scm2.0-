package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Contacts;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositires.ContactRepo;
import com.scm.services.ContactServices;

@Service
public class ContactServicesimpl implements ContactServices {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public void delete(String id) {

        var contact = contactRepo.findById(id).orElseThrow(()-> 
            new ResourceNotFoundException("Contact Not Found with given id" + contactRepo));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contacts> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contacts getById(String id) {
        return contactRepo.findById(id).orElseThrow(()-> 
            new ResourceNotFoundException("Contact Not Found with given id" + contactRepo));
    }

    @Override
    public List<Contacts> getByUserId(String userId) {

       return contactRepo.findByUserId(userId);
    }

    @Override
    public Contacts save(Contacts contacts) {
        String contactId = UUID.randomUUID().toString();
        contacts.setId(contactId);
        return contactRepo.save(contacts);
    }

    @Override
    public List<Contacts> search(String name, String email, String phone_Number) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Contacts update(Contacts contacts) {
        // TODO Auto-generated method stub
        return null;
    }

}
