package it.unicam.cs.ids.repository;

import it.unicam.cs.ids.model.Municipality;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface MunicipalityRepository extends MongoRepository<Municipality, String> {

    @Query(value = "{}", sort = "{ _id : 1 }")
    Municipality getDefaultMunicipality();
}
