export interface AlbumType {
    id: string;
    name: string;
    images: { url: string }[];
    isFavorite: boolean
}