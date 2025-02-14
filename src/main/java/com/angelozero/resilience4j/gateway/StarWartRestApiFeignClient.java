package com.angelozero.resilience4j.gateway;


import com.angelozero.resilience4j.domain.StarWarsCharacter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "star.wars.client", url = "${star.wars.client.url}")
public interface StarWartRestApiFeignClient {

    @GetMapping(path = "${star.wars.client.path}")
    StarWarsCharacter getStarWarsCharacterById(@PathVariable("id") Integer id);
}
