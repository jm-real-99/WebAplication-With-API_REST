package urjc.controller;

import java.net.URI;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import urjc.models.User;
import urjc.models.UserDTO;
import urjc.models.UserResponse;
import urjc.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
   
    @GetMapping("/")
    public Collection<UserResponse> getAllUser(){
        return userService.getAllUser();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id){
        UserResponse user =  userService.getUserById(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserDTO userDTO){
        UserResponse user = userService.addUser(mapUserDTOtoUser(userDTO));
        
        URI location =
            ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> modifyUser(@PathVariable long id, @RequestBody UserDTO userDTO){
        UserResponse user = userService.modifyUser(id,mapUserDTOtoUser(userDTO));

        if (user != null) {
            return ResponseEntity.ok(user);
            } else {
            return ResponseEntity.notFound().build();
            }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable long id){
        UserResponse user = userService.deleteUser(id);

        if (user != null) {
            return ResponseEntity.ok(user);
            } else {
            return ResponseEntity.notFound().build();
            }
    }
    
    @PutMapping("/{id}/pagoBicicleta")
    public ResponseEntity<UserResponse> pagoBicicleta(@PathVariable long id){
        UserResponse user = userService.pagoBicicleta(id);

        if (user != null) {
            return ResponseEntity.ok(user);
            } else {
            return ResponseEntity.notFound().build();
            }
        
    }
    
    @PutMapping("/{id}/devolverFianza")
    public ResponseEntity<UserResponse> devolverFianza(@PathVariable long id){
        UserResponse user = userService.devolverFianza(id);

        if (user != null) {
            return ResponseEntity.ok(user);
         } else {
            return ResponseEntity.notFound().build();
        }  
    }
    
    private User mapUserDTOtoUser (UserDTO userDTO){
        return new User(userDTO.getContraseña(), userDTO.getNombre(), userDTO.getFoto(), userDTO.getEstado(), Double.valueOf(userDTO.getSaldo()));
    }
}
