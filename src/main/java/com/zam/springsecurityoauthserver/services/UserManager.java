package com.zam.springsecurityoauthserver.services;

import com.zam.springsecurityoauthserver.dto.LoginDto;
import com.zam.springsecurityoauthserver.dto.TokenDto;
import com.zam.springsecurityoauthserver.dto.TokenResponse;
import com.zam.springsecurityoauthserver.entity.User;
import com.zam.springsecurityoauthserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Collections;

@Service
@Slf4j
public class UserManager implements UserDetailsManager {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DaoAuthenticationProvider provider;


    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("jwtRefreshTokenDecoder")
    private JwtDecoder jwtDecoder;

    @Autowired
    @Qualifier("jwtRefreshTokenAuthProvider")
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public void createUser(UserDetails user) {
        ((User) user).setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user not found"));
    }

    public TokenResponse authenticate(LoginDto loginDto){
        UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(loginDto.getUsername(), loginDto.getPassword(), Collections.EMPTY_LIST);
        Authentication authenticate = provider.authenticate(authenticated);
        return tokenGenerator.createToken(authenticate);
    }

    public TokenResponse token(TokenDto tokenDto){


        try{
            Jwt decode = jwtDecoder.decode(tokenDto.getRefreshToken());

            userRepository.findById(decode.getSubject()).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED  , "your credentials not valid , please login again"));

            Instant expiresAt = decode.getExpiresAt();
            Instant now = Instant.now();

            if(expiresAt.isBefore(now)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "your token is expired please login again");
            }
            Authentication authentication = jwtAuthenticationProvider.authenticate(new BearerTokenAuthenticationToken(tokenDto.getRefreshToken()));
            return tokenGenerator.createToken(authentication);
        }catch (JwtValidationException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , exception.getMessage());
        }
    }
}
