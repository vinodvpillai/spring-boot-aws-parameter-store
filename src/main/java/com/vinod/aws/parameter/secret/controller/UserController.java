package com.vinod.aws.parameter.secret.controller;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Value("${db.username}")
    private String dbUsername;

    @GetMapping
    public String getResult() {
        log.debug("Request came to fetch the parameter store value");
        String result = "AWS parameter store property DB Username: " + dbUsername;
        log.debug("Parameter store value for DB Username is: {}",dbUsername);
        return result;
    }
}
