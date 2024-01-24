package com.repositories;

import com.constants.Status;
import com.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByClientIdAndStatus(String clientId, Status s);
}
