<script setup lang="ts">
import type { AlbumType } from '../model/AlbumType'

const props = defineProps<{
    album: AlbumType
}>()

const emit = defineEmits(['favorite-toggled'])

const toggleFavorite = async () => {
    const url = `http://localhost:8080/album/favorites${props.album.isFavorite ? '/' + props.album.id : ''}`
    const method = props.album.isFavorite ? 'DELETE' : 'POST'
    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: !props.album.isFavorite ? JSON.stringify({ id: props.album.id, name: props.album.name, imageUrl: props.album.images[0]?.url }) : null
        })

        if (response.ok) {
            props.album.isFavorite = !props.album.isFavorite
        } else {
            console.error("Erreur API:", await response.text())
        }
    } catch (error) {
        console.error("Erreur réseau:", error)
    }
    emit('favorite-toggled')
}
</script>

<template>
    <div>
        <p>{{ props.album.name }}</p>
        <img :src="props.album.images[0]?.url" width="150" />
        <br />

        <button @click="toggleFavorite">
            {{ props.album.isFavorite ? 'Retirer des favoris' : 'Ajouter aux favoris' }}
        </button>
    </div>
</template>
