package sfa.auth_service.controller;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sfa.auth_service.AuthUtils.JwtUtil;
import sfa.auth_service.dto.response.AuthenticationResponse;
import sfa.auth_service.entity.User;
import sfa.auth_service.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil helper;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> addUser(@NonNull @RequestBody User user) {
        AuthenticationResponse authenticationResponse =  userService.registerUser(user);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUsers(@NonNull @RequestBody User user) {
        AuthenticationResponse authenticationResponse =  userService.authenticateUser(user);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is the admin endpoint. Only users with the ADMIN role can access this.";
    }

    @PreAuthorize("hasAuthority('READ_PRIVILEGES')")
    @GetMapping("/user")
    public String userEndpoint() {
        return "This is the user endpoint. Users with ADMIN or MEMBER roles can access this.";
    }

    @GetMapping("/validateToken")
    public ResponseEntity<String> validateTok(@RequestParam String token) {
        try {
            if (helper.validateOnlyToken(token)) {
                return new ResponseEntity<>("valid token", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Not valid token", HttpStatus.UNAUTHORIZED);
            }
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("Expired token", HttpStatus.UNAUTHORIZED);
        }
    }
}