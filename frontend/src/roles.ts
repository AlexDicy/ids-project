import {ref} from "vue";

export const roles = {
    TURISTA: 'Turista',
    TURISTA_AUTENTICATO: 'Turista autenticato',
    CONTRIBUTOR: 'Contributor',
    CONTRIBUTOR_AUTORIZZATO: 'Contributor Autorizzato',
    CURATORE: 'Curatore',
    ANIMATORE: 'Animatore',
    GESTORE_PIATTAFORMA: 'Gestore piattaforma'
};

export const selectedRole = ref(localStorage.getItem('role') ?? 'TURISTA');

export function canSubmitContent() {
    switch (selectedRole.value) {
        case 'CONTRIBUTOR':
        case 'CONTRIBUTOR_AUTORIZZATO':
        case 'CURATORE':
        case 'GESTORE_PIATTAFORMA':
            return true;
        default:
            return false;
    }
}
