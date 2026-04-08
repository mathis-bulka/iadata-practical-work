package fr.ToulouseMetropole.mathis.practical_work_backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import fr.ToulouseMetropole.mathis.practical_work_backend.model.ArtistObject;
import fr.ToulouseMetropole.mathis.practical_work_backend.model.SearchResponse;
import fr.ToulouseMetropole.mathis.practical_work_backend.model.TokenResponse;

@RestController
public class ArtistController {
    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    @GetMapping("/artist")
    public ArtistObject artist(@RequestParam String name) {
        RestClient restClient = RestClient.create();

        TokenResponse tokenResponse = restClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret)
                .retrieve()
                .body(TokenResponse.class);

        String token = tokenResponse.access_token();

        SearchResponse searchResponse = restClient.get()
                .uri("https://api.spotify.com/v1/search?q=artist:{name}&type=artist&limit=1", name)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(SearchResponse.class);

        return searchResponse.artists().items().get(0);
    }
}
