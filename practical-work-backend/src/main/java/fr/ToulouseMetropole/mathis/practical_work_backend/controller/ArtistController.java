package fr.ToulouseMetropole.mathis.practical_work_backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import fr.ToulouseMetropole.mathis.practical_work_backend.model.album.ArtistAlbums;
import fr.ToulouseMetropole.mathis.practical_work_backend.model.artist.ArtistObject;
import fr.ToulouseMetropole.mathis.practical_work_backend.model.utils.SearchResponse;
import fr.ToulouseMetropole.mathis.practical_work_backend.model.utils.TokenResponse;

@RestController
public class ArtistController {
    @Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    private final RestClient restClient = RestClient.create();

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/artist")
    public ArtistObject artist(@RequestParam String name) {
        String token = this.getAccessToken();

        SearchResponse searchResponse = restClient.get()
                .uri("https://api.spotify.com/v1/search?q=artist:{name}&type=artist&limit=1", name)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(SearchResponse.class);

        return searchResponse.artists().items().get(0);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/artists/{id}/albums")
    public ArtistAlbums albums(@PathVariable String id) {
        String token = this.getAccessToken();

        ArtistAlbums albums = restClient.get()
                .uri("https://api.spotify.com/v1/artists/{id}/albums", id)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(ArtistAlbums.class);

        return albums;
    }

    private String getAccessToken() {
        TokenResponse tokenResponse = restClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret)
                .retrieve()
                .body(TokenResponse.class);
        return tokenResponse.access_token();
    }
}