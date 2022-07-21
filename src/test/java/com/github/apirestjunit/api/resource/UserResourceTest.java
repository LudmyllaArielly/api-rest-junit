package com.github.apirestjunit.api.resource;

import com.github.apirestjunit.domain.dto.UserDTO;
import com.github.apirestjunit.domain.model.User;
import com.github.apirestjunit.domain.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserResourceTest {

    public static final long ID = 1L;
    public static final String NAME = "Ana";
    public static final String EMAIL = "ana@xyz.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;

    public static final String OBJECT_NOT_FOUND = "Object not found.";
    public static final String EMAIL_IN_USE = "Email in use.";

    private User user;

    private UserDTO dto;

    @Mock
    ModelMapper mapper;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UserResource userResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
       Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(user);
       Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(dto);

        ResponseEntity<UserDTO> response = userResource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        Mockito.when(userService.findAll()).thenReturn(List.of(user));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(dto);

        ResponseEntity<List<UserDTO>> response = userResource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {
        Mockito.when(userService.create(Mockito.any())).thenReturn(user);

        ResponseEntity<UserDTO> response = userResource.create(dto);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(userService.update(dto)).thenReturn(user);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(dto);

        ResponseEntity<UserDTO> response = userResource.update(ID, dto);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
       Mockito.doNothing().when(userService).delete(Mockito.anyLong());

       ResponseEntity<UserDTO> response = userResource.delete(ID);

       assertNotNull(response);
       assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
       assertEquals(ResponseEntity.class, response.getClass());
       Mockito.verify(userService, Mockito.times(1))
               .delete(Mockito.anyLong());
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        dto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}
