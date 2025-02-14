package com.angelozero.resilience4j.controller;


import com.angelozero.resilience4j.domain.StarWarsCharacter;
import com.angelozero.resilience4j.service.GetStarWarCharacterByIdService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {

    private final GetStarWarCharacterByIdService getStarWarCharacterByIdService;

    @GetMapping("/{id}")
    public ResponseEntity<StarWarsCharacter> getStarWarsCharacter(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(getStarWarCharacterByIdService.execute(id));
    }
}
