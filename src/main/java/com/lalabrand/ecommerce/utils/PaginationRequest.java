package com.lalabrand.ecommerce.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {
    Integer pageSize;
    Integer pageOffset;

    public PageRequest toPageRequest() {
        return PageRequest.of(pageOffset, pageSize);
    }
}
