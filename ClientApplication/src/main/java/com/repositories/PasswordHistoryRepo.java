package com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.domain.PasswordHistory;

@Repository
@Transactional
public interface PasswordHistoryRepo extends JpaRepository<PasswordHistory, Long>{

}
