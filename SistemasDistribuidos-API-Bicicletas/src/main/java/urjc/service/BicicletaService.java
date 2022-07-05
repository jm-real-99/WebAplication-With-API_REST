package urjc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import urjc.exception.BadRequest;
import urjc.models.Bicicleta;
import urjc.models.BicicletaResponse;
import urjc.models.Estacion;
import urjc.models.Estado;
import urjc.models.EstadoBici;
import urjc.repository.BicicletaRepository;
import urjc.repository.EstacionRepository;

@Service
public class BicicletaService {
	
	@Autowired
	private BicicletaRepository bicicletaRepository;
	
	@Autowired 
	private EstacionRepository estacionRepository;
	
	public Optional<Bicicleta> getBicicleta(long id){
		return bicicletaRepository.findById(id);
	}
	
	public boolean bicicletaEnBase(long idBici, long idBase) {
		
		Optional<Bicicleta> bici = bicicletaRepository.findById(idBici);

		return bici.isPresent() && bici.get().getEstado()==EstadoBici.EnBase && bici.get().getEstacion().getId() == idBase;
		
	}
	
	public boolean bicicletaReservada(long idBici) {
		
		Optional<Bicicleta> bici = bicicletaRepository.findById(idBici);

		return bici.isPresent() && bici.get().getEstado()==EstadoBici.Reservada;
		
	}
	
	public boolean devolverBicicleta(long idBici, long idEst) {
		
		Optional<Bicicleta> bici = bicicletaRepository.findById(idBici);
		
		if (bici.isPresent()) {
			
			bici.get().setEstado(EstadoBici.EnBase);
			bici.get().setEstacion(estacionRepository.findById(idEst).get());
			bicicletaRepository.save(bici.get());
			
			return true;
			
		}
		
		return false;		
		
	}

	public ResponseEntity<BicicletaResponse> reservarBicicleta(long idBici, long idUser, long idEst) {
		
		boolean estacionActiva =estacionActiva(idEst);
		boolean bicicletaEnBase= bicicletaEnBase(idBici, idEst);
		
		if(estacionActiva && bicicletaEnBase) {
			String url = "http://localhost:8081/users/"+idUser+"/alquilerBicicleta";
			
			Optional<Bicicleta> bici =bicicletaRepository.findById(idBici);
			try {
				new RestTemplate().put(url, null); 
				reservarBicicleta(bici.get().getId());
				return ResponseEntity.ok(new BicicletaResponse(bici.get()));
				
			}catch(Exception ex){
				throw new BadRequest("Peticion incorrecta a la api usuario, el error es: "+ex.getMessage());
			}
		}
		
		lanzarExcepcion(estacionActiva, bicicletaEnBase);
		return null;
		
	}
	
	private void lanzarExcepcion(boolean estacionActiva, boolean bicicletaEnBase) {
		if(!estacionActiva) {
			throw new BadRequest("La estacion esta inactiva");
		}else {
			throw new BadRequest("La bicileta no esta en la base");
		}
	}
	
	private boolean reservarBicicleta(long id) {
		
		Optional<Bicicleta> bici = bicicletaRepository.findById(id);

		if(bici.isPresent()) {
			
			bici.get().setEstado(EstadoBici.Reservada);
			Estacion estacion= bici.get().getEstacion();
			boolean retirada = estacion.retirarBicicleta(bici.get());
			if(retirada)
				estacionRepository.save(estacion);
			
			bicicletaRepository.save(bici.get());
			
			return true;
			
		}
		
		return false;
		
	}
	
	public ResponseEntity<BicicletaResponse> devolverBicicleta(long idUser, long idEst, long idBici) {
		boolean estacionActiva=estacionActiva(idEst); 
		boolean tieneEspacio=tieneEspacio(idEst);
		boolean bicicletaResevada=bicicletaReservada(idBici);
		if(estacionActiva 
				&& tieneEspacio 
				&& bicicletaResevada) {								

			try {
				String url = "http://localhost:8081/users/"+idUser+"/devolucionFianza";
				new RestTemplate().put(url, null);		

			} catch (Exception ex){
				throw new BadRequest("Peticion incorrecta a la api usuario, el error es: "+ex.getMessage());
			}
			
			if (devolverBicicleta(idBici, idEst) 
					&& devolverBicicleta2(idBici, idEst)) { 
				
				Optional<Bicicleta> bici = getBicicleta(idBici);
				return ResponseEntity.ok(new BicicletaResponse(bici.get()));
			
			} else 
				return ResponseEntity.internalServerError().build();			
		}
		lanzarExcepcion(estacionActiva, tieneEspacio, bicicletaResevada);
		return null;
		
	}
	
	private void lanzarExcepcion(boolean estacionActiva,boolean tieneEspacio, boolean bicicletaResevada) {
		if(!estacionActiva) {
			throw new BadRequest("La estacion esta inactiva");
		}
		else if(!bicicletaResevada) {
			throw new BadRequest("La bicileta no esta reservada");
		}else {
			throw new BadRequest("La estacion no tiene espacio");
		}
	}
	
	
	private boolean estacionActiva(long idEst) {
		
		Optional<Estacion> estacion = estacionRepository.findById(idEst);
		
		return estacion.isPresent() && estacion.get().getEstado() == Estado.Activo;
		
	}
	
	private boolean tieneEspacio(long idEst) {
		
		Optional<Estacion> estacion = estacionRepository.findById(idEst);
		
		return estacion.isPresent() && estacion.get().getEstado() == Estado.Activo && estacion.get().getEspacios() != 0;
		
	}
	
	private boolean devolverBicicleta2 (long idBici, long idEst) {
		
		Optional<Estacion> estacion = estacionRepository.findById(idEst);
		
		if (estacion.isPresent() && estacion.get().getEstado() == Estado.Activo && estacion.get().getEspacios() != 0 ) {
			
			estacion.get().setEspacios(estacion.get().getEspacios() - 1);
			estacion.get().getBicicletas().add(bicicletaRepository.findById(idBici).get());
			estacionRepository.save(estacion.get());
			
			return true;
			
		}		
		
		return false;
		
	}
	
}
