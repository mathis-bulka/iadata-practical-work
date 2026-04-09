package fr.ToulouseMetropole.mathis.practical_work_backend.model.album;

import java.util.List;

import fr.ToulouseMetropole.mathis.practical_work_backend.model.utils.Image;

public record FavoriteAlbum(int id, String name, List<Image> images) {

	@Override
	public String toString() {
		return String.format(
				"FavoriteAlbum[id=%d, name='%s', images='%s']",
				id, name, images.toString());
	}
}