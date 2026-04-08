<script setup lang="ts">
import { ref } from 'vue'
import SearchBar from './components/SearchBar.vue'

const artistId = ref("")
const albums = ref<any[]>([])

async function getArtistIdByName(name: string) {
  const url = new URL("http://localhost:8080/artist")
  url.searchParams.set("name", name)

  try {
    const response = await fetch(url)

    const data = await response.json()
    artistId.value = data.id
  } catch (error) {
    console.error(error)
  }
}

async function getArtistAlbumsById(id: string) {
  const url = `http://localhost:8080/artists/${id}/albums`

  try {
    const response = await fetch(url)

    const data = await response.json()
    albums.value = data.items
  } catch (error) {
    console.error(error)
  }
}

async function search(name: string) {
  await getArtistIdByName(name);
  await getArtistAlbumsById(artistId.value);
}
</script>

<template>
  <SearchBar @search="search" />

  <br /><br />
  <div v-if="albums.length > 0">
    <div v-for="album in albums" :key="album.id">
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
