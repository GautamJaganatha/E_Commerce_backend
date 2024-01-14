package com.Ecommerce.Ecommerce_Website.controller;

import com.Ecommerce.Ecommerce_Website.dto.AuthenticateResponse;
import com.Ecommerce.Ecommerce_Website.dto.SignUp;
import com.Ecommerce.Ecommerce_Website.dto.UserDto;
import com.Ecommerce.Ecommerce_Website.entity.User;
import com.Ecommerce.Ecommerce_Website.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<AuthenticateResponse> register(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.register(userDto));
    }

    @PostMapping("/signUp")
    public ResponseEntity<AuthenticateResponse> signUp(@RequestBody SignUp signUp){
        System.out.println(signUp);
        return ResponseEntity.ok(userService.signUp(signUp));
    }

    @GetMapping("/Welcome")
    public List<User> getUser(){
        return userService.getUser();
    }
}
