package es.fulkuty.prueba1.repository;

import es.fulkuty.prueba1.domain.ProcesoCarga;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProcesoCarga entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcesoCargaRepository extends JpaRepository<ProcesoCarga, Long> {

}
