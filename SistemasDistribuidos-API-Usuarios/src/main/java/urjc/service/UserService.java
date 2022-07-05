package urjc.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import urjc.models.Estado;
import urjc.models.User;
import urjc.models.UserResponse;
import urjc.repository.UserRepository;


@Service
public class UserService {

    @Autowired
	private UserRepository userRepository;
    
    private int fianza=10;

    public List<UserResponse> getAllUser(){
        return userRepository.findAll().stream().map(user-> mapUsertoUserResponse(user)).collect(Collectors.toList());
    }
    
    public UserResponse getUserById(long id){
        Optional<User> user= userRepository.findById(id);
        if(user.isPresent())
            return  mapUsertoUserResponse(user.get());
        else 
            return null;
    }

    public UserResponse addUser(User user){
    	user.setFecha(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        return  mapUsertoUserResponse(userRepository.save(user));
    }
    
    public UserResponse modifyUser(long id,User user){
    	Optional<User> userFind= userRepository.findById(id);
        if(userFind.isPresent()) {
        	userFind.get().setContraseña(user.getContraseña());
        	userFind.get().setEstado(user.getEstado());
        	userFind.get().setFoto(user.getFoto());
        	userFind.get().setNombre(user.getNombre());
        	userFind.get().setContraseña(user.getContraseña());
        	return  mapUsertoUserResponse(userRepository.save(userFind.get()));
        }
        else 
            return null;
    }

    public UserResponse deleteUser(long id){
        Optional<User> user= userRepository.findById(id);
        if(user.isPresent()){
        	user.get().setEstado(Estado.Inactivo);
        	return  mapUsertoUserResponse(userRepository.save(user.get()));
    }
        else 
            return null;
    }
    
    public UserResponse pagoBicicleta(long id){
    	Optional<User> userFind= userRepository.findById(id);
        if(userFind.isPresent()) {
        	if(userFind.get().getEstado()==Estado.Activo && userFind.get().getSaldo()>=fianza*2) {
        		userFind.get().setSaldo(userFind.get().getSaldo()-fianza*2);
        		return  mapUsertoUserResponse(userRepository.save(userFind.get()));
        	}
        	else {
        		return null; //AQUI HABRIA QUE HACER QUE DEVOLVIERA UN MENSAJE DE ESTADO ACTIVO O SALDO INSUFICIENTE
        	}
        }
        else 
            return null;
   }
    
    public UserResponse devolverFianza(long id){
    	Optional<User> userFind= userRepository.findById(id);
        if(userFind.isPresent()) {
        	userFind.get().setSaldo(userFind.get().getSaldo()+fianza);
        	return  mapUsertoUserResponse(userRepository.save(userFind.get()));
        }
        else 
        	return null; 
        
   }

    private UserResponse mapUsertoUserResponse (User user){
        return new UserResponse(Long.toString(user.getId()), user.getContraseña(), user.getNombre(), user.getFoto(), user.getFecha(), user.getEstado().toString(), user.getSaldo()+" €");
    }
}
