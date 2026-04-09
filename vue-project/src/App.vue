<script setup lang="ts">
import { ref } from 'vue'
import SearchBar from './components/SearchBar.vue'
import type { Artist } from './model/Artist'
import type { Albums } from './model/Albums'

const artist = ref<Artist | null>(null)
const albums = ref<Albums>({ items : []})

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
  await getArtistIdByName(name);
  if (artist.value) {
    await getArtistAlbumsById(artist.value.id);
  }
}
</script>

<template>
  <SearchBar @search="search" />

  <br /><br />
  <div v-if="albums.items.length > 0">
    <div v-for="album in albums.items" :key="album.id">
      <p>{{ album.name }}</p>
      <img :src="album.images[0]?.url" width="150" />
    </div>
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
