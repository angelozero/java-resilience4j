# Resilience4j Application

Este projeto é uma aplicação Java construída com Spring Boot que utiliza o Resilience4j para implementar padrões de resiliência em chamadas de APIs externas. O Resilience4j oferece soluções para lidar com falhas de serviço, incluindo **Circuit Breaker**, **Retry**, e **Fallback**, ajudando a melhorar a robustez e a resiliência da aplicação.

## O que é Resilience4j?

O Resilience4j é uma biblioteca leve para Java que fornece padrões de resiliência, como Circuit Breaker, Retry, Rate Limiter, Bulkhead, e Timeout. Essas funcionalidades ajudam a proteger a aplicação contra falhas de serviços externos, garantindo que a aplicação continue a operar mesmo quando um serviço falha.

## Funcionalidades Implementadas

### Circuit Breaker

O padrão Circuit Breaker impede que a aplicação faça chamadas a um serviço que já falhou várias vezes consecutivas, permitindo que o serviço tenha tempo para se recuperar. Abaixo, um exemplo de como o Circuit Breaker é implementado na classe `GetStarWarCharacterByIdService`:

```java
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
@AllArgsConstructor
public class GetStarWarCharacterByIdService {

    @CircuitBreaker(name = "GetStarWarCharacterByIdService", fallbackMethod = "fallbackMethod")
    public StarWarsCharacter execute(Integer id) {
        // Lógica para chamar o serviço externo
    }

    public StarWarsCharacter fallbackMethod(Integer id, Exception ex) {
        // Lógica de fallback
    }
}
```

### Retry

O padrão Retry permite que a aplicação tente novamente uma operação falha várias vezes antes de desistir. Aqui está um exemplo de como o Retry é usado:

```java
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {

    @Retry(name = "nameTest", fallbackMethod = "fallBackTest")
    @GetMapping("/{id}")
    public ResponseEntity<StarWarsCharacter> askToAi(@PathVariable("id") Integer id) {
        // Lógica para chamar o serviço externo
    }
}
```

### Fallback

O método de fallback é chamado quando a operação original falha. Ele fornece uma resposta alternativa, permitindo que a aplicação continue a funcionar. Aqui está um exemplo de uma implementação de fallback:

```java
@Service
public class FallbackService {

    public StarWarsCharacter fallbackGetStarWarCharacterByIdService(Integer id, Exception ex) {
        return new StarWarsCharacter("Angelo Zero - " + ex.getMessage(), null);
    }
}
```

No serviço original, o método de fallback é referenciado:

```java
public StarWarsCharacter fallbackMethod(Integer id, Exception ex) {
    return fallbackService.fallbackGetStarWarCharacterByIdService(id, ex);
}
```

## Configuração

### Dependências

Certifique-se de que as seguintes dependências estão incluídas no seu `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.7</version>
</dependency>
```

### Configurações no `application.properties`

Certifique-se de que as seguintes configurações estão presentes no seu arquivo `application.properties`:

```properties
spring.application.name=java-resilience4j
star.wars.client.url=https://swapi.dev/api/
star.wars.client.path=people/{id}
```

A aplicação estará disponível em `http://localhost:8080/api/{id}`, onde `{id}` é o ID do personagem Star Wars que você deseja consultar.