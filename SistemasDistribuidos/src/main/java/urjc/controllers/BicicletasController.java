package urjc.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import urjc.models.Bicicleta;
import urjc.models.Estacion;
import urjc.models.Estado;
import urjc.models.EstadoBici;
import urjc.repositories.BicicletaRepository;
import urjc.repositories.EstacionRepository;

@Controller
public class BicicletasController {


	@Autowired
	private BicicletaRepository bicicletaRepository;
	
	@Autowired
	private EstacionRepository estacionRepository;

	@GetMapping("/bikes")
	public String showBikes(Model model) {
		
		List<Bicicleta> listBicicletas = bicicletaRepository.findByEstadoIsNot(EstadoBici.Baja);

		boolean find=!listBicicletas.isEmpty();
		if(find) {
			model.addAttribute("bikes", listBicicletas);
		}
		model.addAttribute("find",find);
		model.addAttribute("findFilter",true);
		return "bikes";
		
	}
	
	@PostMapping("/bikes/createBike")
	public String createBike(Model model, Bicicleta bicicleta) {
		if(bicicleta.getNumSerie().length()==16) {
				Optional<Bicicleta> existeBicicleta =bicicletaRepository.findByNumSerie(bicicleta.getNumSerie());
				
				if(!existeBicicleta.isPresent()) {
					bicicleta.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
					bicicleta.setEstado(EstadoBici.SinBase);
					List<EstadoBici> estados= new ArrayList<>();
					estados.add(EstadoBici.SinBase);
					bicicleta.setEstados(estados);
					bicicletaRepository.save(bicicleta);
			}
			model.addAttribute("addedSuccess",true);
		}
		else {
			model.addAttribute("addedNoSuccess",true);
		}
		model.addAttribute("find", !bicicletaRepository.findAll().isEmpty());
		model.addAttribute("findFilter",true);
		
		List<Bicicleta> listBicicletas = bicicletaRepository.findByEstadoIsNot(EstadoBici.Baja);
		
		model.addAttribute("bikes",listBicicletas);
		
		boolean historial = !bicicleta.getEstados().isEmpty();
		
		if(historial) {
			model.addAttribute("estados",bicicleta.getEstados());
		}
		
		model.addAttribute("historial",historial);
		
		
		
		return "bikes";
	}

	
	@GetMapping("/bikes/filterBike")
	public String filterBike(Model model, String numSerie, EstadoBici estado) {
		if(!numSerie.isEmpty() && estado==null) {
			Optional <Bicicleta> bicicleta=bicicletaRepository.findByNumSerie(numSerie);
			boolean find=bicicleta.isPresent();
			if(find) {
				List<Bicicleta> listBicicletas= new ArrayList<>();
				listBicicletas.add(bicicleta.get());
				
				model.addAttribute("bikes", listBicicletas);
			}
			model.addAttribute("findFilter",find);
		}
		else if(numSerie.isEmpty() && estado!=null) {
			List<Bicicleta> listBicicletas=bicicletaRepository.findByEstado(estado);
			boolean find=!listBicicletas.isEmpty();
			if(find) {
				model.addAttribute("bikes", listBicicletas);
			}
			model.addAttribute("findFilter",find);
		}
		else if (!numSerie.isEmpty() && estado!=null){
			Optional <Bicicleta> bicicleta=bicicletaRepository.findByNumSerie(numSerie);
			
			boolean find=bicicleta.isPresent();
			if(find) {
				if(bicicleta.get().getEstado()==estado) {
					List<Bicicleta> listBicicletas= new ArrayList<>();
					listBicicletas.add(bicicleta.get());
					model.addAttribute("bikes", listBicicletas);
				}
				else
					find=false;
			}
			model.addAttribute("findFilter",find);
		}
		else {
			List<Bicicleta> listBicicletas = bicicletaRepository.findByEstadoIsNot(EstadoBici.Baja);

			boolean find=!listBicicletas.isEmpty();
			if(find) {
				model.addAttribute("bikes", listBicicletas);
			}
			model.addAttribute("findFilter",true);
		}
		model.addAttribute("find",true);
		return "bikes";
	}
	

	@GetMapping("/bike/{id}")
	public String getBike(Model model, @PathVariable long id) {
		
		Optional<Bicicleta> bicicleta = bicicletaRepository.findById(id);
		boolean find=bicicleta.isPresent();
		if(find) {
			boolean sinEstado = bicicleta.get().getEstado() == EstadoBici.SinBase;
			boolean historial = !bicicleta.get().getEstados().isEmpty();
			
			if(historial) {
				model.addAttribute("estados",bicicleta.get().getEstados());
			}
			
			model.addAttribute("historial",historial);
			model.addAttribute("bicicleta",bicicleta.get());
			model.addAttribute("sinBase", sinEstado);	
			
			if(sinEstado) {
				model.addAttribute("estaciones", estacionRepository.findByEstado(Estado.Activo));	
			}
		}
		return "bike";
		
	}
	
	@GetMapping("/bike/{id}/darDeBajaBici")
	public String darDeBaja(Model model, @PathVariable long id) {
		Optional<Bicicleta> bike = bicicletaRepository.findById(id);
		if(bike.isPresent()) {
			bike.get().setEstado(EstadoBici.Baja);
			bike.get().getEstados().add(EstadoBici.Baja);
			bicicletaRepository.save(bike.get());
			
			for(Estacion estacion: estacionRepository.findAll()) {
				if(estacion.getBiciletas().contains(bike.get())) {
					estacion.getBiciletas().remove(bike.get());
					estacionRepository.save(estacion);
					break;
				}
			}
		}
		
		
		List<Bicicleta> listBicicletas = bicicletaRepository.findByEstadoIsNot(EstadoBici.Baja);

		boolean find=!listBicicletas.isEmpty();
		if(find) {
			model.addAttribute("bikes", listBicicletas);
		}
		model.addAttribute("find",find);
		model.addAttribute("findFilter",true);
		
		return "bikes";
		
	}
	
	@PostMapping("/bike/{id}/darDeAltaBici")
	public String darDeAlta(Model model, @PathVariable long id, long idEstacion) {
		Optional<Estacion> estacionDisponible= estacionRepository.findById(idEstacion);
		Optional<Bicicleta> bike = bicicletaRepository.findById(id);
		if(estacionDisponible.isPresent()) {
			if(estacionDisponible.get().getBiciletas().size()<estacionDisponible.get().getCapacidad()) {
				estacionDisponible.get().getBiciletas().add(bike.get());
				bike.get().setEstado(EstadoBici.EnBase);
				bike.get().getEstados().add(EstadoBici.EnBase);
				bicicletaRepository.save(bike.get());
				estacionRepository.save(estacionDisponible.get());
				model.addAttribute("dadoDeAlta",true);
			}
			else {
				model.addAttribute("noDadoDeAlta",true);
				model.addAttribute("sinBase", false);	
				model.addAttribute("estaciones", estacionRepository.findAll());	
				
			}
		}
		boolean historial = !bike.get().getEstados().isEmpty();
		
		if(historial) {
			model.addAttribute("estados",bike.get().getEstados());
		}
		
		model.addAttribute("historial",historial);
		model.addAttribute("bicicleta",bike.get());
		
		return "bike";
		
	}
	
}
