package urjc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import urjc.models.Bicicleta;
import urjc.models.Estacion;
import urjc.models.Estado;
import urjc.models.EstadoBici;
import urjc.models.Usuario;
import urjc.repositories.BicicletaRepository;
import urjc.repositories.EstacionRepository;
import urjc.repositories.UsuarioRepository;

@Component
public class DatabaseInitializer {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EstacionRepository estacionRepository;
	
	@Autowired
	private BicicletaRepository bicicletaRepository;
	
	@PostConstruct
	public void init() throws IOException {
		
		
		FileUtils.copyURLToFile(new URL("https://f.rpp-noticias.io/2019/02/15/753298descarga-9jpg.jpg"), new File("foto.jpg"));
		File tempFile = new File("foto.jpg");
		Usuario usuario1=new Usuario("asdfg", "Alberto Martin", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile)), "27-10-2022",Estado.Activo);
		tempFile.delete();
	
		FileUtils.copyURLToFile(new URL("https://f.rpp-noticias.io/2019/02/15/753296descarga-7jpg.jpg"), new File("foto2.jpg"));
		File tempFile2 = new File("foto2.jpg");
		Usuario usuario2=new Usuario("qwert", "Juanma Real", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile2)), "21-11-2020",Estado.Activo);
		tempFile2.delete();
		
		FileUtils.copyURLToFile(new URL("https://f.rpp-noticias.io/2019/02/15/753300descarga-11jpg.jpg"), new File("foto3.jpg"));
		File tempFile3 = new File("foto3.jpg");
		Usuario usuario3=new Usuario("zcvbn", "Benjamin", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile3)), "2-04-2021",Estado.Activo);
		tempFile3.delete();
		
		FileUtils.copyURLToFile(new URL("https://f.rpp-noticias.io/2019/02/15/753297descarga-8jpg.jpg"), new File("foto4.jpg"));
		File tempFile4 = new File("foto4.jpg");
		Usuario usuario4=new Usuario("jdhakla", "Rocio Amengual", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile4)), "23-01-2007",Estado.Activo);
		tempFile4.delete();
		
		FileUtils.copyURLToFile(new URL("https://f.rpp-noticias.io/2019/02/15/753301descarga-12jpg.jpg"), new File("foto5.jpg"));
		File tempFile5 = new File("foto5.jpg");
		Usuario usuario5=new Usuario("dasfsadsa", "Sara Muñoz", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(tempFile5)), "07-07-2016",Estado.Activo);
		tempFile5.delete();
		usuarioRepository.save(usuario1);
		usuarioRepository.save(usuario2);
		usuarioRepository.save(usuario3);
		usuarioRepository.save(usuario4);
		usuarioRepository.save(usuario5);
		
		Estacion estacion1 = new Estacion(12345,"Latitud: 39.8568, Longitud: -4.02448",10,"10-11-2020",Estado.Activo);
		Estacion estacion2 = new Estacion(56789,"Latitud: 60.33, Longitud: -20.4802",5,"03-03-2017",Estado.Activo);
		Estacion estacion3 = new Estacion(13579,"Latitud: 2.5889, Longitud: 10.7291",10,"22-08-2019",Estado.Inactivo);

		estacionRepository.save(estacion1);
		estacionRepository.save(estacion2);
		estacionRepository.save(estacion3);
		
		Bicicleta bicicleta1 = new Bicicleta("0000000000000000", "a", "1-1-2020", EstadoBici.EnBase);
        Bicicleta bicicleta2 = new Bicicleta("0000000000000001", "b", "1-1-2021", EstadoBici.EnBase);
        Bicicleta bicicleta3 = new Bicicleta("0000000000000002", "b", "1-1-2022", EstadoBici.SinBase);
        Bicicleta bicicleta4 = new Bicicleta("0000000000000003", "a", "21-10-2020", EstadoBici.EnBase);
        Bicicleta bicicleta5 = new Bicicleta("0000000000000004", "b", "3-5-2020", EstadoBici.EnBase);
        Bicicleta bicicleta6 = new Bicicleta("0000000000000005", "b", "15-8-2017", EstadoBici.EnBase);
        Bicicleta bicicleta7 = new Bicicleta("0000000000000006", "a", "19-4-2016", EstadoBici.EnBase);
        Bicicleta bicicleta8 = new Bicicleta("0000000000000007", "b", "10-8-2000", EstadoBici.EnBase);
        Bicicleta bicicleta9 = new Bicicleta("0000000000000008", "b", "27-6-2001", EstadoBici.SinBase);
        
        List<EstadoBici> estados1= new ArrayList<>();
        List<EstadoBici> estados2= new ArrayList<>();
        List<EstadoBici> estados3= new ArrayList<>();
        
        estados1.add(EstadoBici.SinBase);
        estados2.add(EstadoBici.SinBase);
        estados3.add(EstadoBici.SinBase);
        
        bicicleta3.setEstados(estados1);
        bicicleta9.setEstados(estados1);
        
        
        estados2.add(EstadoBici.EnBase);
        estados3.add(EstadoBici.EnBase);
        
        bicicleta1.setEstados(estados2);
        bicicleta4.setEstados(estados2);
        bicicleta5.setEstados(estados2);
        bicicleta6.setEstados(estados2);
        bicicleta7.setEstados(estados2);
        bicicleta8.setEstados(estados2);
        
        
        estados3.add(EstadoBici.Reservada);
        estados3.add(EstadoBici.EnBase);
    
        bicicleta2.setEstados(estados3);
        
        bicicletaRepository.save(bicicleta1);
        bicicletaRepository.save(bicicleta2);
        bicicletaRepository.save(bicicleta3);
        bicicletaRepository.save(bicicleta4);
        bicicletaRepository.save(bicicleta5);
        bicicletaRepository.save(bicicleta6);
        bicicletaRepository.save(bicicleta7);
        bicicletaRepository.save(bicicleta8);
        bicicletaRepository.save(bicicleta9);
        
        
        
        List<Bicicleta> list1 = new ArrayList<>();
        list1.add(bicicleta1);
        list1.add(bicicleta2);
        list1.add(bicicleta4);
        list1.add(bicicleta5);
        
        estacion1.setBiciletas(list1);
        
        List<Bicicleta> list2 = new ArrayList<>();
        list2.add(bicicleta6);
        list2.add(bicicleta7);
        list2.add(bicicleta8);
        
        estacion2.setBiciletas(list2);
        
    	estacionRepository.save(estacion1);
		estacionRepository.save(estacion2);
        
	}
}
