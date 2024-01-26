package com.emma.firstapp.user.data;

import com.emma.firstapp.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();

    Page<User> findAllBy(PageRequest pageRequest);


    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    @Override
    User save(User user);
}
