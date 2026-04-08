package fr.ToulouseMetropole.mathis.practical_work_backend.model;

import java.util.List;

public record ArtistAlbums(String href, int limit, String next, int offset, String previous, int total,
        List<SimplifiedAlbumObject> items) {

}