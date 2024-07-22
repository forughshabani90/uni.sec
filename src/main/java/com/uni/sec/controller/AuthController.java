package com.uni.sec.controller;

import com.uni.sec.model.ERole;
import com.uni.sec.model.Role;
import com.uni.sec.model.User;
import com.uni.sec.repository.RoleRepository;
import com.uni.sec.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;

    @PostMapping
    public User signUp(@RequestBody User userRequest) {
        User user1 = new User(userRequest.getUsername(),
                encoder.encode(userRequest.getPassword()), userRequest.getEmail());
        Set<Role> rolesRequest = userRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        user1.setRoles(rolesRequest);
        return userRepository.save(user1);

        for (Role role : rolesRequest)
            switch (role) {
                case "admin":
                    roles.add(roleRepository.findByName(ERole.ROLE_ADMIN));
                    break;
                case "user":
                    roles.add(roleRepository.findByName(ERole.ROLE_USER));
                    break;
                case "moderator":
                    roles.add(roleRepository.findByName(ERole.ROLE_MODERATOR));
                    break;
            }


    }
}
