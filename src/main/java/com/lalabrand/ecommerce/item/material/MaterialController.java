package com.lalabrand.ecommerce.item.material;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @MutationMapping("/material")
    public MaterialDTO createMaterial(@Argument String name) {
        return materialService.create(name);
    }
}
