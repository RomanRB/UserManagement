package com.sigma.users.service;

import com.sigma.users.dto.UserDTO;
import com.sigma.users.model.RegistrationModel;
import com.sigma.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {

    void createUser(RegistrationModel registrationModel);

    UserDTO getUser(Long id);

    Page<UserDTO> getAllUsers(Pageable pageable, Specification<User> specification);

    void updateUser(Long id, UserDTO groupDTO);

    void deleteUser(Long id);
}
