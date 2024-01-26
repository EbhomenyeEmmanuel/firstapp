package com.emma.firstapp.registration;

import com.emma.firstapp.user.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    private String username;
    private String lastname;
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(0L, username, lastname, passwordEncoder.encode(password));
    }
}
