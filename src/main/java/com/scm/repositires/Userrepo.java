package com.scm.repositires;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;


@Repository
public interface Userrepo extends JpaRepository<User, String> {

    // extra DB related method.. Here..
    // Custom Query Method......
    // Custom finder method...     

    Optional <User> findByEmail(String email);

    Optional <User> findByEmailAndPassword(String email, String password);

    Optional <User> findByEmailToken(String id);

}
