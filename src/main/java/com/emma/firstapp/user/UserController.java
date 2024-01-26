package com.emma.firstapp.user;


import com.emma.firstapp.user.data.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/users", produces = "application/json")
@CrossOrigin(origins = {"http://emmajavademo:8080", "http://emmajavademo.com"})
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("recents")
    public Iterable<User> recentUsers() {
        PageRequest page = PageRequest.of(0, 5, Sort.by("id").descending());
        return userRepository.findAllBy(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserByID(@PathVariable("id") Long id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> postUser(@RequestBody User user) {
        Optional<User> optUser = userRepository.findByUsername(user.getUsername());
        if (optUser.isEmpty()) {
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        //"User with username" + user.getUsername() + " already exists."
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT,
//            "Conflict: User already exists"
//        ));
    }

    @PutMapping(path = "/{lastName}", consumes = "application/json")
    public ResponseEntity<User> putUser(
            @PathVariable("lastName") String lastName,
            @RequestBody User user) {
        user.setLastName(lastName);
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{userName}", consumes = "application/json")
    public ResponseEntity<User> patchUser(
            @PathVariable("userName") String userName,
            @RequestBody User user) {
        Optional<User> optUser = userRepository.findByUsername(userName);
        if (optUser.isEmpty()) {
            if (user.getId() != null) {
                user.setId(user.getId());
            }
            if (user.getUsername() != null) {
                user.setUsername(user.getUsername());
            }
            if (user.getLastName() != null) {
                user.setLastName(user.getLastName());
            }
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
        }
    }
}
