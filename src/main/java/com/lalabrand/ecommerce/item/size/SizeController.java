package com.lalabrand.ecommerce.item.size;

import com.lalabrand.ecommerce.utils.CommonResponse;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class SizeController {
    private final SizeService sizeService;

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @QueryMapping(name = "sizes")
    public Set<SizeDTO> findAllSizes() {
        return sizeService.findAll();
    }

    @MutationMapping(name = "size")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SizeDTO createSize(@Argument SizeInput sizeInput) {
        return sizeService.save(sizeInput);
    }
}
