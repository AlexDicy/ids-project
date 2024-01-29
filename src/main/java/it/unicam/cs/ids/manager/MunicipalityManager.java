package it.unicam.cs.ids.manager;

import it.unicam.cs.ids.model.Municipality;
import it.unicam.cs.ids.repository.MunicipalityRepository;
import org.springframework.stereotype.Service;

@Service
public class MunicipalityManager {
    private final MunicipalityRepository repository;
    private Municipality defaultMunicipality;

    public MunicipalityManager(MunicipalityRepository repository) {
        this.repository = repository;
    }

    public Municipality getDefaultMunicipality() {
        if (defaultMunicipality == null) {
            defaultMunicipality = repository.getDefaultMunicipality();
        }
        return defaultMunicipality;
    }
}
