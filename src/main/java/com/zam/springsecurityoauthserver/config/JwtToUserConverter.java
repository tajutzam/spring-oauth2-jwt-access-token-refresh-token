package com.zam.springsecurityoauthserver.config;


import com.zam.springsecurityoauthserver.entity.User;
import com.zam.springsecurityoauthserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class JwtToUserConverter implements Converter<Jwt , UsernamePasswordAuthenticationToken> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        User user = new User();
        user.setUsername(source.getSubject());
        // TODO: 6/5/23 check token has subject in repository
        Optional<User> optionalUser = userRepository.findById(source.getSubject());
        if(optionalUser.isEmpty()){
            System.out.println("is empty user");
            throw new InvalidBearerTokenException("your token is not valid");
        }
        System.out.println("a males");
        // authenticated user jwt
        return new UsernamePasswordAuthenticationToken(user , source, optionalUser.get().getAuthorities());
    }
    @Override
    public <U> Converter<Jwt, U> andThen(Converter<? super UsernamePasswordAuthenticationToken, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
