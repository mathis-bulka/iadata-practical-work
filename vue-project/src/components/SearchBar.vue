<script setup lang="ts">
import { ref } from 'vue'

const emit = defineEmits(['search'])

const artistName = ref('')
</script>

<template>
    <form @submit.prevent="() => emit('search', artistName)">
        <label for="artist">Nom de l'artiste :</label>
        <br />
        <input id="artist" v-model="artistName" type="text" required>
        <button type="submit">Rechercher</button>
    </form>
</template>