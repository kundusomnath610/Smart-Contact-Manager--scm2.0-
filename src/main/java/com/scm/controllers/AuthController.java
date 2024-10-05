package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.repositires.Userrepo;

import jakarta.servlet.http.HttpSession;

@Controller
//@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {

    // Verify Email

    @Autowired
    private Userrepo userrepo;

    @GetMapping("/verify-email")
    public String verifyEmail(
        @RequestParam("token") String token, HttpSession session) {

            User user = userrepo.findByEmailToken(token).orElse(null);

            if(user != null) {
                // User is fetched

                if(user.getEmailToken().equals(token)) {
                    user.setEmailVarified(true);
                    user.setEnabled(true);
                    userrepo.save(user);

                    session.setAttribute("message", Message.builder().
                    type(MessageType.green).content("Email is verified Successfully !! Token is Consider for Login")
                    .build());

                    return "success_page";
                }
                
                session.setAttribute("message", Message.builder().
                type(MessageType.red).content("Email is not verified !! Token is not Consider")
                .build());
                return "error_page";
            }

            session.setAttribute("message", Message.builder().
            type(MessageType.red).content("Email is not verified !! Token is not Consider")
            .build());

        return "error_page";

    }

}
