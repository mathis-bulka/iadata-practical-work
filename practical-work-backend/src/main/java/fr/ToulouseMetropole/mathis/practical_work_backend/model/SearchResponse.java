package fr.ToulouseMetropole.mathis.practical_work_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchResponse(Artists artists) {
}