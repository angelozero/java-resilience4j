package com.angelozero.resilience4j.domain;

import java.util.List;

public record StarWarsCharacter(String name, List<String> films) {
}
