package com.lalabrand.ecommerce.item.material;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class MaterialController {
    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @MutationMapping("material")
    public MaterialDTO addMaterial(@Argument String name) {
        return materialService.create(name);
    }

    @QueryMapping("materials")
    public Set<MaterialDTO> findAllMaterials() {
        return materialService.findAll();
    }
}
