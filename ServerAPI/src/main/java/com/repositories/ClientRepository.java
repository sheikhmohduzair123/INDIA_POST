package com.repositories;

import com.constants.Status;
import com.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByClientIdAndStatus(String clientId, Status status);
    Client findClientByClientIdAndClientStatusAndStatus(String clientId, String clientStatus, Status status);
    Client findClientByClientTokenAndClientStatusAndStatus(String clientToken, String clientStatus, Status status);
	//Client findClientByClientId(String clientId);
}
