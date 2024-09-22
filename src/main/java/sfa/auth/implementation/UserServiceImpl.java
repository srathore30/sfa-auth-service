package sfa.auth.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sfa.auth.AuthUtils.JwtHelper;
import sfa.auth.config.AuthConfig;
import sfa.auth.constant.ApiErrorCodes;
import sfa.auth.dto.request.JwtRequest;
import sfa.auth.dto.response.UserResponseDto;
import sfa.auth.entity.UserEntity;
import sfa.auth.exception.NoSuchElementFoundException;
import sfa.auth.repo.UserRepository;
import sfa.auth.service.UserServices;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;

    public UserServiceImpl(UserRepository userRepository, JwtHelper jwtHelper) {
        this.userRepository = userRepository;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public void createUser(JwtRequest jwtRequest) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByMobileNo(jwtRequest.getMobileNo());
        if(optionalUserEntity.isPresent()){
            throw new NoSuchElementFoundException(ApiErrorCodes.ALREADY_EXISTS.getErrorCode(), ApiErrorCodes.ALREADY_EXISTS.getErrorMessage());
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserRoleList(jwtRequest.getUserRoleList());
        userEntity.setPassword(AuthConfig.passwordEncoder().encode(jwtRequest.getPassword()));
        userEntity.setMemberId(jwtRequest.getMemberId());
        userEntity.setMobileNo(jwtRequest.getMobileNo());
        userRepository.save(userEntity);
    }

    @Override
    public String loginUser(Long mobileNo, String password) {
        UserDetails userDetails = loadUserByUsername(mobileNo.toString());
        AuthConfig.matches(password, userDetails.getPassword());
        if(AuthConfig.matches(password, userDetails.getPassword())){
            return jwtHelper.generateToken(userDetails);
        }
        throw new NoSuchElementFoundException(ApiErrorCodes.INVALID_USERNAME_OR_PASSWORD. getErrorCode(), ApiErrorCodes.INVALID_USERNAME_OR_PASSWORD.getErrorMessage());
    }

    @Override
    public UserResponseDto findByMemberId(Long memberId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByMemberId(memberId);
        if(optionalUserEntity.isEmpty()){
            throw new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage());
        }
        return new UserResponseDto(optionalUserEntity.get().getId(), optionalUserEntity.get().getMobileNo(), optionalUserEntity.get().getUserRoleList());
    }

    @Override
    public boolean validateToken(String token) {
        return jwtHelper.validateOnlyToken(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByMobileNo(Long.valueOf(username));
        if(optionalUserEntity.isEmpty()){
            throw new NoSuchElementFoundException(ApiErrorCodes.USER_NOT_FOUND.getErrorCode(), ApiErrorCodes.USER_NOT_FOUND.getErrorMessage());
        }
        return optionalUserEntity.get();
    }
}
