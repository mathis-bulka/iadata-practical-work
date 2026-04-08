package fr.ToulouseMetropole.mathis.practical_work_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SimplifiedArtistsObject(@JsonProperty("external_urls") ExternalUrls externalUrls, String href, String id,
        String name, String type, String uri) {
}