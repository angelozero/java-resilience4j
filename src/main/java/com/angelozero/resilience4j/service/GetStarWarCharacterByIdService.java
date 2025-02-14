package com.angelozero.resilience4j.service;


import com.angelozero.resilience4j.domain.StarWarsCharacter;
import com.angelozero.resilience4j.gateway.StarWartRestApiFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class GetStarWarCharacterByIdService {

    private final StarWartRestApiFeignClient starWartRestApiFeignClient;


    //@HystrixCommand(fallbackMethod = "fallbackGetStarWarCharacterByIdService")
    public StarWarsCharacter execute(Integer id) {
        if (new Random().nextBoolean()) {
            return starWartRestApiFeignClient.getStarWarsCharacterById(id);
        }
        throw new RuntimeException("The Darth Sith");
    }

    public StarWarsCharacter fallbackGetStarWarCharacterByIdService(Integer id) {
        return new StarWarsCharacter("Angelo Zero ", null);
    }
}
