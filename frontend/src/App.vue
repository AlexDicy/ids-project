<template>
  <div class="flex flex-col min-h-full h-full w-full">
    <header class="header px-4 py-2 shadow-md flex items-center flex-wrap">
      <router-link to="/" class="text-2xl font-light mr-auto">TownExplorer</router-link>

      <div class="flex gap-2">
        <a v-if="canSubmitContent()" @click="selectingPositionForPOI = true" class="button">
          Aggiungi POI <FontAwesomeIcon :icon="faPlus"/>
        </a>

        <a v-if="selectedRole === 'TURISTA'" @click="selectedRole = 'CURATORE'" class="button">
          Login <FontAwesomeIcon :icon="faSignIn"/>
        </a>
        <a v-else @click="selectedRole = 'TURISTA'" class="button">
          Logout <FontAwesomeIcon :icon="faSignOut"/>
        </a>
      </div>


      <div class="ml-4">
        <select v-model="selectedRole">
          <option v-for="(role, key) of roles" :value="key">{{ role }}</option>
        </select>
      </div>

    </header>

    <div v-if="selectingPositionForPOI" class="w-full bg-blue-200 shadow-md p-4 font-medium">
      Clicca un punto sulla mappa per aggiungere il nuovo POI
      <a @click="selectingPositionForPOI = false" class="button ml-2 bg-white">
        Annulla
      </a>
    </div>

    <router-view></router-view>
  </div>
</template>

<script setup lang="ts">
import {watch} from "vue";
import {canSubmitContent, roles, selectedRole} from "@/roles";
import {faPlus, faSignIn, faSignOut} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {selectingPositionForPOI} from "@/globals";

watch(selectedRole, () => {
  localStorage.setItem('role', selectedRole.value);
});
</script>

<style scoped>
.header {
  z-index: 20000;
}
</style>
