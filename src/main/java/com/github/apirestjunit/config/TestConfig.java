package com.github.apirestjunit.config;

import com.github.apirestjunit.domain.model.User;
import com.github.apirestjunit.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public void startDB(){
        User u1 = new User(null, "Ana", "ana@xyz", "123");
        User u2 = new User(null, "Lucas", "lucas@xyz", "321");

        repository.saveAll(List.of(u1, u2));

    }
}
