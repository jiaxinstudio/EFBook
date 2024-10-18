package com.book.ef.controller;

import com.book.ef.config.BookUserDetails;
import com.book.ef.dto.LoginResponse;
import com.book.ef.dto.LoginUserDto;
import com.book.ef.dto.RegisterUserDto;
import com.book.ef.dto.UpdateUserDto;
import com.book.ef.entity.User;
import com.book.ef.service.JwtService;
import com.book.ef.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserDto userDto) {
        if (userService.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        User user = userService.registerUser(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto user) {
        User authenticatedUser = userService.login(user.getUsername(), user.getPassword());
        BookUserDetails bookUserDetails = new BookUserDetails(authenticatedUser);

        String jwtToken = jwtService.generateToken(bookUserDetails);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable Long id,
                                            @RequestBody UpdateUserDto updateUserDto) {
        boolean updated = userService.updateUserInfo(id, updateUserDto);
        return ResponseEntity.status(updated ? HttpStatus.NO_CONTENT : HttpStatus.NOT_MODIFIED).build();
    }
}
