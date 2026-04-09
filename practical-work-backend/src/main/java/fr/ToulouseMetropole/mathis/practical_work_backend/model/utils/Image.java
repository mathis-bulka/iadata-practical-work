package fr.ToulouseMetropole.mathis.practical_work_backend.model.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Image(String url) {

}
