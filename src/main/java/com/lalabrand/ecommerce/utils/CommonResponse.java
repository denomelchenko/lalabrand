package com.lalabrand.ecommerce.utils;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse {
    private boolean success;
    private String message;
}