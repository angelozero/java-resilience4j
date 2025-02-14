package com.angelozero.resilience4j.service;

import com.angelozero.resilience4j.domain.StarWarsCharacter;
import org.springframework.stereotype.Service;

@Service
public class FallBackService {

    public StarWarsCharacter fallbackGetStarWarCharacterByIdService(Integer id, Exception ex) {
        return new StarWarsCharacter("Angelo Zero - " + ex.getMessage(), null);
    }
}
