package com.derrikcurran.sample.employeerestserver.security;

import org.springframework.security.core.AuthenticationException;

public class UserIdNotFoundException extends AuthenticationException {

    public UserIdNotFoundException(String msg) {
        super(msg);
    }

    public UserIdNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
