package com.github.apirestjunit.domain.service;

import com.github.apirestjunit.domain.model.User;

public interface UserService {

    User findById(Long id);
}
