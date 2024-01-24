package com.repositories;

import java.util.Collection;
import java.util.List;

import com.constants.ClientStatus;
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

	long countByPostalCode(int postalCode);

	long countByMachineIdAndClientStatusAndStatus(String machineId, String clientStatus, Status active);
	
	
	
	List<Client> findAllByClientStatusAndStatus(String clientStatus, Status status);

	List<Client> findByZoneAndClientStatusAndStatus(String zone, String clientStatus, Status status);
	
	List<Client> findByZoneAndDivisionAndClientStatusAndStatus(String zone, String div, String clientStatus, Status status);
	
	List<Client> findByZoneAndDivisionAndDistrictAndClientStatusAndStatus(String zone, String div, String dist, String clientStatus, Status status);
	
	List<Client> findByZoneAndDivisionAndDistrictAndThanaAndClientStatusAndStatus(String zone, String div, String dist, String thana, String clientStatus, Status status);
	
	List<Client> findByZoneAndDivisionAndDistrictAndThanaAndSubOfficeAndClientStatusAndStatus(String zone, String div, String dist, String thana, String subOffice, String clientStatus, Status status);
	
}
