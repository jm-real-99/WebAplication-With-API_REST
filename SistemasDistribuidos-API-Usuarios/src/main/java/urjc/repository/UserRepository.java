package urjc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import urjc.models.User;


public interface UserRepository extends JpaRepository<User, Long> {

}