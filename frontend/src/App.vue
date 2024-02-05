<template>
  <div class="flex flex-col min-h-full h-full w-full">
    <header class="text-2xl font-light px-4 py-2 shadow-md">
      TownExplorer
    </header>
    <div class="flex-1 flex">
      <aside class="w-[26rem] shadow-xl">
        <!-- Search bar -->
        <div class="mx-4 my-4 px-5 border border-gray-200 rounded-full flex items-center">
          <input type="text"
                 class="flex-1 py-3 text-[0.9375rem] placeholder-gray-500/90 outline-0"
                 placeholder="Cerca su TownExplorer"
          />
          <FontAwesomeIcon :icon="faSearch" class="text-gray-500"/>
        </div>
      </aside>
      <!-- Map -->
      <div ref="map" class="flex-1"></div>
    </div>
  </div>
</template>
<script setup lang="ts">
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';
import {faSearch} from '@fortawesome/free-solid-svg-icons';
</script>
<script lang="ts">
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

export default {
  mounted() {
    const hashValues: any = window.location.hash.slice(1).split('/');
    const lat = hashValues[0] || 43.145;
    const lng = hashValues[1] || 13.06;
    const zoom = hashValues[2] || 14;
    const map = L.map(this.$refs.map).setView([lat, lng], zoom);
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    map.on('moveend', () => {
      window.location.hash = `#${map.getCenter().lat.toFixed(3)}/${map.getCenter().lng.toFixed(3)}/${map.getZoom()}`;
    });
  }
}
</script>
