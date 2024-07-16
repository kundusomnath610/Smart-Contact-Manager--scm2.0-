package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    private String name;
    private String email;
    private String phone_Number;
    private String address;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedinLink;

    private MultipartFile profileImage;

}
