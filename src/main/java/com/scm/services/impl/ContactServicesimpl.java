package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SortAdapter;
import org.springframework.stereotype.Service;

import com.scm.entities.Contacts;
import com.scm.entities.User;
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
    public Contacts update(Contacts contacts) {
        var contactOld = contactRepo.findById(contacts.getId())
        .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        contactOld.setName(contacts.getName());
        contactOld.setEmail(contacts.getEmail());
        contactOld.setPhoneNumber(contacts.getPhoneNumber());
        contactOld.setAddress(contacts.getAddress());
        contactOld.setDescription(contacts.getDescription());
        contactOld.setPicture(contacts.getPicture());
        contactOld.setFavorite(contacts.isFavorite());
        contactOld.setWebsiteLink(contacts.getWebsiteLink());
        contactOld.setLinkedinLink(contacts.getLinkedinLink());
        contactOld.setCloudinaryImagePublicId(contacts.getCloudinaryImagePublicId());

        return contactRepo.save(contactOld);
    }

     /*
     * Search Contact Implemented Here 
     * From ContactServices Interface search Method...
     */

    @Override
    public Page <Contacts> getByUser(User user , int page , int size , String sortBy, String direction) {

        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size , sort);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contacts> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contacts> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return contactRepo.findByUserAndEmailContaining(user,emailKeyword, pageable);
    }

    @Override
    public Page<Contacts> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }








}


































//   @Override
//     public Page<Contacts> searchByname(String nameKeyword, int size, int page, String sortBy, String order) {
//         Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//         var pageable = PageRequest.of(page, size, sort);
//         return contactRepo.findBynameContaining(nameKeyword, pageable); 
//     }

//     @Override
//     public Page<Contacts> searchByemail(String emailKeyword, int size, int page, String sortBy, String order) {
//         Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//         var pageable = PageRequest.of(page, size, sort);
//         return contactRepo.findByemailContaining(emailKeyword, pageable); 
//     }

//     @Override
//     public Page<Contacts> searchByphone_Number(String phone_NumberKeyword, int size, int page, String sortBy, String order) {
//         Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//         var pageable = PageRequest.of(page, size, sort);
//         return contactRepo.findByphone_NumberContaining(phone_NumberKeyword, pageable); 
//     }
// }