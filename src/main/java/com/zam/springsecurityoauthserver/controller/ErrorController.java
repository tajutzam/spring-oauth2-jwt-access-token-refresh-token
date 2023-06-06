package com.zam.springsecurityoauthserver.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zam.springsecurityoauthserver.dto.WebResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestControllerAdvice
public class ErrorController {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(InvalidBearerTokenException.class)
    public void handleUnAuthorized(HttpServletResponse response){
        System.out.println("unauthorized");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public void handleResponseStatusExceptions(HttpServletResponse response , ResponseStatusException statusException) throws IOException {
        response.setStatus(statusException.getStatusCode().value());
        WebResponse<String> errors = WebResponse.<String>builder().errors(statusException.getMessage()).build();
        response.getWriter().write(objectMapper.writeValueAsString(errors));
    }

}
