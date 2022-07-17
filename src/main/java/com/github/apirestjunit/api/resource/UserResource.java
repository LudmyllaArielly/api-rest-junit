package com.github.apirestjunit.api.resource;

import com.github.apirestjunit.domain.dto.UserDTO;
import com.github.apirestjunit.domain.model.User;
import com.github.apirestjunit.domain.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.map(userService.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll(){
        List<UserDTO> listDto = userService.findAll().stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listDto);
    }
}
