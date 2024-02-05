<template>
  <div class="flex-1 flex relative">
    <aside class="sidebar" :class="{detached: !showFullSidebar, 'with-results': showSmallSidebar}">
      <!-- Search bar -->
      <div class="sidebar-content">
        <SearchContainer :map="map" :markers-by-id="markersById"
                         @toggle-full-container="showFullSidebar = $event" :show-full-sidebar="showFullSidebar"
                         @has-results="searchHasResults = $event"/>
      </div>
    </aside>
    <!-- Map -->
    <div ref="map" class="flex-1"></div>
  </div>

  <NewPOIModal v-if="showNewPOIModal" :coord="newPOICoord" @submit="reloadPOIs" @close="showNewPOIModal = false; this.selectingPositionForPOI = false"/>
</template>

<script lang="ts">
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import SearchContainer from "@/components/SearchContainer.vue";
import api from "@/api";
import {checkCoordinate, perimeter, selectingPositionForPOI} from "@/globals";
import NewPOIModal from "@/components/NewPOIModal.vue";

export default {
  components: {NewPOIModal, SearchContainer},
  data() {
    return {
      showFullSidebar: false,
      searchHasResults: false,
      map: null as L.Map,
      markersById: new Map<string, L.Marker>(),
      showNewPOIModal: false,
      newPOICoord: [0, 0],
      selectingPositionForPOI: selectingPositionForPOI,
      resizeObserver: null as ResizeObserver
    };
  },
  mounted() {
    const hashValues: any = window.location.hash.slice(1).split('/');
    const lat = hashValues[0] || 43.145;
    const lng = hashValues[1] || 13.06;
    const zoom = hashValues[2] || 14;
    this.map = L.map(this.$refs.map, {
      attributionControl: false,
    }).setView([lat, lng], zoom);
    this.map.zoomControl.setPosition('bottomright');

    this.reloadPOIs();
    L.Marker.prototype.options.icon = new L.icon({
      iconUrl: '/images/location-dot-border.svg',
      iconSize: [24, 32],
      iconAnchor: [12, 32],
      popupAnchor: [0, -32],
      shadowUrl: '/images/marker-shadow.png',
      shadowSize: [32, 32],
      shadowAnchor: [12, 32]
    });
    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 19,
    }).addTo(this.map);
    this.map.on('moveend', () => {
      window.location.hash = `#${this.map.getCenter().lat.toFixed(4)}/${this.map.getCenter().lng.toFixed(4)}/${this.map.getZoom()}`;
      this.reloadPOIs();
    });
    this.map.on('click', this.onMapClick);

    L.polyline(perimeter, {
      dashArray: '5, 10',
      weight: 2.5,
    }).addTo(this.map);

    this.resizeObserver = new ResizeObserver(() => {
      this.map.invalidateSize();
    });
    this.resizeObserver.observe(this.$refs.map);
  },
  beforeUnmount() {
    this.resizeObserver.disconnect();
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
    },
    onMapClick(e: L.LeafletMouseEvent) {
      if (this.selectingPositionForPOI) {
        this.newPOICoord = [e.latlng.lat, e.latlng.lng];
        if (checkCoordinate(this.newPOICoord)) {
          this.showNewPOIModal = true;
        } else {
          alert('Il punto selezionato non è all\'interno del perimetro del comune!');
        }
      }
    }
  },
  computed: {
    showSmallSidebar() {
      return this.searchHasResults && !this.showFullSidebar;
    }
  }
}
</script>

<style scoped>
.sidebar {
  @apply w-[26rem] relative;
}

.sidebar.detached {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 10000;
}

.sidebar:not(.detached) {
  z-index: 1000;
  @apply bg-white shadow-xl;
}

.sidebar.with-results {
  @apply bg-white rounded-br-xl shadow-xl;
}

.sidebar:not(.detached) .sidebar-content {
  @apply absolute left-0 top-0 right-0 bottom-0 overflow-y-auto;
}
</style>
