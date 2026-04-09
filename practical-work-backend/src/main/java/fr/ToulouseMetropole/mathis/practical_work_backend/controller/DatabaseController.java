package fr.ToulouseMetropole.mathis.practical_work_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseController {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/album/favorites")
    public ResponseEntity<String> saveAlbumToFavorite(@RequestBody Map<String, String> payload) {
        String id = payload.get("id");
        String name = payload.get("name");
        String imageUrl = payload.get("imageUrl");

        if (id == null || id.isEmpty() || name == null || name.isEmpty() || imageUrl == null || imageUrl.isEmpty()) {
            return ResponseEntity.badRequest().body("Les champs 'id', 'name' et 'imageUrl' sont obligatoires.");
        }

        try {
            String query = "INSERT INTO favorite_albums (id, name, image_url) VALUES (?, ?, ?)";
            jdbcTemplate.update(query, id, name, imageUrl);

            return ResponseEntity.status(201).body("L'album " + name + " a été ajouté à vos favoris.");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/album/favorites")
    public ResponseEntity<List<Map<String, Object>>> getAllFavoriteAlbums() {
        try {
            String query = "SELECT album_id, name, image_url FROM favorite_albums";
            List<Map<String, Object>> albums = jdbcTemplate.queryForList(query);

            return ResponseEntity.ok(albums);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/album/favorites/{id}")
    public ResponseEntity<String> deleteAlbumFromFavorite(@PathVariable String id) {
        try {
            String query = "DELETE FROM favorite_albums WHERE album_id = ?";

            int rowsAffected = jdbcTemplate.update(query, id);
            if (rowsAffected > 0) {
                return ResponseEntity.ok("L'album " + id + " a été supprimé des favoris.");
            } else {
                return ResponseEntity.status(404).body("Album introuvable.");
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
