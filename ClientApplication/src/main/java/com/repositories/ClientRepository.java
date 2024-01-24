package com.repositories;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import com.constants.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import com.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

//	List<Client> findByClientStatus(String status);

	List<Client> findByPostalCodeAndStatus(int postalCode, Status status);

	//List<Client> findByStatus(Status status);

	Client findClientByClientId(String clientId);

	List<Client> findByClientStatusAndStatus(String clientStatus, Status status);

	List<Client> findByClientStatusInAndStatus(Collection clientStatus, Status status);

	   Client findClientByClientIdAndStatus(String clientId, Status status);
	   Client findClientByClientIdAndClientStatusAndStatus(String clientId, String clientStatus, Status status);
	   Client findClientByClientTokenAndClientStatusAndStatus(String clientToken, String clientStatus, Status status);
       //Client findClientByStatus(Status active);

	long countByClientStatusInAndStatus(List<String> clientstatuses, Status active);
	
	@Transactional
	List<Client> findAllByCreatedOnBeforeAndStatus(Timestamp tsFromDate, Status disabled);


}
