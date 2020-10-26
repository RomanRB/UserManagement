package com.sigma.users.web;

import com.sigma.users.dto.UserDTO;
import com.sigma.users.model.RegistrationModel;
import com.sigma.users.model.User;
import com.sigma.users.service.UserService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody RegistrationModel registrationModel){
        userService.createUser(registrationModel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public Page<UserDTO> getAllUsers(@RequestParam(defaultValue = "0") int pageNumber,
                                      @RequestParam(defaultValue = "3") int pageSize,
                                      @RequestParam(defaultValue = "id") String sorting,
                                      @And ({
                                            @Spec(path = "lastName", spec = Equal.class),
                                            @Spec(path = "address", spec = Like.class)
                                       })Specification<User> specification){

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sorting));

        return userService.getAllUsers(pageable, specification);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity getUser(@PathVariable Long id){
        UserDTO userDTO = userService.getUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping(path = "/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        userService.updateUser(id, userDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }


}
