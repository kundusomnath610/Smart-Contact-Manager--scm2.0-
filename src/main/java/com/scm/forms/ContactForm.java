package com.scm.forms;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Name Requried")
    @Size(min = 3, message = "Min 3 letter requried")
    private String name;

    @Email(message = "invalid Email Address")
    @NotBlank(message = "Email is Requried [example@gmail.com]")
    private String email;

    @NotBlank(message = "Phone Number Requried")
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message = "Address Requried")
    private String address;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedinLink;

    //public String cloudinaryImagePublicId;


    private MultipartFile contactImage; 

    private String picture;

}
