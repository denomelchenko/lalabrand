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

    public LookDTO findLook(Optional<Integer> previousLookId) {
        if (previousLookId.isPresent()) {
            Optional<Look> nextLook = lookRepository.findFirstByIdGreaterThan(previousLookId.get());
            if (nextLook.isPresent()) {
                return LookDTO.fromEntity(nextLook.get());
            } else {
                throw new EntityNotFoundException("Look with ID: " + previousLookId + " is at the end of the table");
            }
        } else {
            Optional<Look> firstLook = lookRepository.findFirstByOrderByIdAsc();
            if (firstLook.isPresent()) {
                return LookDTO.fromEntity(firstLook.get());
            } else {
                throw new EntityNotFoundException("There is no data in the table looks");
            }
        }
    }

}
