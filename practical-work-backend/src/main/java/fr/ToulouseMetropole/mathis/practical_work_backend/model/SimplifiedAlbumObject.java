package fr.ToulouseMetropole.mathis.practical_work_backend.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SimplifiedAlbumObject(@JsonProperty("album_type") String albumType,
        @JsonProperty("total_tracks") int totalTracks,
        @JsonProperty("available_markets") List<String> availableMarkets,
        @JsonProperty("external_urls") ExternalUrls externalUrls,
        String href, String id, List<Image> images, String name,
        @JsonProperty("release_date") String releaseDate,
        @JsonProperty("release_date_precision") String releaseDatePrecision, Restrictions restrictions,
        String type, String uri, List<SimplifiedArtistsObject> artists,
        @JsonProperty("album_group") String albumGroup) {
}