package ar.edu.utn.dds.k3003.search_service.repository;

import ar.edu.utn.dds.k3003.search_service.model.HechoIndexado;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface HechoIndexadoRepository extends MongoRepository<HechoIndexado, String> {
    Optional<HechoIndexado> findByHechoId(String hechoId);
}
