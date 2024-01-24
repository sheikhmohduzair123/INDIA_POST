package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;

import com.domain.User;

import java.util.List;

public interface SUserRepository extends JpaRepository<User, Integer> {
	
  /*  @Query("select u from User u where u.email=?1 and u.password=?2")
    User login(String email, String password);

    User findByEmailAndPassword(String email, String password);

    User findUserByEmail(String email);
    
    User findUserByUsername(String username);

    User findUserByActivationCode(String activationCode);

    List<User> findUserByEnabled(boolean status);
*/

}

