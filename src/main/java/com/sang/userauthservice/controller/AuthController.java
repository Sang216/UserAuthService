package com.sang.userauthservice.controller;

import com.sang.userauthservice.dto.AuthRequest;
import com.sang.userauthservice.dto.UserDto;
import com.sang.userauthservice.model.User;
import com.sang.userauthservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UserDto> signUp(@RequestBody AuthRequest request) {
        System.out.println("Request: "+ request);
        User user = authService.signUp(request.getEmail(),
                request.getPassword());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setId(user.getId());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
