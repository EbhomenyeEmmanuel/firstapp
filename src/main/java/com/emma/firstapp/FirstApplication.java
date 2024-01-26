package com.emma.firstapp;

import com.emma.firstapp.user.User;
import com.emma.firstapp.user.data.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class FirstApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            Stream.of(new User(1L, "Emma", "Ebhomenye", encoder.encode("password")),
                    new User(2L, "Zion", "Fred", encoder.encode("password")),
                    new User(3L, "Philip", "Mary", encoder.encode("password")),
                    new User(4L, "John", "David", encoder.encode("password")),
                    new User(5L, "Faith", "Billy", encoder.encode("password"))).forEachOrdered(repo::save);
            repo.findAll().forEach(System.out::println);
        };
    }
}
