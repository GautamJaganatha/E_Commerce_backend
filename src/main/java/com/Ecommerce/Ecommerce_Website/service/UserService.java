package com.Ecommerce.Ecommerce_Website.service;

import com.Ecommerce.Ecommerce_Website.dto.AuthenticateResponse;
import com.Ecommerce.Ecommerce_Website.dto.SignUp;
import com.Ecommerce.Ecommerce_Website.dto.UserDto;
import com.Ecommerce.Ecommerce_Website.entity.Order;
import com.Ecommerce.Ecommerce_Website.entity.User;
import com.Ecommerce.Ecommerce_Website.enums.OrderStatus;
import com.Ecommerce.Ecommerce_Website.enums.Role;
import com.Ecommerce.Ecommerce_Website.repo.OrderRepository;
import com.Ecommerce.Ecommerce_Website.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private OrderRepository orderRepository;

    public AuthenticateResponse register(UserDto userDto) {
        var user = User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.USER)
                .build();
        User createdUser = userRepo.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setTotalAmount(0L);
        order.setDiscount(0L);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.Pending);
        orderRepository.save(order);


        var jwtToken = jwtService.generateToken(user.getFirstname(),user.getRole());
        return AuthenticateResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticateResponse signUp(SignUp signUp) {
        System.out.println(signUp);
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
            var jwtToken = jwtService.generateToken((signUp.getEmail()),user.getRole());
            return AuthenticateResponse.builder()
                    .token(jwtToken)
                    .build();
        }

    }


    @PostConstruct
    public void createAdminAccount(){
        Optional<User> adminacc = userRepo.findByEmail("admin@gautam.com");
        if (null==adminacc) {

            User adminGautam = new User();
            adminGautam.setEmail("admin@gautam.com");
            adminGautam.setFirstname("Gautam");
            adminGautam.setLastname("Jaganatha");
            adminGautam.setRole(Role.ADMIN);
            adminGautam.setPassword(passwordEncoder.encode("admin"));
            userRepo.save(adminGautam);

        }

        Optional<User> adminacc2 = userRepo.findByEmail("admin@gautam.com");
        if (null==adminacc2) {


            User adminSaiChand = new User();
            adminSaiChand.setEmail("admin@saichand.com");
            adminSaiChand.setFirstname("Sai");
            adminSaiChand.setLastname("Chand");
            adminSaiChand.setRole(Role.ADMIN);
            adminSaiChand.setPassword(passwordEncoder.encode("admin"));

            userRepo.save(adminSaiChand);
            String str = String.valueOf(userRepo.findByLastname("chand"));
            System.out.println(str);

        }

    }


    public List<User> getUser() {
       return userRepo.findAll();
    }
}
