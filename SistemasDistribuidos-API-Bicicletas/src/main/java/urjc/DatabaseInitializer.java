package urjc;

import java.io.IOException;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import urjc.models.Bicicleta;
import urjc.models.Estacion;
import urjc.models.Estado;
import urjc.models.EstadoBici;
import urjc.repository.BicicletaRepository;
import urjc.repository.EstacionRepository;


@Component
@Profile("local")
public class DatabaseInitializer {

	@Autowired
	private BicicletaRepository bicicletaRepository;
	
	@Autowired
	private EstacionRepository estacionRepository;
	
	
	@PostConstruct
	public void init() throws IOException {
		
		Estacion estacion1 = new Estacion(12345,"Latitud: 39.8568, Longitud: -4.02448",5,"10-11-2020",Estado.Activo);
		Estacion estacion2 = new Estacion(56789,"Latitud: 60.33, Longitud: -20.4802",10,"03-03-2017",Estado.Activo);
		
		
		estacionRepository.save(estacion1);
		estacionRepository.save(estacion2);
		
		Bicicleta bicicleta1 = new Bicicleta("0000000000000000", "a", "1-1-2020",   EstadoBici.EnBase, estacion1);
        Bicicleta bicicleta2 = new Bicicleta("0000000000000001", "b", "1-1-2021",   EstadoBici.EnBase, estacion1);
        Bicicleta bicicleta3 = new Bicicleta("0000000000000002", "b", "1-1-2022",   EstadoBici.EnBase, estacion1);
        Bicicleta bicicleta4 = new Bicicleta("0000000000000003", "a", "21-10-2020", EstadoBici.EnBase, estacion1);
        Bicicleta bicicleta5 = new Bicicleta("0000000000000004", "b", "3-5-2020",   EstadoBici.EnBase, estacion2);
        Bicicleta bicicleta6 = new Bicicleta("0000000000000005", "b", "15-8-2017",  EstadoBici.EnBase, estacion2);
        Bicicleta bicicleta7 = new Bicicleta("0000000000000006", "a", "19-4-2016",  EstadoBici.EnBase, estacion2);
        Bicicleta bicicleta8 = new Bicicleta("0000000000000007", "b", "10-8-2000",  EstadoBici.SinBase,null);
        Bicicleta bicicleta9 = new Bicicleta("0000000000000008", "b", "27-6-2001",  EstadoBici.Reservada,null);
        
        
        bicicletaRepository.save(bicicleta1);
        bicicletaRepository.save(bicicleta2);
        bicicletaRepository.save(bicicleta3);
        bicicletaRepository.save(bicicleta4);
        bicicletaRepository.save(bicicleta5);
        bicicletaRepository.save(bicicleta6);
        bicicletaRepository.save(bicicleta7);
        bicicletaRepository.save(bicicleta8);
        bicicletaRepository.save(bicicleta9);
        
        estacion1.setEspacios(1);
        
        estacion1.getBicicletas().add(bicicleta1);
        estacion1.getBicicletas().add(bicicleta2);
        estacion1.getBicicletas().add(bicicleta3);
        estacion1.getBicicletas().add(bicicleta4);
        
        estacion2.setEspacios(7);
        
        estacion2.getBicicletas().add(bicicleta5);
        estacion2.getBicicletas().add(bicicleta6);
        estacion2.getBicicletas().add(bicicleta7);        
        
    	estacionRepository.save(estacion1);
		estacionRepository.save(estacion2);


		
	}
}
