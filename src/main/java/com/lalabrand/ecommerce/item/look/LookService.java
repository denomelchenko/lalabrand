package com.lalabrand.ecommerce.item.look;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class LookService {
    private final LookRepository lookRepository;

    public LookService(LookRepository lookRepository) {
        this.lookRepository = lookRepository;
    }

    public LookDto findLook(Optional<Integer> previousLookId) {
        try {
            if (previousLookId.isPresent()) {
                return LookDto.fromEntity(lookRepository.findFirstByIdGreaterThan(previousLookId.get())
                        .orElseThrow(
                                () -> new EntityNotFoundException("Look with ID: " + previousLookId + " is at the end of the table")
                        ));
            } else {
                return LookDto.fromEntity(lookRepository.findFirstByOrderByIdAsc().orElseThrow(() ->
                        new EntityNotFoundException("There is no data in the table looks")));
            }
        } catch (EntityNotFoundException e) {
            if (Objects.equals(e.getMessage(), "Look with ID: " + previousLookId + " is at the end of the table")
                    && lookRepository.findFirstByOrderByIdAsc().isPresent()
            ) {
                return LookDto.fromEntity(lookRepository.findFirstByOrderByIdAsc().get());
            }
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
