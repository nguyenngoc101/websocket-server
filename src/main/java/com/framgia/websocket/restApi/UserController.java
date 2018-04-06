package com.framgia.websocket.restApi;


import com.framgia.websocket.model.User;
import com.framgia.websocket.repository.UserRepository;
import com.framgia.websocket.utils.JsonObject;
import com.framgia.websocket.utils.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@RestController
public class UserController {

    public static final List<String> DEFAULT_RETURNED_FIELDS = asList("id", "name", "email");
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping("/users/signup")
    public Map<String, Object> createUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = this.userRepository.save(user);
        Map<String, Object> userMap = new JsonObject(savedUser).toJsonObject(DEFAULT_RETURNED_FIELDS);
        userMap.put("access_token", new Jwt().signDefault(user.getName()));
        return userMap;
    }

    @DeleteMapping("/users/{id}")
    public User deleteUser(@PathVariable("id") long userId) {
        User user = userRepository.findById(userId).orElse(null);
        this.userRepository.deleteById(userId);
        return user;
    }

}
