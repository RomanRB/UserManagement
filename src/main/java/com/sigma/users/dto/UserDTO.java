package com.sigma.users.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;


@Data
public class UserDTO {

    private long id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String login;

    private String aboutMe;

    private String address;

}
