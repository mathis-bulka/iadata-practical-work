package fr.ToulouseMetropole.mathis.practical_work_backend.model.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import fr.ToulouseMetropole.mathis.practical_work_backend.model.artist.Artists;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchResponse(Artists artists) {
}