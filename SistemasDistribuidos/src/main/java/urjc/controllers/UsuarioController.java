package urjc.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import urjc.models.Estado;
import urjc.models.Usuario;
import urjc.repositories.UsuarioRepository;


@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@GetMapping("/users")
	public String showUser(Model model) {
		List<Usuario> listUser=usuarioRepository.findAll();
		List<Usuario> listActivos = usuarioRepository.findByEstado(Estado.Activo);

		boolean find=!listUser.isEmpty();
		if(find) {
			model.addAttribute("users", listUser);
			for(Usuario u:listUser) {
				model.addAttribute("baja",u.getEstado()==Estado.Inactivo);
			}
		}
		model.addAttribute("usuariosActivos",listActivos);
		
		model.addAttribute("find",find);
		return "users";
	}
	
	@GetMapping("/user/{id}")
	public String getUser(Model model, @PathVariable long id) {
		Optional<Usuario> user=usuarioRepository.findById(id);
		boolean find=user.isPresent();
		if(find) {
			model.addAttribute("ident",user.get().getId());
			model.addAttribute("nombre",user.get().getNombre());
			model.addAttribute("estado",user.get().getEstado());
			model.addAttribute("fecha",user.get().getFecha());
			model.addAttribute("foto",user.get().getFoto());
			model.addAttribute("baja",user.get().getEstado()!=Estado.Inactivo);
		}
		
		
		return "user";
	}
	
	@PostMapping("/user/createUser")
	public String createUser(@RequestParam MultipartFile file,  String nombre, String contraseña, Model model) throws IOException {
		Path tempPath = Paths.get(file.getOriginalFilename());
		file.transferTo(tempPath);
	    File tempFile = tempPath.toFile();
		byte[] fileContent = FileUtils.readFileToByteArray(tempFile);
		Usuario usuario = new Usuario(contraseña,nombre,Base64.getEncoder().encodeToString(fileContent));
		tempFile.delete();
		
		usuario.setEstado(Estado.Activo);
		usuario.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
		
		usuarioRepository.save(usuario);
		
		model.addAttribute("find",true);
		model.addAttribute("users",usuarioRepository.findAll());
		model.addAttribute("addedUserSuccess",true);
		
		return "users";
	}
	
	
	@GetMapping("/user/deleteUser")
	public String deleteUser(Model model, @RequestParam String usuario) {
		
		long id = Long.parseLong(usuario);
		Optional<Usuario> user=usuarioRepository.findById(id);
		
		user.get().setEstado(Estado.Inactivo);
		
		usuarioRepository.save(user.get());
		
		return "redirect:/users";
		
	}
	
	@GetMapping("/user/{id}/modifyUser")
	public String modifyUser(Model model, @PathVariable long id) {
		
		Optional<Usuario> user=usuarioRepository.findById(id);
		
		model.addAttribute("nombreUsuario",user.get().getNombre());
		model.addAttribute("ident",id);
		
		return "modifyUser";
	}
	
	@PostMapping("/user/{id}/modifiedUser")
	public String modifiedUser(@RequestParam MultipartFile file, Model model, @RequestParam String nombre,String contraseña, @PathVariable long id) throws IllegalStateException, IOException {
		
		Optional<Usuario> user=usuarioRepository.findById(id);
		if(user.isPresent()){
			if(!contraseña.isEmpty()) {
				user.get().setContraseña(contraseña);
			}
			if(!file.isEmpty()) {
				Path tempPath = Paths.get(file.getOriginalFilename());
				file.transferTo(tempPath);
			    File tempFile = tempPath.toFile();
				byte[] fileContent = FileUtils.readFileToByteArray(tempFile);
				user.get().setFoto(Base64.getEncoder().encodeToString(fileContent));
				tempFile.delete();
			}
			
			if(!nombre.isEmpty()) {
				user.get().setNombre(nombre);
			}
			
			usuarioRepository.save(user.get());
		}
		
		return "redirect:/users";
	}
	
}
