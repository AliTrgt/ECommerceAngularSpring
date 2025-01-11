package com.example.E_commerce.service;


import com.example.E_commerce.dto.AuthRequest;
import com.example.E_commerce.dto.UserDto;
import com.example.E_commerce.exception.IdNotFoundException;
import com.example.E_commerce.mapper.UserMapper;
import com.example.E_commerce.model.Cart;
import com.example.E_commerce.model.User;
import com.example.E_commerce.repository.CartRepository;
import com.example.E_commerce.repository.UserRepository;
import com.example.E_commerce.security.JwtService;
import com.example.E_commerce.security.SecurityService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService  {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityService securityService;
    private final CartService cartService;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, SecurityService securityService, CartService cartService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.securityService = securityService;
        this.cartService = cartService;
    }

    public List<UserDto> getAllUser() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(userMapper::toDto).collect(Collectors.toList());
    }



    public UserDto findById(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IdNotFoundException(userId));
        return userMapper.toDto(user);
    }

    public Map<String,String> login(AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(),authRequest.password()));
        if(authentication.isAuthenticated()){
                String accessToken = jwtService.generateToken(authRequest.username());
                String refreshToken = jwtService.generateRefreshToken(authRequest.username());
                Map<String,String> tokens = new HashMap<>();
                tokens.put("accessToken",accessToken);
                tokens.put("refreshToken",refreshToken);
                return tokens;
        }
        throw new UsernameNotFoundException("Invalid Username :"+authRequest.username());
    }


    public Map<String,String> refresh(Map<String,String> request){
            String refreshToken = request.get("refreshToken");
            String username = jwtService.extractUser(refreshToken);
            UserDetails user = securityService.loadUserByUsername(username);
            if(jwtService.validateToken(refreshToken,user)){
                Map<String,String> tokens = new HashMap<>();
                String accessToken = jwtService.generateToken(username);
                tokens.put("accessToken",accessToken);
                return tokens;
            }
            throw new UsernameNotFoundException("User Not Found Exception"+username);
    }

    public User getUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userData = (User) authentication.getPrincipal();
        return  userData;
    }



    public UserDto getRecentUserBalance(int userId){
            User user = userRepository.findById(userId).orElse(null);

            Double cartAmount =  cartService.calculateToPrice(user.getCart().getId());

            if(user.getBalance() < cartAmount){
                    throw new RuntimeException("you don't have enough balance");
            }

            Double userBalance = user.getBalance();

            Double lastBalance = userBalance - cartAmount;

            user.setBalance(lastBalance);
            userRepository.save(user);

            return userMapper.toDto(user);
    }

    public double addBalanceToUser(int userId,double amount){
            User user = userRepository.findById(userId).orElseThrow( () -> new IdNotFoundException(userId));
            double userbalance = user.getBalance();
            double newBalance = userbalance + amount;
            user.setBalance(newBalance);
            userRepository.save(user);
            return newBalance;
    }

    public UserDto createUser(User user) {
        // Yeni bir User nesnesi oluştur
        User tempUser = new User();
        Cart cart = new Cart();

        // Gelen User nesnesinden değerleri alıyoruz
        tempUser.setUsername(user.getUsername());
        tempUser.setPassword(passwordEncoder.encode(user.getPassword()));
        tempUser.setFirstName(user.getFirstName());
        tempUser.setLastName(user.getLastName());
        tempUser.setEmail(user.getEmail());
        tempUser.setBalance(user.getBalance());
        tempUser.setAuthorities(user.getAuthorities());

        // Cart'ı User'a atıyoruz
        cart.setUser(tempUser);
        tempUser.setCart(cart);

        // Order ve Review listelerini User'a atıyoruz
        tempUser.setOrderList(user.getOrderList());
        tempUser.setReviewList(user.getReviewList());

        // User nesnesini veritabanına kaydet
        userRepository.save(tempUser);

        // User nesnesini UserDto'ya dönüştür ve döndür
        return userMapper.toDto(tempUser);
    }


    public UserDto updateUser(int userId, User user) {
        User currentUser = userRepository.findById(userId).orElseThrow(() -> new IdNotFoundException(userId));

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setEmail(user.getEmail());
        currentUser.setUsername(user.getUsername());
        currentUser.setPassword(user.getPassword());
        currentUser.setBalance(user.getBalance());

        currentUser.setAuthorities(user.getAuthorities());
        currentUser.setOrderList(user.getOrderList());
        currentUser.setReviewList(user.getReviewList());

        userRepository.save(currentUser);

        return userMapper.toDto(currentUser);
    }


    public void deleteUser(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IdNotFoundException(userId));
        if (user != null) {
            userRepository.deleteById(userId);
            log.info("Successfully Deleted");
        } else {
            log.info("Id Not Found !!");
        }
    }

}
