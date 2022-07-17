package com.github.apirestjunit.domain.service;

import com.github.apirestjunit.domain.dto.UserDTO;
import com.github.apirestjunit.domain.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    List<User> findAll();

    User create(UserDTO dto);

    User update(UserDTO dto);
}
