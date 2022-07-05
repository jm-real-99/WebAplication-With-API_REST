package urjc.repositories;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.models.Bicicleta;
import urjc.models.Estacion;
import urjc.models.Estado;
import urjc.models.EstadoBici;

public interface BicicletaRepository extends JpaRepository<Bicicleta, Long>{
	
	Optional<Bicicleta> findByNumSerie(String numSerie);
	List <Bicicleta> findByEstado(EstadoBici estado);
	List<Bicicleta> findByEstadoIsNot(EstadoBici estado);
}
