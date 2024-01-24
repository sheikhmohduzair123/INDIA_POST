package com.repositories;

import com.constants.Status;
import com.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT r FROM Role r WHERE r.updatedOn >= ?1")
    List<Role> findByUpdatedOnGreaterThanSyncTime(Timestamp syncTimestamp);

	List<Role> findByNameAndStatus(String string, Status s);

	Role findOneByName(String string);
}
