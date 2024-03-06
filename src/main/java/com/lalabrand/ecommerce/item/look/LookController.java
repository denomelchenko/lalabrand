package com.lalabrand.ecommerce.item.look;

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
    public LookDto findLook(@Argument Optional<Integer> previousLookId) {
        return lookService.findLook(previousLookId);
    }
}
