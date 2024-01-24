package com.repositories;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.PasswordHistory;

public interface SUserPasswordRepository extends JpaRepository<PasswordHistory, Integer>{

	ArrayList<PasswordHistory> findTop5ByUserIdOrderByCreatedOn(int id);
}
