package fr.ToulouseMetropole.mathis.practical_work_backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    public static void main(String[] args) {
        SpringApplication.run(PracticalWorkBackendApplication.class, "0TnOYISbd1XYRBk9myaseg");
    }

    @Bean
    @Profile("!test")
    public ApplicationRunner run(RestClient.Builder builder) {
        RestClient restClient = builder.build();

        return args -> {
            String artistId = args.getSourceArgs()[0];
            TokenResponse tokenResponse = restClient.post()
                    .uri("https://accounts.spotify.com/api/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret)
                    .retrieve()
                    .body(TokenResponse.class);

            if (tokenResponse == null || tokenResponse.access_token() == null) {
                log.error("Failed to retrieve access token");
                return;
            }

            String token = tokenResponse.access_token();

            ArtistAlbums albums = restClient.get()
                    .uri("https://api.spotify.com/v1/artists/{id}/albums", artistId)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .body(ArtistAlbums.class);

            log.info(albums.toString());
        };
    }
}