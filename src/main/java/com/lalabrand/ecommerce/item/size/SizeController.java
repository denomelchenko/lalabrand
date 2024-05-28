package com.lalabrand.ecommerce.item.size;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class SizeController {
    private final SizeService sizeService;

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }



    @MutationMapping(name = "size")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SizeDTO createSize(@Argument SizeInput sizeInput) {
        return sizeService.save(sizeInput);
    }
}
