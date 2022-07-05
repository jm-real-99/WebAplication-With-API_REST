package urjc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.models.Bicicleta;
import urjc.models.Estacion;
import urjc.models.Estado;

public interface EstacionRepository extends JpaRepository<Estacion, Long> {

	Optional<Estacion> findByNumSerie(int numSerie);
	List<Estacion> findByEstado(Estado estado);
}
