package com.github.apirestjunit.api.resource;

import com.github.apirestjunit.domain.dto.UserDTO;
import com.github.apirestjunit.domain.model.User;
import com.github.apirestjunit.domain.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserResource {

    public static final String ID = "/{id}";
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping(ID)
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.map(userService.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> listDto = userService.findAll().stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listDto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ID).buildAndExpand(userService.create(dto).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(ID)
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO dto) {
        dto.setId(id);
        User user = userService.update(dto);
        return ResponseEntity.ok(mapper.map(user, UserDTO.class));
    }

    @DeleteMapping(ID)
    public ResponseEntity<UserDTO> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
