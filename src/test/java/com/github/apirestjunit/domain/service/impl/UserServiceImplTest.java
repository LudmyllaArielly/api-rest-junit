package com.github.apirestjunit.domain.service.impl;

import com.github.apirestjunit.domain.dto.UserDTO;
import com.github.apirestjunit.domain.model.User;
import com.github.apirestjunit.domain.repository.UserRepository;
import com.github.apirestjunit.domain.service.exceptions.DataIntegrityViolationException;
import com.github.apirestjunit.domain.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceImplTest {

    public static final long ID = 1L;
    public static final String NAME = "Ana";
    public static final String EMAIL = "ana@xyz.com";
    public static final String PASSWORD = "123";
    public static final String OBJECT_NOT_FOUND = "Object not found.";
    public static final int INDEX = 0;
    public static final String EMAIL_IN_USE = "Email in use.";

    @Mock
    private ModelMapper mapper;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO dto;
    private Optional<User> userOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(userOptional);

        User response = userService.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    public void whenFindByIdThenReturnAnObjectNotFoundException(){
        Mockito.when(repository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));

        try {
            userService.findById(ID);
        }catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        Mockito.when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = userService.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        User response = userService.create(dto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(userOptional);

       try {
           userOptional.get().setId(2L);
           userService.create(dto);
       }catch (Exception ex) {
           assertEquals(DataIntegrityViolationException.class, ex.getClass());
           assertEquals(EMAIL_IN_USE, ex.getMessage());
       }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        User response = userService.update(dto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
   public void whenUpdateThenReturnAnDataIntegrityViolationException() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(userOptional);

        try {
            userOptional.get().setId(2L);
            userService.update(dto);
        }catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(EMAIL_IN_USE, ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(userOptional);

        // nao faca nada
        Mockito.doNothing().when(repository).deleteById(Mockito.anyLong());
        userService.delete(ID);

        Mockito.verify(repository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void deleteWithObjectNotFoundException(){
        Mockito.when(repository.findById(Mockito.anyLong())).thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));

        try {
            userService.delete(ID);
        }catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
        }
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        dto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        userOptional = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }

}
