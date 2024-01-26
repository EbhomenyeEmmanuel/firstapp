package com.emma.firstapp;

import com.emma.firstapp.user.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
    private UserRepository userRepo;

    @Autowired
    public HomeController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
}