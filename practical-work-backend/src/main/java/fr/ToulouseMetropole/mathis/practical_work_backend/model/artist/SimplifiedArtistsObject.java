package fr.ToulouseMetropole.mathis.practical_work_backend.model.artist;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.ToulouseMetropole.mathis.practical_work_backend.model.utils.ExternalUrls;

public record SimplifiedArtistsObject(@JsonProperty("external_urls") ExternalUrls externalUrls, String href, String id,
        String name, String type, String uri) {
}