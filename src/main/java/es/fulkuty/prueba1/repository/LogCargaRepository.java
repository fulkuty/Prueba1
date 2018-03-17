package es.fulkuty.prueba1.repository;

import es.fulkuty.prueba1.domain.LogCarga;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LogCarga entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogCargaRepository extends JpaRepository<LogCarga, Long> {

}
