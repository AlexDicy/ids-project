<template>
  <div class="mx-4 my-4 px-5 border border-gray-200 rounded-full flex items-center">
    <input type="text" v-model="searchQuery"
           class="flex-1 py-3 text-[0.9375rem] placeholder-gray-500/90 outline-0"
           placeholder="Cerca su TownExplorer"
    />
    <FontAwesomeIcon :icon="faSearch" class="text-gray-500"/>
  </div>
  <div>
    <div v-for="result of results" :key="result.id" class="px-4 py-3 border-b-[1px] border-gray-300 relative">
      <a class="absolute inset-0 cursor-pointer" @click="onResultClick(result)"></a>
      <div class="mb-1">
        <span class="font-medium">{{ result.name }}</span>&nbsp;
        <span v-if="result.type === 'POI'" class="text-sm font-medium text-blue-600">Punto di interesse</span>
        <span v-else-if="result.type === 'ITINERARY'" class="text-sm font-medium text-green-600">Itinerario</span>
      </div>
      <div class="text-sm text-gray-600 line-clamp-3">{{ result.description }}</div>
      <div class="text-sm text-gray-600">{{ result.latitude }}, {{ result.longitude }}</div>
    </div>

    <div class="mx-4 my-8 text-sm text-gray-600 text-center">
      <span v-if="results.length === 0">Nessun risultato</span>
      <span v-else>Hai raggiunto la fine dei risultati</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faSearch} from "@fortawesome/free-solid-svg-icons";
import {ref, watch} from "vue";
import api from "@/api";
import {useRoute, useRouter} from "vue-router";

const props = defineProps({
  map: L.Map,
  markersById: Map<string, L.Marker>
});

let debounceTimeout: number;

const debouncedSearch = () => {
  clearTimeout(debounceTimeout);
  debounceTimeout = setTimeout(() => {
    search(searchQuery.value);
  }, 200);
};

const router = useRouter();
const route = useRoute();
const searchQuery = ref(route.query.search as string || '');
watch(searchQuery, debouncedSearch);
search(searchQuery.value);

const results = ref([]);

function search(query: string) {
  if (query.length < 3) {
    return;
  }

  router.push({path: route.path, query: {search: query}, hash: route.hash});

  api.post('/v1/search', {query}).then(data => {
    results.value = data;
  });
}

function onResultClick(result: any) {
  props.map.setView([result.latitude, result.longitude], 16);
  const marker = props.markersById.get(result.id);
  if (marker) {
    marker.openPopup();
  }
}
</script>
