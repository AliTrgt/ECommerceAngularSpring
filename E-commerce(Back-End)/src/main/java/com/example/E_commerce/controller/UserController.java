package com.example.E_commerce.controller;


import com.example.E_commerce.dto.AuthRequest;
import com.example.E_commerce.dto.UserDto;
import com.example.E_commerce.mapper.UserMapper;
import com.example.E_commerce.model.User;
import com.example.E_commerce.repository.UserRepository;
import com.example.E_commerce.security.JwtService;
import com.example.E_commerce.security.SecurityService;
import com.example.E_commerce.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService, SecurityService securityService, SecurityService securityService1, UserRepository userRepository, UserMapper userMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.securityService = securityService1;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userList = userService.getAllUser();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable int userId) {
        UserDto userDto = userService.findById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        UserDto userDto = userService.createUser(user);
        return new ResponseEntity<>(userDto,  HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody User user, @PathVariable int userId) {
        UserDto userDto = userService.updateUser(userId, user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Transactional
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted ", HttpStatus.OK);
    }


    @GetMapping("/getUserInfo")
        public User getUserInfo(){
            return userService.getUserInfo();
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody AuthRequest authRequest){
            return userService.login(authRequest);
    }


    @PostMapping("/{userId}")
    public double addBalanceToUser(@PathVariable int userId,@RequestParam double amount){
            return userService.addBalanceToUser(userId,amount);
    }

    @PostMapping("/refreshToken")
    public Map<String,String> refreshToken(@RequestBody Map<String,String> request){
            return userService.refresh(request);
    }

    @PostMapping("/generateToken")
    public String generateToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(),authRequest.password()));
        if(authentication.isAuthenticated()){
                return jwtService.generateToken(authRequest.username());
        }
        throw new UsernameNotFoundException("username not found :"+authRequest.username());
    }

    @GetMapping("/admin")
    public String admin(){
            return "ADMIN !!";
    }
}
