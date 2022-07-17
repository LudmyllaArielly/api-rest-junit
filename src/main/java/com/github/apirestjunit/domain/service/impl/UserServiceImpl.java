package com.github.apirestjunit.domain.service.impl;

import com.github.apirestjunit.domain.model.User;
import com.github.apirestjunit.domain.repository.UserRepository;
import com.github.apirestjunit.domain.service.UserService;
import com.github.apirestjunit.domain.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;


    @Override
    public User findById(Long id) {
        Optional<User> optUser = repository.findById(id);
        return optUser.orElseThrow(() -> new ObjectNotFoundException("Object not found."));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }


}
