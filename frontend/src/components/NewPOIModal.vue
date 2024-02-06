<template>
  <dialog ref="dialog" class="p-8 rounded shadow-2xl flex flex-col gap-4">
    <div class="flex gap-12 text-2xl">
      <div>Inserisci nuovo POI</div>
      <button class="close-icon ml-auto" @click="close" :disabled="isLoading">
        <FontAwesomeIcon :icon="faClose"/>
      </button>
    </div>
    <div class="font-light text-sm text-gray-500">
      {{ props.coord }}
    </div>
    <div class="flex flex-col">
      <div class="subtitle">Scegli il tipo</div>
      <div class="flex gap-2 mt-2">
        <input type="radio" name="poiType" value="POI" v-model="poiType" id="poiTypeGeneric">
        <label class="radio-label" for="poiTypeGeneric">
          <span>Semplice</span>
          <div>
            Punto di interesse generico, come un monumento o un punto di riferimento.
          </div>
        </label>
        <input type="radio" name="poiType" value="TimedPOI" v-model="poiType" id="poiTypeTimed">
        <label class="radio-label" for="poiTypeTimed">
          <span>Con orari</span>
          <div>
            Per attività che hanno un orario di apertura e chiusura come bar e negozi.
          </div>
        </label>
        <input type="radio" name="poiType" value="TemporaryPOI" v-model="poiType" id="poiTypeTemporary">
        <label class="radio-label" for="poiTypeTemporary">
          <span>Temporaneo</span>
          <div>
            Eventi, mercati e manifestazioni temporanee. POI che hanno una data di inizio e fine.
          </div>
        </label>
      </div>
    </div>

    <div>
      <div class="subtitle">Inserisci i dettagli</div>
      <label class="input-label" for="poiName">Nome</label>
      <input v-model="name" type="text" placeholder="Inserisci il nome del POI" id="poiName">
    </div>
    <div>
      <label class="input-label" for="poiDescription">Descrizione</label>
      <textarea v-model="description" placeholder="Inserisci una descrizione del POI" id="poiDescription"></textarea>
    </div>
    <div class="flex gap-3">
      <button class="cancel-button" @click="close" :disabled="isLoading">Annulla</button>
      <button class="button submit-button" @click="submit" :disabled="isLoading">Salva
        <FontAwesomeIcon :icon="faPen"/>
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
.radio-label {
  transition: all 100ms;
  @apply border-4 border-gray-300 rounded-lg p-3 shadow-lg cursor-pointer select-none;
}

input[type="radio"] {
  display: none;
}

input:checked + .radio-label {
  @apply border-blue-500 shadow-md;
}

input[type="text"], textarea {
  @apply w-full my-2 px-4 py-2 rounded border border-gray-300 placeholder-gray-500;
}

.radio-label span {
  @apply font-medium uppercase text-gray-600;
}

.radio-label div {
  @apply text-sm text-gray-500 max-w-32 mt-1;
}

.radio-label:hover {
  @apply border-blue-500 shadow-md;
}

.subtitle {
  @apply text-xl text-gray-800 mt-4 pb-2 mb-2 border-b;
}

.input-label {
  @apply text-sm text-gray-600 font-medium uppercase;
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
