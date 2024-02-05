<template>
  <div class="mx-4 my-4 px-[1.125rem] border rounded-full flex items-center bg-white"
       :class="{'with-background': showBackground, 'without-background': !showBackground}">
    <input type="text" v-model="searchQuery" ref="searchInput"
           class="flex-1 py-3 text-[0.9375rem] placeholder-gray-500/90 outline-0"
           placeholder="Cerca su TownExplorer" @keydown.enter="onSearchSubmit"
    />
    <div class="flex gap-4">
      <FontAwesomeIcon :icon="faSearch" class="text-gray-500 cursor-pointer p-2" @click="onSearchSubmit"/>
      <FontAwesomeIcon :icon="faX" class="text-gray-500 cursor-pointer p-2" v-if="showFullSidebar" @click="closeResults(); searchInput.focus();"/>
    </div>
  </div>

  <div v-if="showFullSidebar">
    <div class="px-8 py-1 text-lg">Risultati</div>
    <div v-for="result of results" :key="result.id" class="px-8 py-3 border-b-[1px] border-gray-300 relative">
      <a class="absolute inset-0 cursor-pointer" @click="onResultClick(result)"></a>
      <div class="mb-1">
        <span class="font-medium">{{ result.name }}</span>&nbsp;
        <span v-if="result.type === 'POI'" class="text-sm font-medium text-blue-600">
          Punto di interesse <img class="h-4 ml-1 inline align-text-bottom" src="/images/location-dot-solid.svg" alt="POI">
        </span>
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
  <div v-else-if="results.length > 0" class="mb-4">
    <div v-for="result of results.slice(0, 5)" :key="result.id" class="px-8 py-3 flex gap-3 items-center relative">
      <a class="absolute inset-0 cursor-pointer" @click="onResultClick(result)"></a>
      <img v-if="result.type === 'POI'" class="h-5" src="/images/location-dot-solid.svg" alt="POI">
      <span class="ml-2">{{ result.name }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {faSearch, faX} from "@fortawesome/free-solid-svg-icons";
import {computed, ref, watch} from "vue";
import api from "@/api";
import {useRoute, useRouter} from "vue-router";

const props = defineProps({
  map: L.Map,
  markersById: Map<string, L.Marker>,
  showFullSidebar: Boolean
});

const emit = defineEmits(['toggle-full-container', 'has-results']);
const showBackground = computed(() => props.showFullSidebar || results.value.length > 0);

let debounceTimeout: number;

const debouncedSearch = () => {
  clearTimeout(debounceTimeout);
  debounceTimeout = setTimeout(() => {
    search(searchQuery.value);
  }, 200);
};

const results = ref([]);
watch(results, () => emit('has-results', results.value.length > 0));

const router = useRouter();
const route = useRoute();
const searchQuery = ref(route.query.search as string || '');
watch(searchQuery, debouncedSearch);
search(searchQuery.value);
if (searchQuery.value.length > 0) {
  emit('toggle-full-container', true);
}

function search(query: string) {
  if (query.length === 0) {
    results.value = [];
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

const searchInput = ref<HTMLInputElement>();

function onSearchSubmit() {
  if (searchQuery.value.length > 0) {
    emit('toggle-full-container', true);
    search(searchQuery.value);
  } else {
    searchInput.value.focus();
  }
}

function closeResults() {
  searchQuery.value = '';
  search('');
  emit('toggle-full-container', false);
}
</script>

<style scoped>
.with-background {
  @apply border-gray-200;
}

.without-background {
  @apply border-transparent shadow-md;
}
</style>
