package sfa.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import sfa.auth.dto.request.JwtRequest;
import sfa.auth.dto.response.UserResponseDto;

public interface UserServices extends UserDetailsService {
    void createUser(JwtRequest jwtRequest);
    String loginUser(Long mobileNo, String password);
    UserResponseDto findByMemberId(Long memberId);
    boolean validateToken(String token);
}
