package com.sigma.users.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegistrationModel {

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String login;

    private String password;

    private String aboutMe;

    private String address;
}
