package sfa.auth.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import sfa.auth.constant.UserRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtRequest {
    String password;
    Long mobileNo;
    List<UserRole> userRoleList;
    Long memberId;
}