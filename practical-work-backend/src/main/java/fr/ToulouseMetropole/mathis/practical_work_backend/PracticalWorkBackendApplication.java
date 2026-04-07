package fr.ToulouseMetropole.mathis.practical_work_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestClient;

import fr.ToulouseMetropole.model.ArtistAlbums;
import fr.ToulouseMetropole.model.TokenResponse;

@SpringBootApplication
public class PracticalWorkBackendApplication {

    private static final Logger log = LoggerFactory.getLogger(PracticalWorkBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PracticalWorkBackendApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public ApplicationRunner run(RestClient.Builder builder) {
        String clientId = "";     // I'd like to get the client Id through my .env file,
        String clientSecret = ""; // but I'm currently struggling to do so

        RestClient restClient = builder.baseUrl("https://api.spotify.com").build();
        return args -> {
            TokenResponse tokenResponse = restClient.post()
                    .uri("https://accounts.spotify.com/api/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body("grant_type=client_credentials&client_id=" + clientId + "&client_secret="
                            + clientSecret)
                    .retrieve()
                    .body(TokenResponse.class);

            String token = tokenResponse.access_token();

            ArtistAlbums albums = restClient
                    .get().uri("/v1/artists/0TnOYISbd1XYRBk9myaseg/albums")
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(ArtistAlbums.class);
            log.info(albums.toString());
        };
    }
}