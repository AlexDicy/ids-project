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

    <div v-if="poiType === 'TimedPOI'">
      <div class="subtitle">Orari di apertura</div>
      <div v-for="day in 7" class="flex flex-col gap-2">
        <label class="input-label min-w-20">{{ getDayName(day) }}</label>
        <template v-for="time in openingTimes[day - 1]">
          <div v-if="time.error" class="text-red-500">
            {{ time.error || 'Orario non valido' }}
          </div>
          <div class="flex gap-2 items-center">
            <span>dalle ore </span>
            <input type="time" v-model="time.open" class="flex-1">
            <span> alle ore </span>
            <input type="time" v-model="time.close" class="flex-1" @blur="checkTimes">
            <button @click="openingTimes[day - 1].splice(openingTimes[day - 1].indexOf(time), 1)" class="text-red-500">
              <FontAwesomeIcon :icon="faRemove"/>
            </button>
          </div>
        </template>
        <div v-if="!openingTimes[day - 1].length">
          chiuso tutto il giorno
        </div>
        <div class="mb-8 text-sm flex gap-2 justify-between">
          <button @click="askCopyTimes(day)" class="button">
            Copia su tutti gli altri giorni
            <FontAwesomeIcon :icon="faCopy"/>
          </button>
          <button @click="openingTimes[day - 1].push({open: '', close: ''})" class="button new-times-button">
            Nuova riga
            <FontAwesomeIcon :icon="faAdd"/>
          </button>
        </div>
      </div>
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
import {nextTick, onMounted, PropType, ref} from 'vue';
import {faClose, faPen, faRemove, faAdd} from "@fortawesome/free-solid-svg-icons";
import {faCopy} from "@fortawesome/free-regular-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import api, {parseResponse} from "@/api";
import {selectedRole} from "@/roles";
import {getDayName, getOffsetString} from "@/dates";

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
const openingTimes = ref(Array(7));
for (let i = 0; i < 7; i++) {
  openingTimes.value[i] = [{open: '', close: '', error: false}];
}

const isLoading = ref(false);

function submit() {
  if (!name.value || !description.value) {
    alert('Nome e descrizione sono obbligatori');
    return;
  }

  let additionalData = {};
  let path = '/submit';

  if (poiType.value === 'TimedPOI') {
    checkTimes();
    for (let i = 0; i < 7; i++) {
      if (openingTimes.value[i].some(time => time.error)) {
        alert('Correggi gli orari di apertura');
        return;
      }
    }

    const offset = getOffsetString();
    additionalData = {
      openingTimes: openingTimes.value.map(times => times.map(time => time.open + offset)),
      closingTimes: openingTimes.value.map(times => times.map(time => time.close + offset))
    };
    path = '/submitTimed';
  }

  const isApproved = selectedRole.value != 'CONTRIBUTOR';

  isLoading.value = true;
  api.post(`/v1/poi${path}`, {
    name: name.value,
    coordinate: {
      latitude: props.coord[0],
      longitude: props.coord[1]
    },
    description: description.value,
    createdBy: "65b7ded68bfb54b2eacfe3b7",
    approved: isApproved,
    ...additionalData
  }).then(() => {
    isLoading.value = false;
    close(true);
    emit('submit');
    nextTick(() => alert('POI salvato correttamente' + (isApproved ? '' : ', verrà approvato da un moderatore')));
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

function askCopyTimes(day: number) {
  if (confirm('Sei sicuro di voler copiare gli orari di apertura di ' + getDayName(day) + ' su tutti gli altri giorni?')) {
    for (let i = 0; i < 7; i++) {
      if (i !== day - 1) {
        openingTimes.value[i] = openingTimes.value[day - 1].map(time => ({open: time.open, close: time.close, error: time.error}));
      } else {
      }
    }
  }
}

function checkTimes() {
  for (let i = 0; i < 7; i++) {
    for (let time of openingTimes.value[i]) {
      time.error = false;
      if (!time.open || !time.close) {
        time.error = 'Inserisci entrambi gli orari';
        continue;
      }
      const [openHour, openMinute] = time.open.split(':').map(Number);
      const [closeHour, closeMinute] = time.close.split(':').map(Number);
      if (openHour > closeHour || (openHour === closeHour && openMinute >= closeMinute)) {
        time.error = 'L\'orario di chiusura deve essere successivo a quello di apertura';
      }
    }
  }
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

input[type="time"] {
  @apply px-2 py-1 rounded border border-gray-300;
}

.new-times-button {
  @apply text-blue-500;
}

.cancel-button {
  @apply text-gray-600 px-4 rounded ml-auto;
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
