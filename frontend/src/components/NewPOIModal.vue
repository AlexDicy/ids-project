<template>
  <dialog ref="dialog" class="p-8 rounded shadow-xl flex flex-col gap-4">
    <div class="flex gap-12 text-2xl">
      <div>Inserisci nuovo POI</div>
      <button class="close-icon" @click="close" :disabled="isLoading">
        <FontAwesomeIcon :icon="faClose"/>
      </button>
    </div>
    <div class="font-light text-sm text-gray-500">
      {{ props.coord }}
    </div>
    <div class="flex flex-col">
      <div class="font-medium">Scegli il tipo di POI</div>
      <label>
        <input type="radio" name="poiType" value="POI" v-model="poiType">
        Generico
      </label>
      <label>
        <input type="radio" name="poiType" value="TimedPOI" v-model="poiType">
        Con orari di apertura
      </label>
      <label>
        <input type="radio" name="poiType" value="TemporaryPOI" v-model="poiType">
        Temporaneo
      </label>
    </div>
    <div>
      <label>Nome<br>
        <input v-model="name" type="text" placeholder="Inserisci il nome del POI">
      </label>
    </div>
    <div>
      <label>Descrizione<br>
        <textarea v-model="description" placeholder="Inserisci una descrizione del POI"></textarea>
      </label>
    </div>
    <div class="flex gap-3">
      <button class="cancel-button" @click="close" :disabled="isLoading">Annulla</button>
      <button class="button submit-button" @click="submit" :disabled="isLoading">Salva &nbsp;<FontAwesomeIcon :icon="faPen"/>
      </button>
    </div>
  </dialog>
</template>

<script setup lang="ts">
import {onMounted, PropType, ref} from 'vue';
import {faClose, faPen} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import api, {parseResponse} from "@/api";

const props = defineProps({
  coord: {
    type: Array as PropType<[number, number]>,
    required: true
  }
});
const emit = defineEmits(['close', 'submit']);

const dialog = ref<HTMLDialogElement>(null);

onMounted(() => {
  dialog.value.showModal();
})

const close = (force = false) => {
  if (force || confirm('Sei sicuro di voler chiudere?')) {
    dialog.value.close();
    emit('close');
  }
}

const poiType = ref('POI');
const name = ref('');
const description = ref('');
const isLoading = ref(false);

function submit() {
  if (!name.value || !description.value) {
    alert('Nome e descrizione sono obbligatori');
    return;
  }

  isLoading.value = true;
  api.post('/v1/poi/submit', {
    name: name.value,
    coordinate: {
      latitude: props.coord[0],
      longitude: props.coord[1]
    },
    description: description.value,
    createdBy: "65b7ded68bfb54b2eacfe3b7"
  }).then(() => {
    close(true);
    emit('submit');
    isLoading.value = false;
  }).catch(async (e) => {
    isLoading.value = false;
    console.error(e);
    let message = e;
    if (e instanceof Response) {
      const parsed = await parseResponse(e) as any;
      message = parsed.message ?? parsed;
    }

    if (message === 'Coordinate is not valid') {
      alert('Le coordinate non rientrano nel perimetro del comune');
    } else {
      alert('Errore durante il salvataggio del POI\n\n' + message);
    }
  });
}
</script>

<style scoped>
input[type="text"], textarea {
  @apply w-full my-2 px-4 py-2 rounded border border-gray-300 placeholder-gray-500;
}

.cancel-button {
  @apply text-red-800 px-4 rounded ml-auto;
}

.cancel-button:hover {
  @apply text-opacity-80;
}

.cancel-button:disabled, .close-icon:disabled {
  @apply opacity-50 cursor-not-allowed;
}

.submit-button {
  @apply text-blue-500 border border-blue-500 shadow px-4 py-1 rounded;
}
</style>
