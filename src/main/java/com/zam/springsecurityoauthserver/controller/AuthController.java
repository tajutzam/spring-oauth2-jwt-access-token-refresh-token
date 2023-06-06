package com.zam.springsecurityoauthserver.controller;


import com.zam.springsecurityoauthserver.dto.*;
import com.zam.springsecurityoauthserver.entity.User;
import com.zam.springsecurityoauthserver.services.TokenGenerator;
import com.zam.springsecurityoauthserver.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private UserManager userDetailsManager;

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @PostMapping("/register")
    public ResponseEntity<WebResponse<TokenResponse>> register(@RequestBody SignUpDto signUpDto) {
        User user = User.builder().
                username(signUpDto.getUsername())
                .password(signUpDto.getPassword()).
                firstName(signUpDto.getFirstName())
                .lastName(signUpDto.getLastName())
                .email(signUpDto.getEmail()).
                build();
        userDetailsManager.createUser(user);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user, signUpDto.getPassword(), Collections.EMPTY_LIST);
        TokenResponse response = tokenGenerator.createToken(authentication);
        return ResponseEntity.ok().body(WebResponse.<TokenResponse>builder().data(response).build());
    }

    @PostMapping("/login")
    public WebResponse<Object> login(@RequestBody LoginDto loginDto){
        TokenResponse tokenResponse = userDetailsManager.authenticate(loginDto);
        return WebResponse.builder().data(tokenResponse).build();
    }

    @PostMapping("/token")
    public WebResponse<TokenResponse> token(@RequestBody TokenDto tokenDto){
        TokenResponse response = userDetailsManager.token(tokenDto);
        return WebResponse.<TokenResponse>builder().data(response).build();
    }

}
