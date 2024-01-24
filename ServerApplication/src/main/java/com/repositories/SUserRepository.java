package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.constants.Status;
import com.domain.Role;
import com.domain.User;

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

	User findUserByEmailContainingIgnoreCaseAndStatusAndEnabled(String email, Status active, boolean b);

	List<User> findAllByEnabledAndStatusAndFirstLogin(boolean b, Status disabled, boolean c);

	List<User> findByRmsId(Long rmsId);

	List<User> findAllByPostalCodeAndRole(long postalCode, Role role);

	List<User> findByPostalCode(long postalCode);

	List<User> findByRmsIdAndEnabled(Long rmsName, boolean b);

	List<User> findAllByPostalCodeAndRoleAndEnabled(Long postalCode, Role role, boolean b);

	List<User> findByRoleAndPostalCode(Role role, long num1);

	List<User> findByRoleAndRmsId(Role role, Long id);
}

