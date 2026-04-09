package fr.ToulouseMetropole.mathis.practical_work_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @PostMapping("/album/favorites")
    public ResponseEntity<String> saveAlbumToFavorite(@RequestBody Map<String, String> payload) {
        String id = payload.get("id");

        if (id == null || id.isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'id' est obligatoire");
        }

        try {
            String query = "INSERT INTO favorite_albums (album_id) VALUES (?)";
            jdbcTemplate.update(query, id);

            return ResponseEntity.status(201).body("L'album " + id + " a été ajouté à vos favoris.");

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/album/favorites")
    public ResponseEntity<List<String>> getAllFavoriteAlbums() {
        try {
            String query = "SELECT album_id FROM favorite_albums";

            List<String> albums = jdbcTemplate.query(query, (rs, rowNum) -> {
                return rs.getString("album_id");
            });
            return ResponseEntity.ok(albums);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

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
