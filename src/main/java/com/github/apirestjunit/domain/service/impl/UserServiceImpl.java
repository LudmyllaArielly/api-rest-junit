package com.github.apirestjunit.domain.service.impl;

import com.github.apirestjunit.domain.dto.UserDTO;
import com.github.apirestjunit.domain.model.User;
import com.github.apirestjunit.domain.repository.UserRepository;
import com.github.apirestjunit.domain.service.UserService;
import com.github.apirestjunit.domain.service.exceptions.DataIntegrityViolationException;
import com.github.apirestjunit.domain.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper mapper;

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

    @Override
    public User create(UserDTO dto) {
        findByEmailUsed(dto);
        return repository.save(mapper.map(dto, User.class));
    }

    @Override
    public User update(UserDTO dto) {
        findByEmailUsed(dto);
        return repository.save(mapper.map(dto, User.class));
    }

    @Override
    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmailUsed(UserDTO dto){
        Optional<User> user = repository.findByEmail(dto.getEmail());
        if(user.isPresent() && !user.get().getId().equals(dto.getId())) {
            throw new DataIntegrityViolationException("Email in use.");
        }
    }

}
