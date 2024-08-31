package sfa.auth_service.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sfa.auth_service.AuthUtils.JwtUtil;
import sfa.auth_service.constant.ApiErrorCodes;
import sfa.auth_service.constant.Role;
import sfa.auth_service.dto.response.AuthenticationResponse;
import sfa.auth_service.entity.RoleEntity;
import sfa.auth_service.entity.User;
import sfa.auth_service.exception.NoSuchElementFoundException;
import sfa.auth_service.repo.PermissionRepository;
import sfa.auth_service.repo.RoleRepository;
import sfa.auth_service.repo.UserRepository;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    public AuthenticationResponse authenticateUser(User user) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        Optional<User> dbUser = userRepository.findByEmail(user.getEmail());
        if(dbUser.isEmpty()){
            throw new NoSuchElementFoundException(ApiErrorCodes.NOT_FOUND.getErrorCode(), ApiErrorCodes.NOT_FOUND.getErrorMessage());
        }
        String jwtToken = jwtUtil.generateToken(dbUser.get());
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setId(UUID.randomUUID());
        user.setPassword(encodedPassword);

        List<RoleEntity> roles = new ArrayList<>();
        for (RoleEntity role : user.getRoles()) {
            Optional<RoleEntity> dbRole = roleRepository.findByName(role.getName());
            if(dbRole.isEmpty()){
                throw new NoSuchElementFoundException(ApiErrorCodes.NOT_FOUND.getErrorCode(), ApiErrorCodes.NOT_FOUND.getErrorMessage());
            }
            roles.add(dbRole.get());
        }
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        String jwtToken = jwtUtil.generateToken(savedUser);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .build();
    }
}