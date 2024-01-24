package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;

import com.constants.Status;
import com.domain.Role;
import com.domain.User;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public interface SUserRepository extends JpaRepository<User, Integer> {

	//@Query("select u from User u where u.email=?1 and u.password=?2 and u.status=?3")
    //User login(String email, String password, Status s);

    User findByEmailAndPasswordAndStatus(String email, String password, Status s);

   // User findUserByEmail(String email);

    User findUserByUsernameAndStatus(String username, Status s);

    User findUserByActivationCodeAndStatus(String activationCode, Status s);

    List<User> findUserByEnabledAndStatus(boolean status, Status s);

	List<User> findByRoleAndEnabledAndStatus(Role roleid, boolean b, Status s);

	List<User> findAllByStatusOrderByIdAsc(Status valueOf);

	List<User> findByPostalCodeAndStatus(long postalCode, Status s);

	List<User> findByRmsIdAndStatus(Long rmsId, Status s);

	User findByIdAndStatus(Long createdBy, Status s);

	//User findUserById(Integer createdBy);

	List<User> findByRoleAndEnabledAndPostalCodeAndStatus(Role role, boolean b, long postalCode, Status s);

	List<User> findByRoleAndEnabledAndRmsIdAndStatus(Role role, boolean b, Long rmsId, Status s);
	List<User> findByStatusOrderByIdAsc(Status s);

	User findUserByEmailContainingIgnoreCaseAndStatus(String email, Status active);

	//List<User> findAllByEnabled(boolean b);

	List<User> findAllByEnabledAndStatusOrderByIdAsc(boolean b, Status active);

	List<User> findAllByPostalCodeAndRoleAndStatus(long postalCode, Role role, Status s);

	List<User> findByRmsIdAndEnabledAndStatus(Long rmsName, boolean b, Status s);

	List<User> findAllByPostalCodeAndRoleAndEnabledAndStatus(Long postalCode, Role role, boolean b, Status s);

	List<User> findByOrderByIdAsc();

	List<User> findAllByEnabledOrderByIdAsc(boolean b);

	User findUserByEmailContainingIgnoreCaseAndStatusAndEnabled(
			@NotEmpty(message = "{email.not.empty}") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z]+(\\.[A-Za-z]{2,4})$", message = "{email.pattern.wrong}") String email,
			Status active, boolean b);



}

