package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.constants.Status;
import com.domain.Services;

public interface PostalServiceRepository extends JpaRepository<Services, Long> {

    List<Services> findByParentServiceIdNullAndStatus(Status status);
    List<Services> findByParentServiceIdAndStatus(Long serviceId, Status status);
    List<Services> findByIdInAndStatus(List<Long> serviceIds, Status status);
    List<Services> findByParentServiceIdIsNullAndStatus(Status status);
    List<Services> findByServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus(String service, Status status1, String serviceCode, Status status2);
    List<Services> findByParentServiceIdAndStatusOrServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus(Long parentServiceId, Status status1, String serviceName, Status status2, String serviceCode, Status status3);
    List<Services> findByParentServiceIdAndServiceNameContainingIgnoreCaseAndServiceCodeContainingIgnoreCaseAndStatus(Long parentServiceId, String serviceName, String serviceCode, Status status);
    List<Services> findAllByStatusOrderByServiceNameAsc(Status status);
    List<Services> findByParentServiceIdNotNullAndStatus(Status status);
	Services findByIdAndStatus(Long parentServiceId, Status active);
	Services findTopByIdOrderByUpdatedOnDesc(Long parentServiceId);
	List<Services> findByParentServiceIdAndStatusOrderByServiceNameAsc(Long parentServiceId, Status status);
	
	List<Services> findByParentServiceIdIsNotNullAndStatusOrderByServiceNameAsc(Status status);
	List<Services> findByParentServiceIdIsNullAndStatusOrderByServiceNameAsc(Status status);
	Services findByIdAndStatusOrderByServiceNameAsc(Long id, Status active);
	@Query("select id from Services where serviceName in (select DISTINCT(serviceName) from Services)")
	List<Long> findDistinctServiceName();

}
