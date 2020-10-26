package com.sigma.users.service.impl;


import com.sigma.users.dto.UserDTO;
import com.sigma.users.exceptions.AlreadyExistsException;
import com.sigma.users.exceptions.ErrorMessage;
import com.sigma.users.exceptions.NotFoundException;
import com.sigma.users.model.RegistrationModel;
import com.sigma.users.model.User;
import com.sigma.users.repositories.UserRepository;
import com.sigma.users.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    @Transactional
    public void createUser(RegistrationModel registrationModel) {

        if(userRepository.findByLogin(registrationModel.getLogin()) != null){
            throw new AlreadyExistsException(ErrorMessage.USER_ALREADY_EXISTS);
        }
        User user = modelMapper.map(registrationModel, User.class);
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(registrationModel.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDTO getUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    @Transactional
    public Page<UserDTO> getAllUsers(Pageable pageable, Specification<User> specification) {

        Page<User> page = userRepository.findAll(specification, pageable);
        return page.map(new Function<User, UserDTO>() {
            @Override
            public UserDTO apply(User user) {

                return modelMapper.map(user, UserDTO.class);
            }
        });
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserDTO userDTO) {

        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
        modelMapper.map(userDTO, user);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        userRepository.delete(user);
    }
}
