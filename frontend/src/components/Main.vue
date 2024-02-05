<template>
  <div class="flex-1 flex">
    <aside class="w-[26rem] shadow-xl">
      <!-- Search bar -->
      <SearchContainer :map="map" :markers-by-id="markersById"/>
    </aside>
    <!-- Map -->
    <div ref="map" class="flex-1"></div>
  </div>
</template>

<script lang="ts">
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import SearchContainer from "@/SearchContainer.vue";
import api from "@/api";

export default {
  components: {SearchContainer},
  data() {
    return {
      map: null as L.Map,
      markersById: new Map<string, L.Marker>()
    };
  },
  mounted() {
    const hashValues: any = window.location.hash.slice(1).split('/');
    const lat = hashValues[0] || 43.145;
    const lng = hashValues[1] || 13.06;
    const zoom = hashValues[2] || 14;
    this.map = L.map(this.$refs.map).setView([lat, lng], zoom);
    this.map.attributionControl.remove();

    this.reloadPOIs();

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
    }).addTo(this.map);
    this.map.on('moveend', () => {
      window.location.hash = `#${this.map.getCenter().lat.toFixed(4)}/${this.map.getCenter().lng.toFixed(4)}/${this.map.getZoom()}`;
      this.reloadPOIs();
    });
  },
  methods: {
    getMapBounds() {
      const bounds = this.map.getBounds();
      return {
        start: [bounds.getSouth(), bounds.getWest()],
        end: [bounds.getNorth(), bounds.getEast()]
      };
    },
    async loadPOIsForRange(start: [number, number], end: [number, number]) {
      const pois = await api.post('/v1/poi/getInRange', {
        start: {
          latitude: start[0],
          longitude: start[1]
        },
        end: {
          latitude: end[0],
          longitude: end[1]
        }
      });

      const oldMarkers = this.markersById;
      this.markersById = new Map();

      for (const poi of pois) {
        if (oldMarkers.has(poi.id)) {
          this.markersById.set(poi.id, oldMarkers.get(poi.id)!);
          oldMarkers.delete(poi.id);
        } else {
          const marker = L.marker([poi.latitude, poi.longitude]).addTo(this.map);
          marker.bindPopup(poi.name);
          this.markersById.set(poi.id, marker);
        }
      }
      // Remove old markers
      for (const [_, marker] of oldMarkers) {
        marker.remove();
      }
    },
    async reloadPOIs() {
      const bounds = this.getMapBounds();
      await this.loadPOIsForRange(bounds.start, bounds.end);
    }
  },
}
</script>
