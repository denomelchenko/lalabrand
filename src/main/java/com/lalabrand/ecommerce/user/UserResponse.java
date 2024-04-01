package com.lalabrand.ecommerce.user;

import com.lalabrand.ecommerce.user.role.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    Integer id;
    Set<UserRole> userRoles;
    String email;
}
