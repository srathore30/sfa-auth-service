package sfa.auth.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import sfa.auth.constant.UserRole;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    Long userId;
    Long mobileNo;
    List<UserRole> userRoleList;
}
