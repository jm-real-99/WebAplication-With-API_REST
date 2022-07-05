package urjc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.models.Bicicleta;


public interface BicicletaRepository extends JpaRepository<Bicicleta, Long> {
	
	Optional<Bicicleta> findById(long id);
	
}
