package urjc;

import java.io.IOException;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import urjc.models.*;
import urjc.repository.UserRepository;


@Component
public class DatabaseInitializer {

	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void init() throws IOException {

		User user1=new User("dasds", "Alberto Martin", "foto","4-10-2018",Estado.Activo, 31);
		User user2=new User("qwert", "Juanma Real", "foto", "21-11-2020",Estado.Activo,10.67);
		User user3=new User("zcvbn", "Benjamin", "foto", "2-04-2021",Estado.Activo,21.15);
		User user4=new User("jdhakla", "Rocio Amengual", "foto", "23-01-2007",Estado.Activo,100);
		User user5=new User("dasfsadsa", "Sara Muñoz","foto", "07-07-2016",Estado.Activo, 46.12);
	
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
		userRepository.save(user4);
		userRepository.save(user5);
		
	}
}
