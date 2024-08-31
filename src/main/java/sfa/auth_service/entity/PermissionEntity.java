package sfa.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sfa.auth_service.constant.Permission;

import java.util.List;
import java.util.Set;


@Entity(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Permission name;

    @ManyToMany(mappedBy = "permissions")
    private List<RoleEntity> roles;

}