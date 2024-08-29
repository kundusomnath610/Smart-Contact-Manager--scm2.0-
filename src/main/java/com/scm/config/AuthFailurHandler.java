package com.scm.config;

import java.io.IOException;
import java.lang.management.MemoryType;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.scm.helpers.Message;
import com.scm.helpers.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailurHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

            if(exception instanceof DisabledException) {
                // User is Disable

                HttpSession session = request.getSession();
                session.setAttribute("message",
                Message.builder().content("User is Disable, Email send with Verification Link").type(MessageType.red).build());

                response.sendRedirect("/login");
            } else {
                response.sendRedirect("/login?error=true");
            }
    }
    
}
