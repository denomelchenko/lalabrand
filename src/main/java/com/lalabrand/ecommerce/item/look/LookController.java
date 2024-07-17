package com.lalabrand.ecommerce.item.look;

import org.hibernate.validator.constraints.UUID;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class LookController {
    private final LookService lookService;

    public LookController(LookService lookService) {
        this.lookService = lookService;
    }

    @QueryMapping(name = "look")
    public LookDTO findLook(@Argument Optional<String> previousLookId) {
        return lookService.findLook(previousLookId);
    }
}
