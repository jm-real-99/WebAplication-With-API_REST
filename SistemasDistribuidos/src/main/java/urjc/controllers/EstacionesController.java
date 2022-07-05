package urjc.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import urjc.models.Bicicleta;
import urjc.models.Estacion;
import urjc.models.Estado;
import urjc.models.EstadoBici;
import urjc.repositories.BicicletaRepository;
import urjc.repositories.EstacionRepository;

@Controller
public class EstacionesController {

	@Autowired
	private EstacionRepository estacionRepository;
	
	@Autowired
	private BicicletaRepository bicicletaRepository;

	@GetMapping("/estaciones")
	public String showEstacion(Model model) {
		
		List<Estacion> listEstacion=estacionRepository.findByEstado(Estado.Activo);
		
		boolean find=!listEstacion.isEmpty();
		if(find) {
			model.addAttribute("estaciones", listEstacion);
		}
		model.addAttribute("SinEstaciones",listEstacion.isEmpty());
		model.addAttribute("find",true);
		return "estaciones";
	}
	
	@GetMapping("/estacion/{id}")
	public String getEstacion(Model model, @PathVariable long id) {
		Optional<Estacion> estacion=estacionRepository.findById(id);
		boolean find=estacion.isPresent();
		if(find) {
			System.out.println(estacion.get());
			model.addAttribute("estacion",estacion.get());
			if(!estacion.get().getBiciletas().isEmpty())
				model.addAttribute("bikeList",estacion.get().getBiciletas());
			else
				model.addAttribute("noBicis", true);
		}
		return "estacion";
	}
	
	@PostMapping("/estacion/filterNumSerie")
	public String showEstacionById(@RequestParam String numSerie, Model model) {
		Optional<Estacion> estacion =estacionRepository.findByNumSerie(Integer.parseInt(numSerie));
		if(estacion.isPresent()) {
			model.addAttribute("estaciones", estacion.get());
			model.addAttribute("find", true);
			model.addAttribute("numSerie", numSerie);
			model.addAttribute("numSerieFilter", true);
		} 
		model.addAttribute("SinEstaciones",estacionRepository.findByEstado(Estado.Activo).isEmpty());

		return "estaciones";
	}
	
	@PostMapping("/estacion")
	public String createEstacion(Model model, Estacion estacion, String coordenadas1, String coordenadas2) {
		Optional<Estacion> existeEstacion =estacionRepository.findByNumSerie(estacion.getNumSerie());
			if(!existeEstacion.isPresent()) {
				String coordenadas = "Latitud: "+coordenadas1+", Longitud: "+coordenadas2;
				estacion.setCoordenadas(coordenadas);
				System.out.println("CREAMOS LA BICICLETA: "+estacion);
				estacion.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
				estacion.setEstado(Estado.Activo);
				System.out.println("AÑADIMOS DATOS LA BICICLETA: "+estacion);
				estacionRepository.save(estacion);
		}
		model.addAttribute("find", true);
		model.addAttribute("estaciones",estacionRepository.findByEstado(Estado.Activo));
		model.addAttribute("addedSuccess",true);
		model.addAttribute("SinEstaciones",estacionRepository.findByEstado(Estado.Activo).isEmpty());
		
		return "estaciones";
	}
	
	
	@GetMapping("/eliminarEst/{id}")
	public String eliminarEstacion(Model model, @PathVariable long id) {
		Optional<Estacion> estacion=estacionRepository.findById(id);
		
		estacion.get().setEstado(Estado.Inactivo);
		for(Bicicleta bicicleta : estacion.get().getBiciletas()) {
			bicicleta.setEstado(EstadoBici.SinBase);
			bicicleta.getEstados().add(EstadoBici.SinBase);
			bicicletaRepository.save(bicicleta);
		}
		List<Bicicleta> listEmpty= new ArrayList<>();
		estacion.get().setBiciletas(listEmpty);
		estacionRepository.save(estacion.get());
		
		return "redirect:/estaciones";

	}
	
	@GetMapping("/modificarEst/{id}")
	public String modificarEstacion(Model model, @PathVariable long id) {
		Optional<Estacion> estacion=estacionRepository.findById(id);
		boolean find=estacion.isPresent();
		if(find) {
			model.addAttribute("NumeroSerie",estacion.get().getId());
			model.addAttribute("Coor",estacion.get().getCoordenadas());
			model.addAttribute("id",estacion.get().getId());
		}
		return "modifyStation";
	}
	
	@PostMapping("/estacion/modifiedStation")
	public String modifiedStation(Model model, @RequestParam String coordenadas1,String coordenadas2, String editar) {
		
		long id = Long.parseLong(editar);
		Optional<Estacion> estacion=estacionRepository.findById(id);
		boolean esta=estacion.isPresent();
		if(esta) {
			String coordenadas = "Latitud: "+coordenadas1+", Longitud: "+coordenadas2;
			estacion.get().setCoordenadas(coordenadas);
		}
		
		estacionRepository.save(estacion.get());
		
		return "redirect:/estaciones";
	
	}
}
