package com.scm.repositires;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contacts;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contacts,String> {
    // Find By Contact User

    // Custom finder Method...
    List<Contacts> findByUser(User user);

    // Custom Query Method...
    @Query("SELECT c FROM Contacts c WHERE c.user.id = :userId")
    List<Contacts> findByUserId(@Param("userId")String userId);

}
