package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {

    @NotBlank(message = "User Name requried")
    @Size(min = 3, message = "Min 3 Charecter is Requried")
    private String name;

    @Email(message = "Invalied Email Address")
    @NotBlank(message = "Email is requried")
    private String email;

    @NotBlank(message = "Password Requried")
    @Size(min = 6, message = "Min 6 Charecter reuqried")
    private String password;

    @Size(min = 10, max = 12, message = "Valied Phn Number Requried")
    private String phoneNumber;

    @NotBlank(message = "About is Requried")
    private String about;
}
