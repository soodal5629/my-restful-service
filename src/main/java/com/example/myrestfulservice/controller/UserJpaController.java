package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
@RequiredArgsConstructor
public class UserJpaController {
    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity retrieveAllUsers() {
        Map<String, Object> map = new HashMap<>();
        map.put("count", userRepository.count());
        map.put("users", userRepository.findAll());

        return ResponseEntity.ok(map);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }
        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder linTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linTo.withRel("all-users"));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Validated @RequestBody User user) {
        if (user.getJoinDate() == null) {
            user.setJoinDate(LocalDateTime.now());
        }
        User savedUser = userRepository.save(user);
        // response 헤더에 key: location으로 생성된 user 상세보기 uri 를 담아서 보낼 수 있음
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        // http status code: 201
        return ResponseEntity.created(location).build();
    }
}
