export interface Artist {
    id: string;
    name: string;
    genres?: string[];
    images?: { url: string }[];
}