package com.repositories;

import com.constants.Status;
import com.domain.Control;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Statement;

public interface ControlRepository extends JpaRepository<Control, Long> {
    Control findByStatus(Status status);
}
