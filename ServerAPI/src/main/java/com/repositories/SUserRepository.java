package com.repositories;

import com.constants.Status;
import com.domain.Role;
import com.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

//import org.springframework.data.repository.CrudRepository;

public interface SUserRepository extends JpaRepository<User, Integer> {

    User findUserByActivationCodeAndStatus(String activationCode, Status s);
    List<User> findUserByPostalCodeAndEnabledAndStatus(long postalCode, boolean status, Status s);
	User findUserByEmailAndStatus(@NotEmpty(message = "{email.not.empty}") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z]+(\\.[A-Za-z]{2,4})$", message = "{email.pattern.wrong}") String email, Status s);
	List<User> findByRoleAndEnabledAndStatus(Role roleid, boolean b, Status s);
	List<User> findByRoleAndEnabledAndPostalCode(Role roleid_User, boolean b, long postalCode);
	List<User> findUserByPostalCodeAndRole(long postalCode, Role roleId);


}

