<template>
  <div class="px-8 py-3 border-b-[1px] border-gray-300 relative">
    <a class="absolute inset-0 cursor-pointer" @click="onResultClick(result)"></a>
    <div class="mb-1 line-clamp-1">
      <span class="font-medium">{{ result.name }}</span>&nbsp;
      <span v-if="result.type === 'POI'" class="text-sm font-medium text-blue-600">
          Punto di interesse <img class="h-4 ml-1 inline align-text-bottom" src="/images/location-dot-solid.svg" alt="POI">
        </span>
      <span v-else-if="result.type === 'ITINERARY'" class="text-sm font-medium text-green-600">Itinerario</span>
    </div>
    <div class="text-sm text-gray-600 line-clamp-3">{{ result.description }}</div>
    <div v-if="hasOpeningTimes" class="text-sm text-gray-500">
      <div v-if="openingResult.open"><span class="text-green-700">Aperto</span><span> ⋅ Chiude alle ore {{ formatTimeFromDate(openingResult.nextClosingTime) }}</span></div>
      <div v-else><span class="text-red-700">Chiuso</span><span v-if="openingResult.nextOpeningTime"> ⋅ Apre alle ore {{ formatTimeFromDate(openingResult.nextOpeningTime) }}</span></div>
    </div>
    <div class="font-light text-sm text-gray-500">[ {{ result.latitude.toFixed(5) }}, {{ result.longitude.toFixed(5) }} ]</div>
  </div>
</template>

<script setup lang="ts">
import {defineProps} from 'vue';
import {CurrentOpeningResult, formatTimeFromDate, isCurrentlyOpen} from "@/dates";

const props = defineProps({
  result: {
    type: Object as any,
    required: true
  },
  onResultClick: Function
});

const hasOpeningTimes = props.result.openingTimes && props.result.closingTimes;
const openingResult: CurrentOpeningResult = hasOpeningTimes ? isCurrentlyOpen(props.result.openingTimes, props.result.closingTimes) : null;
</script>


<style scoped>

</style>
