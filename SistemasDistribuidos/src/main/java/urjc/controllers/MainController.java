package urjc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import urjc.models.Estado;
import urjc.models.Usuario;
import urjc.repositories.UsuarioRepository;

@Controller
public class MainController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping ("/")
	public String mainPage(Model model) {
		
		List<Usuario> listUser=usuarioRepository.findByEstado(Estado.Activo);

		boolean find=!listUser.isEmpty();
		if(find) {
			model.addAttribute("users", listUser);
		}
		model.addAttribute("find",find);
		return "users";
		
	}

}
