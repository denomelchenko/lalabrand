package com.lalabrand.ecommerce.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserResponse {
    String userId;
    String email;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }
}
