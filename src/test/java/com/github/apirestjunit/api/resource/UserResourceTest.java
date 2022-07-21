package com.github.apirestjunit.api.resource;

import com.github.apirestjunit.domain.dto.UserDTO;
import com.github.apirestjunit.domain.model.User;
import com.github.apirestjunit.domain.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
    void testFindById() {

    }

    @Test
    void testFindAll() {

    }

    @Test
    void testCreate() {

    }

    @Test
    void testUpdate() {

    }

    @Test
    void testDelete() {

    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        dto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}
