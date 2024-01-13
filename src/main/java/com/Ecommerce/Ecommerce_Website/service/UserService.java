package com.Ecommerce.Ecommerce_Website.service;

import com.Ecommerce.Ecommerce_Website.dto.AuthenticateResponse;
import com.Ecommerce.Ecommerce_Website.dto.SignUp;
import com.Ecommerce.Ecommerce_Website.dto.UserDto;
import com.Ecommerce.Ecommerce_Website.entity.User;
import com.Ecommerce.Ecommerce_Website.enums.Role;
import com.Ecommerce.Ecommerce_Website.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse register(UserDto userDto) {
        var user = User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user.getFirstname());
        return AuthenticateResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticateResponse signUp(SignUp signUp) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUp.getEmail(),
                        signUp.getPassword()
                )
        );
        var user = userRepo.findByEmail(signUp.getEmail()).orElseThrow(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found for email: " + signUp.getEmail());
        } else {
            var jwtToken = jwtService.generateToken((signUp.getEmail()));
            return AuthenticateResponse.builder()
                    .token(jwtToken)
                    .build();
        }

    }


    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = (User) userRepo.findByRole(Role.ADMIN);
        if (null == adminAccount){
            User user = new User();
            user.setEmail("admin@test.com");
            user.setFirstname("admin");
            user.setLastname("1");
            user.setRole(Role.ADMIN);
            user.setPassword(passwordEncoder.encode("admin"));
            userRepo.save(user);
        }
    }

    public List<User> getUser() {
       return userRepo.findAll();
    }
}
