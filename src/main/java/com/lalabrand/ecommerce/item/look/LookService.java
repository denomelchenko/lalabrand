package com.lalabrand.ecommerce.item.look;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LookService {
    private final LookRepository lookRepository;

    public LookService(LookRepository lookRepository) {
        this.lookRepository = lookRepository;
    }

    public LookDto getLook(Optional<Integer> previousLookId) {
        if (previousLookId.isPresent()) {
            Optional<Look> nextLook = lookRepository.findById(previousLookId.get() + 1);
            return nextLook.map(LookDto::fromEntity).orElseThrow(() ->
                    new EntityNotFoundException("Look not found for id: " + (previousLookId.get() + 1)));
        } else {
            return LookDto.fromEntity(lookRepository.findById(1).orElseThrow(() ->
                    new EntityNotFoundException("Look not found for id: 1")));
        }
    }
}
