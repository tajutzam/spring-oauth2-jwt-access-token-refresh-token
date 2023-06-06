package com.zam.springsecurityoauthserver.controller;


import com.zam.springsecurityoauthserver.entity.User;
import com.zam.springsecurityoauthserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok().body(userRepository.findAll());
    }

}
