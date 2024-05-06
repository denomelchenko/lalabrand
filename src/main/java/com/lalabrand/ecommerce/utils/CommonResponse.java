package com.lalabrand.ecommerce.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommonResponse {
    private boolean success;
    private String message;
}
