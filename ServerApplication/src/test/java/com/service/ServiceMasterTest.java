package com.service;

import com.Application;
import com.constants.Status;
import com.domain.Control;
import com.domain.Role;
import com.domain.Services;
import com.domain.User;
import com.repositories.ControlRepository;
import com.repositories.PostalServiceRepository;
import com.services.impl.MasterAddressServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ServiceMasterTest {

	@InjectMocks
	MasterAddressServiceImpl masterAddressServiceImpl;
	
	@Mock
	List<Services> servicesList;

	@Mock
	PostalServiceRepository postalServiceRepository;

	@Mock
	ControlRepository controlRepo;

	@Before
	public void init(){

		servicesList = new ArrayList<>();
		Services servicesP = new Services();
		servicesP.setId(1L);
		servicesP.setParentServiceId(null);
		servicesP.setServiceCode("PPP");
		servicesP.setServiceName("New Parent Service");
		servicesP.setStatus(Status.ACTIVE);
		servicesList.add(servicesP);

		Services servicesC = new Services();
		servicesC.setId(2L);
		servicesC.setParentServiceId(1L);
		servicesC.setServiceCode("CCC");
		servicesC.setServiceName("New Child Service");
		servicesC.setStatus(Status.ACTIVE);
		servicesList.add(servicesC);
	}
	@Test
	public void fetchServiceList(){

		Mockito.when(postalServiceRepository.findByParentServiceIdIsNullAndStatus(Status.ACTIVE)).thenReturn(servicesList);
		Assert.assertEquals(servicesList, masterAddressServiceImpl.fetchServicesList());
	}

	@Test
	public void fetchServiceListIsEmpty(){

		List<Services> servicesList1 = new ArrayList<>();
		Mockito.when(postalServiceRepository.findByParentServiceIdIsNullAndStatus(Status.ACTIVE)).thenReturn(servicesList1);
		Assert.assertEquals(true, masterAddressServiceImpl.fetchServicesList().isEmpty());
	}

	@Test
	public void fetchExistingServicesTrue(){

		Mockito.when(postalServiceRepository.findByServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus("New Parent Service", Status.ACTIVE,
				"PPP", Status.ACTIVE)).thenReturn(servicesList);
		Assert.assertTrue(masterAddressServiceImpl.fetchExistingServices("New Parent Service","PPP"));
	}

	@Test
	public void fetchExistingServicesFalse(){

		List<Services> servicesList1 = new ArrayList<>();
		Mockito.when(postalServiceRepository.findByServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus("New Parent Service", Status.ACTIVE,
				"PPP", Status.ACTIVE)).thenReturn(servicesList1);
		Assert.assertFalse(masterAddressServiceImpl.fetchExistingServices("New Parent Service","PPP"));
	}

	@Test
	public void fetExistingServicesForUpdateTrue(){

		Mockito.when(postalServiceRepository.findByServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus("New Parent Service", Status.ACTIVE,
				"PPP", Status.ACTIVE)).thenReturn(servicesList);
		Assert.assertTrue(masterAddressServiceImpl.fetchExistingServicesForUpdate("New Parent Service","PPP"));
	}

	@Test
	public void fetExistingServicesForUpdateFalse(){

		List<Services> servicesList1 = new ArrayList<>();
		Mockito.when(postalServiceRepository.findByServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus("New Parent Service", Status.ACTIVE,
				"PPP", Status.ACTIVE)).thenReturn(servicesList1);
		Assert.assertFalse(masterAddressServiceImpl.fetchExistingServicesForUpdate("New Parent Service","PPP"));
	}

	@Test
	public void addServices(){

		Services services = new Services();
		services.setId(1L);
		services.setParentServiceId(null);
		services.setServiceCode("PPP");
		services.setServiceName("New Parent Service");
		services.setStatus(Status.ACTIVE);
		services.setUpdatedOn(new Date());

		Role role = new Role();
		role.setId(1);
		role.setStatus(Status.ACTIVE);

		User user = new User();
		user.setId(1);
		user.setRole(role);
		user.setStatus(Status.ACTIVE);

		List<Control> controlList = new ArrayList<Control>();
		Control control = new Control();
		controlList.add(control);

		Mockito.when(postalServiceRepository.save(services)).thenReturn(services);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		Assert.assertEquals(services.getServiceName(), masterAddressServiceImpl.addServices("New Parent Service", "PPP", user).getServiceName());
	}

	@Test
	public void updateServices(){

		Services services1 = new Services();
		services1.setId(1L);
		services1.setParentServiceId(null);
		services1.setServiceCode("UPPP");
		services1.setServiceName("Update Parent Service");
		services1.setStatus(Status.ACTIVE);
		services1.setUpdatedOn(new Date());

		Services services2 = new Services();
		services2.setId(2L);
		services2.setParentServiceId(null);
		services2.setServiceCode("PPP");
		services2.setServiceName("New Parent Service");
		services2.setStatus(Status.DISABLED);
		services2.setUpdatedOn(new Date());

		Role role = new Role();
		role.setId(1);
		role.setStatus(Status.ACTIVE);

		User user = new User();
		user.setId(1);
		user.setRole(role);
		user.setStatus(Status.ACTIVE);

		List<Control> controlList = new ArrayList<Control>();
		Control control = new Control();
		controlList.add(control);

		Mockito.when(postalServiceRepository.findById(1L)).thenReturn(java.util.Optional.of(services1));
		Mockito.when(postalServiceRepository.save(services2)).thenReturn(services2);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		Assert.assertEquals(services1, masterAddressServiceImpl.updateServices(1L,"Update Parent Service", "PPP", user));
	}

	@Test
	public void updateSubServices(){

		Services services1 = new Services();
		services1.setId(2L);
		services1.setParentServiceId(1L);
		services1.setServiceCode("UCCC");
		services1.setServiceName("Update New Child Service");
		services1.setStatus(Status.ACTIVE);
		services1.setUpdatedOn(new Date());

		Services services2 = new Services();
		services2.setId(3L);
		services2.setParentServiceId(1L);
		services2.setServiceCode("CCC");
		services2.setServiceName("New Child Service");
		services2.setStatus(Status.DISABLED);
		services2.setUpdatedOn(new Date());

		Role role = new Role();
		role.setId(1);
		role.setStatus(Status.ACTIVE);

		User user = new User();
		user.setId(1);
		user.setRole(role);
		user.setStatus(Status.ACTIVE);

		List<Control> controlList = new ArrayList<Control>();
		Control control = new Control();
		controlList.add(control);

		Mockito.when(postalServiceRepository.findById(2L)).thenReturn(java.util.Optional.of(services1));
		Mockito.when(postalServiceRepository.save(services2)).thenReturn(services2);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		Assert.assertEquals("Update New Child Service", masterAddressServiceImpl.updateSubServices(1L, 2L, "Update New Child Service", "UCCC", user).getServiceName());
	}

	@Test
	public void deleteServiceServices(){

		List<Services> servicesList1 = new ArrayList<>();
		Services servicesP = new Services();
		servicesP.setId(1L);
		servicesP.setParentServiceId(null);
		servicesP.setServiceCode("PPP");
		servicesP.setServiceName("New Parent Service");
		servicesP.setStatus(Status.ACTIVE);
		servicesP.setUpdatedOn(new Date());
		servicesList1.add(servicesP);

		Services servicesC = new Services();
		servicesC.setId(2L);
		servicesC.setParentServiceId(1L);
		servicesC.setServiceCode("CCC");
		servicesC.setServiceName("New Child Service");
		servicesC.setStatus(Status.ACTIVE);
		servicesList1.add(servicesC);

		Role role = new Role();
		role.setId(1);
		role.setStatus(Status.ACTIVE);

		User user = new User();
		user.setId(1);
		user.setRole(role);
		user.setStatus(Status.ACTIVE);

		List<Control> controlList = new ArrayList<Control>();
		Control control = new Control();
		controlList.add(control);

		Mockito.when(postalServiceRepository.findById(1L)).thenReturn(java.util.Optional.of(servicesP));
		Mockito.when(postalServiceRepository.findByParentServiceIdAndStatus(1L, Status.ACTIVE)).thenReturn(servicesList1);

		Mockito.when(postalServiceRepository.save(servicesP)).thenReturn(servicesP);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		Assert.assertEquals(servicesP, masterAddressServiceImpl.deleteService(1L, user));
	}

	@Test
	public void onlyUpdateSubService(){

		Services servicesC = new Services();
		servicesC.setId(2L);
		servicesC.setParentServiceId(1L);
		servicesC.setServiceCode("CCC");
		servicesC.setServiceName("New Child Service");
		servicesC.setUpdatedOn(new Date());
		servicesC.setStatus(Status.ACTIVE);

		Role role = new Role();
		role.setId(1);
		role.setStatus(Status.ACTIVE);

		User user = new User();
		user.setId(1);
		user.setRole(role);
		user.setStatus(Status.ACTIVE);

		List<Control> controlList = new ArrayList<Control>();
		Control control = new Control();
		controlList.add(control);

		Mockito.when(postalServiceRepository.findById(2L)).thenReturn(java.util.Optional.of(servicesC));
		Mockito.when(postalServiceRepository.save(servicesC)).thenReturn(servicesC);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		Assert.assertEquals(Status.DISABLED, masterAddressServiceImpl.onlyUpdateSubService(2L, user).getStatus());
	}

	@Test
	public void fetchExistingSubServicesTrue() throws Exception {

		List<Services> servicesList1 = new ArrayList<>();
		Services servicesC = new Services();
		servicesC.setId(2L);
		servicesC.setParentServiceId(1L);
		servicesC.setServiceCode("CCC");
		servicesC.setServiceName("New Child Service");
		servicesC.setStatus(Status.ACTIVE);
		servicesList1.add(servicesC);

		Mockito.when(postalServiceRepository
				.findByParentServiceIdAndStatusOrServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus
						(1L, Status.ACTIVE, "New Child Service", Status.ACTIVE, "CCC", Status.ACTIVE)).thenReturn(servicesList1);
		Assert.assertTrue(masterAddressServiceImpl.fetchExistingSubServices(1L,"New Child Service","CCC"));
	}

	@Test
	public void fetchExistingSubServicesFalse() throws Exception {

		List<Services> servicesList1 = new ArrayList<>();
		Mockito.when(postalServiceRepository
				.findByParentServiceIdAndStatusOrServiceNameContainingIgnoreCaseAndStatusOrServiceCodeContainingIgnoreCaseAndStatus
						(1L, Status.ACTIVE, "New Child Service", Status.ACTIVE, "CCC", Status.ACTIVE)).thenReturn(servicesList1);
		Assert.assertFalse(masterAddressServiceImpl.fetchExistingSubServices(1L,"New Child Service","CCC"));
	}

	@Test
	public void fetchExistingSubServicesOnUpdateTrue() throws Exception {

		List<Services> servicesList1 = new ArrayList<>();
		Services servicesC = new Services();
		servicesC.setId(2L);
		servicesC.setParentServiceId(1L);
		servicesC.setServiceCode("CCC");
		servicesC.setServiceName("New Child Service");
		servicesC.setStatus(Status.ACTIVE);
		servicesList1.add(servicesC);

		Mockito.when(postalServiceRepository
				.findByParentServiceIdAndServiceNameContainingIgnoreCaseAndServiceCodeContainingIgnoreCaseAndStatus
						(1L, "New Child Service", "CCC", Status.ACTIVE)).thenReturn(servicesList1);
		Assert.assertTrue(masterAddressServiceImpl.fetchExistingSubServicesOnUpdate(1L,"New Child Service","CCC"));
	}

	@Test
	public void fetchExistingSubServicesOnUpdateFalse() throws Exception {

		List<Services> servicesList1 = new ArrayList<>();
		Mockito.when(postalServiceRepository
				.findByParentServiceIdAndServiceNameContainingIgnoreCaseAndServiceCodeContainingIgnoreCaseAndStatus
						(1L, "New Child Service", "CCC", Status.ACTIVE)).thenReturn(servicesList1);
		Assert.assertFalse(masterAddressServiceImpl.fetchExistingSubServicesOnUpdate(1L,"New Child Service","CCC"));
	}

	@Test
	public void addSubServices(){

		Services services = new Services();
		services.setId(2L);
		services.setParentServiceId(1L);
		services.setServiceCode("CCC");
		services.setServiceName("New Child Service");
		services.setStatus(Status.ACTIVE);
		services.setUpdatedOn(new Date());

		Role role = new Role();
		role.setId(1);
		role.setStatus(Status.ACTIVE);

		User user = new User();
		user.setId(1);
		user.setRole(role);
		user.setStatus(Status.ACTIVE);

		List<Control> controlList = new ArrayList<Control>();
		Control control = new Control();
		controlList.add(control);

		Mockito.when(postalServiceRepository.save(services)).thenReturn(services);
		Mockito.when(controlRepo.findAll()).thenReturn(controlList);
		Assert.assertEquals("New Child Service", masterAddressServiceImpl.addSubServices(1L,"New Child Service", "CCC", user).getServiceName());
	}

	@Test
	public void fetchAllServicesList(){

		Role role = new Role();
		role.setId(1);
		role.setStatus(Status.ACTIVE);

		User user = new User();
		user.setId(1);
		user.setStatus(Status.ACTIVE);
		user.setName("Ibney Ali");
		user.setRole(role);

		List<Services> servicesList1 = new ArrayList<>();
		Services servicesP = new Services();
		servicesP.setId(1L);
		servicesP.setCreatedBy(user);
		servicesP.setUpdatedBy(user);
		servicesP.setCreatedOn(new Date());
		servicesP.setUpdatedOn(new Date());
		servicesP.setParentServiceId(null);
		servicesP.setServiceCode("PPP");
		servicesP.setServiceName("New Parent Service");
		servicesP.setStatus(Status.ACTIVE);
		servicesList1.add(servicesP);

		Services servicesC = new Services();
		servicesC.setId(2L);
		servicesC.setUpdatedBy(user);
		servicesC.setCreatedBy(user);
		servicesC.setCreatedOn(new Date());
		servicesC.setUpdatedOn(new Date());
		servicesC.setParentServiceId(1L);
		servicesC.setServiceCode("CCC");
		servicesC.setServiceName("New Child Service");
		servicesC.setStatus(Status.ACTIVE);
		servicesList1.add(servicesC);

		Mockito.when(postalServiceRepository.findAll()).thenReturn(servicesList1);
		Mockito.when(postalServiceRepository.findById(1L)).thenReturn(java.util.Optional.of(servicesP));
		Assert.assertEquals("New Parent Service", masterAddressServiceImpl.getAllServicesList().get(0).getServiceName());
	}

	@Test
	public void fetchAllServicesListByStatus(){

		Role role = new Role();
		role.setId(1);
		role.setStatus(Status.ACTIVE);

		User user = new User();
		user.setId(1);
		user.setStatus(Status.ACTIVE);
		user.setName("Ibney Ali");
		user.setRole(role);

		List<Services> servicesList1 = new ArrayList<>();
		Services servicesP = new Services();
		servicesP.setId(1L);
		servicesP.setCreatedBy(user);
		servicesP.setUpdatedBy(user);
		servicesP.setCreatedOn(new Date());
		servicesP.setUpdatedOn(new Date());
		servicesP.setParentServiceId(null);
		servicesP.setServiceCode("PPP");
		servicesP.setServiceName("New Parent Service");
		servicesP.setStatus(Status.ACTIVE);
		servicesList1.add(servicesP);

		Services servicesC = new Services();
		servicesC.setId(2L);
		servicesC.setUpdatedBy(user);
		servicesC.setCreatedBy(user);
		servicesC.setCreatedOn(new Date());
		servicesC.setUpdatedOn(new Date());
		servicesC.setParentServiceId(1L);
		servicesC.setServiceCode("CCC");
		servicesC.setServiceName("New Child Service");
		servicesC.setStatus(Status.ACTIVE);
		servicesList1.add(servicesC);

		Mockito.when(postalServiceRepository.findAllByStatusOrderByServiceNameAsc(Status.ACTIVE)).thenReturn(servicesList1);
		Mockito.when(postalServiceRepository.findById(1L)).thenReturn(java.util.Optional.of(servicesP));
		Assert.assertEquals(Status.ACTIVE, masterAddressServiceImpl.getAllServicesListByStatus(Status.ACTIVE).get(0).getStatus());
	}

	@Test
	public void fetchAllServicesEmptyList(){

		Mockito.when(postalServiceRepository.findAllByStatusOrderByServiceNameAsc(Status.DISABLED)).thenReturn(null);
		Mockito.when(postalServiceRepository.findById(1L)).thenReturn(null);
		Assert.assertEquals(null, masterAddressServiceImpl.getAllServicesListByStatus(Status.DISABLED));
	}


}