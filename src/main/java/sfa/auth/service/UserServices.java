package sfa.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import sfa.auth.constant.UserRole;
import sfa.auth.dto.request.JwtRequest;
import sfa.auth.dto.response.UserResponseDto;

import java.util.List;


public interface UserServices extends UserDetailsService {
    void createUser(JwtRequest jwtRequest);
    String loginUser(Long mobileNo, String password);
    UserResponseDto findByMemberId(Long memberId);
    boolean validateToken(String token);
    List<UserRole> getAllRoleByMobileNo(Long mobileNo);
}
