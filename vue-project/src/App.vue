<script setup lang="ts">
import { onMounted, ref } from 'vue'
import Album from './components/Album.vue'
import SearchBar from './components/SearchBar.vue'
import type { Artist } from './model/Artist'
import type { Albums } from './model/Albums'
import type { AlbumType } from './model/AlbumType'

const artist = ref<Artist | null>(null)
const albums = ref<Albums>({ items: [] })
const favorites = ref<AlbumType[]>([])

async function getFavorites() {
    const url = "http://localhost:8080/album/favorites"

    try {
        const response = await fetch(url)
        if (!response.ok) throw new Error("Erreur lors de la récupération des favoris")

        const data = await response.json()
        const formattedAlbums: AlbumType[] = []

        for (const fav of data) {
            formattedAlbums.push({
                id: fav.album_id,
                name: fav.name,
                images: [{ url: fav.image_url }],
                isFavorite: true
            })
        }

        favorites.value = formattedAlbums

    } catch (error) {
        console.error("Erreur API:", error)
    }
}

async function getArtistIdByName(name: string) {
    const url = new URL("http://localhost:8080/artist")
    url.searchParams.set("name", name)

    try {
        const response = await fetch(url)

        const data: Artist = await response.json()
        artist.value = data
    } catch (error) {
        console.error(error)
    }
}

async function getArtistAlbumsById(id: string) {
    const url = `http://localhost:8080/artists/${id}/albums`

    try {
        const response = await fetch(url)

        const data = await response.json()
        albums.value.items = data.items
    } catch (error) {
        console.error(error)
    }
}

async function search(name: string) {
    await getFavorites()
    await getArtistIdByName(name)
    if (artist.value && artist.value.id) {
        await getArtistAlbumsById(artist.value.id)
    }
    for (const album of albums.value.items) {   // on parcourt les albums
        for (const fav of favorites.value) {    // on parcourt les favoris
            if (album.id === fav.id) {          // si l'id d'un album est dans les favoris
                album.isFavorite = true         // on le met à jour
                break                           // pas besoin d'aller plus loin
            }
        }
    }
}

async function favoriteToggled() {
    await getFavorites()
}

onMounted(() => {
    getFavorites()
})
</script>

<template>
    <section v-if="favorites.length > 0">
        <h2>Albums Favoris</h2>
        <Album v-for="favorite in favorites" :key="favorite.id" :album="favorite" @favorite-toggled="favoriteToggled" />
    </section>

    <SearchBar @search="search" />

    <br /><br />
    <div v-if="albums.items.length > 0">
        <Album v-for="album in albums.items" :key="album.id" :album="album" @favorite-toggled="favoriteToggled" />
    </div>
</template>

<style scoped>
header {
    line-height: 1.5;
}

.logo {
    display: block;
    margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
    header {
        display: flex;
        place-items: center;
        padding-right: calc(var(--section-gap) / 2);
    }

    .logo {
        margin: 0 2rem 0 0;
    }

    header .wrapper {
        display: flex;
        place-items: flex-start;
        flex-wrap: wrap;
    }
}
</style>
