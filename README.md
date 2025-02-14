# SpringBoot e Resilience4j

## O que é Resilience4j?

O [Resilience4j](https://resilience4j.readme.io/docs/getting-started) é uma biblioteca leve para Java que fornece padrões de resiliência, como Circuit Breaker, Retry, Rate Limiter, Bulkhead, e Timeout. Essas funcionalidades ajudam a proteger a aplicação contra falhas de serviços externos, garantindo que a aplicação continue a operar mesmo quando um serviço falha.

---

### Circuit Breaker e Retry

- O padrão Circuit Breaker impede que a aplicação faça chamadas a um serviço que já falhou várias vezes consecutivas, permitindo que o serviço tenha tempo para se recuperar.
- O padrão Retry permite que a aplicação tente novamente uma operação falha várias vezes antes de desistir.

```java
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
@AllArgsConstructor
public class GetStarWarCharacterByIdService {

    private final StarWartRestApiFeignClient starWartRestApiFeignClient;
    private final FallBackService fallBackService;


    @CircuitBreaker(name = "GetStarWarCharacterByIdService", fallbackMethod = "fallBackGetStarWarCharacterById")
    //@Retry(name = "GetStarWarCharacterByIdService", fallbackMethod = "fallBackGetStarWarCharacterById")
    public StarWarsCharacter execute(Integer id) {
        if (new Random().nextBoolean()) {
            return starWartRestApiFeignClient.getStarWarsCharacterById(id);
        }
        throw new RuntimeException("The Darth Sith");
    }

    public StarWarsCharacter fallBackGetStarWarCharacterById(Integer id, Exception ex) {
        return fallBackService.execute(id, ex);
    }
}
```

### Fallback

- O método de fallback é chamado quando a operação original falha. Ele fornece uma resposta alternativa, permitindo que a aplicação continue a funcionar.

```java
@Service
public class FallbackService {

    public StarWarsCharacter fallbackGetStarWarCharacterByIdService(Integer id, Exception ex) {
        return new StarWarsCharacter("Angelo Zero - " + ex.getMessage(), null);
    }
}
```

### Configuração

- Certifique-se de que as seguintes dependências estão incluídas no seu `pom.xml`:

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

- Por fim certifique-se de a classe principal esteja com a annotation `@EnableAspectJAutoProxy`
```java
@EnableAspectJAutoProxy
@EnableFeignClients
@SpringBootApplication
public class Resilience4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jApplication.class, args);
    }

}
```

- A aplicação estará disponível em `http://localhost:8080/api/{id}`, onde `{id}` é o ID do personagem Star Wars que você deseja consultar.