package fr.ToulouseMetropole.mathis.practical_work_backend.model.artist;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Artists(List<ArtistObject> items) {

}
