package urjc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.models.Estado;
import urjc.models.Usuario;


public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
	
	List<Usuario> findByEstado(Estado estado);

}
