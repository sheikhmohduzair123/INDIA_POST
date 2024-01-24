package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	List<Role> findAll();

	Role findOneByName(String string);

	Role findOneByDisplayName(String role);
}