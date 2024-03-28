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

    public LookDTO findLook(Optional<String> previousLookId) {
        if (previousLookId.isPresent()) {
            Optional<Look> previousLook = lookRepository.findById(previousLookId.get());
            if (previousLook.isPresent()) {
                Optional<Look> nextLook = lookRepository.findFirstByCreatedAtAfterOrderByCreatedAt(previousLook.get().getCreatedAt());
                if (nextLook.isPresent()) {
                    return LookDTO.fromEntity(nextLook.get());
                } else {
                    throw new EntityNotFoundException("Look with ID: " + previousLookId.get() + " is at the end of the table");
                }
            } else {
                throw new EntityNotFoundException("Look with ID: " + previousLookId.get() + " is not exist");
            }
        } else {
            Optional<Look> firstLook = lookRepository.findFirstByOrderByCreatedAt();
            if (firstLook.isPresent()) {
                return LookDTO.fromEntity(firstLook.get());
            } else {
                throw new EntityNotFoundException("There is no data in the table looks");
            }
        }
    }

}
