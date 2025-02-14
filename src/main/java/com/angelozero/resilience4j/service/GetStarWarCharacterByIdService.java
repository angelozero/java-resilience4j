package com.angelozero.resilience4j.service;


import com.angelozero.resilience4j.domain.StarWarsCharacter;
import com.angelozero.resilience4j.gateway.StarWartRestApiFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class GetStarWarCharacterByIdService {

    private final StarWartRestApiFeignClient starWartRestApiFeignClient;
    private final FallBackService service;


    @CircuitBreaker(name = "GetStarWarCharacterByIdService", fallbackMethod = "fallbackGetStarWarCharacterByIdService")
//    @Retry(name = "GetStarWarCharacterByIdService", fallbackMethod = "fallbackGetStarWarCharacterByIdService")
    public StarWarsCharacter execute(Integer id) {
        if (new Random().nextBoolean()) {
            return starWartRestApiFeignClient.getStarWarsCharacterById(id);
        }
        throw new RuntimeException("The Darth Sith");
    }

    public StarWarsCharacter fallbackGetStarWarCharacterByIdService(Integer id, Exception ex) {
        return service.fallbackGetStarWarCharacterByIdService(id, ex);
    }
}
