package es.fulkuty.prueba1.repository;

import es.fulkuty.prueba1.domain.ZonaCarga;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ZonaCarga entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZonaCargaRepository extends JpaRepository<ZonaCarga, Long> {

}
