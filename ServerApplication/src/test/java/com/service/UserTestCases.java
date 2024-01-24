package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.constants.Status;
import com.domain.Address;
import com.domain.Control;
import com.domain.RmsTable;
import com.domain.Role;
import com.domain.User;
import com.repositories.ControlRepository;
import com.repositories.RmsRepository;
import com.repositories.RoleRepository;
import com.repositories.SUserRepository;
import com.services.impl.UserRegistrationServiceImpl;
import com.util.MailEngine;
import com.util.UpdatePasswordError;
import com.vo.CreateUserVo;
import com.vo.UserVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class UserTestCases {

	@InjectMocks
	UserRegistrationServiceImpl registrationServiceimp;

	@Mock
	SUserRepository userRepository;

	@Mock
	MailEngine mailEngine;

	@Mock
	ControlRepository controlRepository;

	@Mock
	RmsRepository rmsRepository;

	@Mock
	RoleRepository roleRepository;

	@Mock
	UpdatePasswordError message;

	User loggedUser;

	User userPO;

	User userFD;

	User userAdmin;

	User userRMS;

	Role roleCurrentUser;

	Role rolePO;

	Role roleAdmin;

	Role roleFD;

	Role roleRMS;

	Address address;

	RmsTable rmsTable;

	List<Control> controlList;

	Control control1;

	String reSentMail = "Mail sent.";
	String mailSentSuccesfully = "User has been created !";
	String emailAlreadyExsits = "Email already Exists";
	String massageEmailError = "Something went wrong Please try Again !";

	String applicationSecret = "asd67@we";

	@Before
	public void init() {
		address = new Address();
		address.setPostalCode(1212);
		rmsTable = new RmsTable();
		rmsTable.setId(5l);
		rmsTable.setRmsName("noida");
		rmsTable.setRmsType("GPO");
		rmsTable.setRmsAddress(address);

		rolePO = new Role();
		rolePO.setDisplayName("Post Office User");
		roleAdmin = new Role();
		roleAdmin.setDisplayName("Admin");
		roleFD = new Role();
		roleFD.setDisplayName("Front Desk User");
		roleRMS = new Role();
		roleRMS.setDisplayName("RMS User");

		loggedUser = new User();

		loggedUser.setId(3);
		loggedUser.setName("AdminUser123");
		loggedUser.setEmail("adminuser11@gmail.com");
		loggedUser.setRole(roleAdmin);
		loggedUser.setPostalCode(0);
		loggedUser.setRmsId(null);
		loggedUser.setUpdatedOn(new Date());
		loggedUser.setStatus(Status.ACTIVE);
		loggedUser.setEnabled(true);

		userPO = new User();

		userPO.setId(33);
		userPO.setName("POUserXYZ");
		userPO.setEmail("pouser1@gmail.com");
		userPO.setRole(rolePO);
		userPO.setPostalCode(1000);
		userPO.setDob(new Date());
		userPO.setStatus(Status.ACTIVE);
		userPO.setUpdatedOn(new Date());

		userFD = new User();

		userFD.setId(43);
		userFD.setName("FDUserABC");
		userFD.setEmail("fduser1@gmail.com");
		userFD.setRole(roleFD);
		userFD.setPostalCode(1513);
		userFD.setDob(new Date());
		userFD.setStatus(Status.ACTIVE);
		userFD.setUpdatedOn(new Date());

		userAdmin = new User();

		userAdmin.setId(12);
		userAdmin.setName("AdminKK");
		userAdmin.setEmail("admin33@gmail.com");
		userAdmin.setRole(roleAdmin);
		userAdmin.setPostalCode(0);
		userAdmin.setRmsId(null);
		userAdmin.setDob(new Date());
		userAdmin.setStatus(Status.ACTIVE);
		userAdmin.setUpdatedOn(new Date());

		userRMS = new User();

		userRMS.setId(21);
		userRMS.setName("RMSUserXYZ");
		userRMS.setEmail("rmsuser1@gmail.com");
		userRMS.setRole(roleRMS);
		userRMS.setPostalCode(0);
		userRMS.setDob(new Date());
		userRMS.setStatus(Status.ACTIVE);
		userRMS.setUpdatedOn(new Date());
		userRMS.setRmsId(rmsTable.getId());

		Mockito.when(message.getEmailAlreadyExits()).thenReturn(emailAlreadyExsits);
		Mockito.when(message.getResentmail()).thenReturn(reSentMail);
		Mockito.when(message.getMailsent()).thenReturn(mailSentSuccesfully);
		Mockito.when(message.getSomethingGotWrong()).thenReturn(massageEmailError);

		controlList = new ArrayList<Control>();
		control1 = new Control();

	}

	@Test
	public void registerUserTestFrootDeskUser() {

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail("ap.prashantgu@gmail.com");
		createUserVo.setPostalCode("1000");
		createUserVo.setName("new name");
		createUserVo.setRole("Front Desk User");
		createUserVo.setDateOfBirth("2011-11-11");
		control1.setId(1L);
		controlList.add(control1);

		User userFD = new User();
		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(null);

		Mockito.when(userRepository.save(userFD)).thenReturn(userFD);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		Assert.assertEquals(message.getMailsent(), registrationServiceimp.registerUser(createUserVo, loggedUser));

	}

	@Test
	public void registerUserTestPOUser() {

		User userPO = new User();

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail("abc@gmail.com");
		createUserVo.setPostalCode("1212");
		createUserVo.setName("XYZ");
		createUserVo.setRole("Post Office User");
		createUserVo.setDateOfBirth("2005-05-05");

		control1.setId(2L);
		controlList.add(control1);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(null);

		Mockito.when(userRepository.save(userPO)).thenReturn(userPO);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		Assert.assertEquals(message.getMailsent(), registrationServiceimp.registerUser(createUserVo, loggedUser));

	}

	@Test
	public void registerUserTestRMSUser() {

		User userRMS = new User();
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail("rms@gmail.com");
		createUserVo.setPostalCode("0");
		createUserVo.setRmsId("noida,GPO,1212");
		createUserVo.setName("RMS123");
		createUserVo.setRole("RMS User");
		createUserVo.setDateOfBirth("2005-05-05");

		control1.setId(3L);
		controlList.add(control1);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(null);

		Mockito.when(rmsRepository.findByRmsNameAndRmsTypeAndRmsAddress_PostalCodeAndStatus("noida", "GPO", 1212,
				Status.ACTIVE)).thenReturn(rmsTable);

		Mockito.when(userRepository.save(userRMS)).thenReturn(userRMS);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		Assert.assertEquals(message.getMailsent(), registrationServiceimp.registerUser(createUserVo, loggedUser));

	}

	@Test
	public void registerUserTestAdminUser() {

		User userAdmin = new User();
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail("admin@gmail.com");
		createUserVo.setPostalCode("0");
		createUserVo.setName("AdminName");
		createUserVo.setRole("Admin");
		createUserVo.setDateOfBirth("2010-10-10");

		control1.setId(4L);
		controlList.add(control1);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(null);

		Mockito.when(userRepository.save(userAdmin)).thenReturn(userAdmin);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		Assert.assertEquals(message.getMailsent(), registrationServiceimp.registerUser(createUserVo, loggedUser));

	}

	@Test
	public void reMailUserTest() {
		User currentUser = new User();
		currentUser.setEmail("currentuser@gmail.com");
		currentUser.setEnabled(true);
		currentUser.setStatus(Status.ACTIVE);

		control1.setId(5L);
		controlList.add(control1);

		Mockito.when(userRepository.findUserByEmailContainingIgnoreCaseAndStatusAndEnabled(currentUser.getEmail(),
				Status.ACTIVE, true)).thenReturn(currentUser);
		Mockito.when(userRepository.save(currentUser)).thenReturn(currentUser);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		assertEquals(message.getResentmail(),
				registrationServiceimp.resendMailUserService("currentuser@gmail.com", loggedUser));

	}

	@Test
	public void Negative_ReMailUserTestWhenUserNull() {
		User currentUser = new User();
		currentUser.setEmail("currentuser@gmail.com");
		currentUser.setEnabled(true);
		currentUser.setStatus(Status.ACTIVE);

		Mockito.when(userRepository.findUserByEmailContainingIgnoreCaseAndStatusAndEnabled(currentUser.getEmail(),
				Status.ACTIVE, true)).thenReturn(null);

		assertEquals(message.getSomethingGotWrong(),
				registrationServiceimp.resendMailUserService("currentuser@gmail.com", loggedUser));

	}

	@Test
	public void UserEnableAndDisableTestCaseForIfPart() {
		User currentUser = new User();
		currentUser.setEmail("currentuser@gmail.com");
		currentUser.setEnabled(false);
		currentUser.setStatus(Status.ACTIVE);
		control1.setId(6L);
		controlList.add(control1);
		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus("currentuser@gmail.com", Status.ACTIVE))
				.thenReturn(currentUser);
		Mockito.when(userRepository.save(currentUser)).thenReturn(currentUser);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		UserVo responseUserVo = registrationServiceimp.enableAndDisableUserService("currentuser@gmail.com", loggedUser);
		assertEquals(currentUser.getStatus(), responseUserVo.getStatus());
		assertEquals(true, responseUserVo.isEnabled());
	}

	@Test
	public void UserEnableAndDisableTestCaseForElsePart() {
		User currentUser = new User();
		currentUser.setEmail("currentuser@gmail.com");
		currentUser.setEnabled(true);
		currentUser.setStatus(Status.ACTIVE);
		control1.setId(7L);
		controlList.add(control1);
		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus("currentuser@gmail.com", Status.ACTIVE))
				.thenReturn(currentUser);
		Mockito.when(userRepository.save(currentUser)).thenReturn(currentUser);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		UserVo responseUserVo = registrationServiceimp.enableAndDisableUserService("currentuser@gmail.com", loggedUser);
		assertEquals(currentUser.getStatus(), responseUserVo.getStatus());
		assertEquals(false, responseUserVo.isEnabled());
	}

	@Test
	public void DeleteUserIfPart() {
		User currentUser = new User();
		currentUser.setEmail("currentuser@gmail.com");
		currentUser.setEnabled(false);
		currentUser.setStatus(Status.DISABLED);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus("currentuser@gmail.com", Status.ACTIVE))
				.thenReturn(null);

		assertEquals(null, registrationServiceimp.updateUserStatus("currentuser@gmail.com", loggedUser));
	}

	@Test
	public void DeleteUserElsePart() {

		User currentUser = new User();
		currentUser.setEmail("currentuser@gmail.com");
		currentUser.setEnabled(true);
		currentUser.setStatus(Status.ACTIVE);
		control1.setId(8L);
		controlList.add(control1);
		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus("currentuser@gmail.com", Status.ACTIVE))
				.thenReturn(currentUser);
		Mockito.when(userRepository.save(currentUser)).thenReturn(currentUser);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		UserVo responseUserVo = registrationServiceimp.updateUserStatus("currentuser@gmail.com", loggedUser);
		assertEquals(Status.DISABLED, responseUserVo.getStatus());
		assertEquals(false, responseUserVo.isEnabled());
	}

	@Test
	public void UserListWhenActiveFilterIsSelect() {
		List<User> activeUserList = new ArrayList<User>();
		activeUserList.add(loggedUser);
		User userA = new User();
		userA.setStatus(Status.ACTIVE);
		userA.setEnabled(true);
		userA.setUpdatedOn(new Date());
		activeUserList.add(userA);
		User userB = new User();
		userB.setStatus(Status.ACTIVE);
		userB.setEnabled(true);
		userB.setUpdatedOn(new Date());
		activeUserList.add(userB);
		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(true, Status.ACTIVE))
				.thenReturn(activeUserList);

		assertEquals(activeUserList.get(0).getStatus(),
				registrationServiceimp.findUserListByStatus(loggedUser, "active").get(0).getStatus());
		assertEquals(activeUserList.get(1).getStatus(),
				registrationServiceimp.findUserListByStatus(loggedUser, "active").get(1).getStatus());
		assertEquals(activeUserList.get(0).isEnabled(),
				registrationServiceimp.findUserListByStatus(loggedUser, "active").get(0).isEnabled());
		assertEquals(activeUserList.get(1).isEnabled(),
				registrationServiceimp.findUserListByStatus(loggedUser, "active").get(1).isEnabled());

		assertEquals(2, registrationServiceimp.findUserListByStatus(loggedUser, "active").size());

	}

	@Test
	public void NegativeUserListWhenActiveFilterIsSelectNoData() {
		List<User> activeUserList = new ArrayList<User>();
		activeUserList.add(loggedUser);

		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(true, Status.ACTIVE))
				.thenReturn(activeUserList);

		assertEquals(true, registrationServiceimp.findUserListByStatus(loggedUser, "active").isEmpty());
		assertEquals(0, registrationServiceimp.findUserListByStatus(loggedUser, "active").size());
	}

	@Test
	public void UserListWhenDisabledFilterIsSelect() {
		List<User> disableduserlist = new ArrayList<User>();

		User userA = new User();
		userA.setStatus(Status.ACTIVE);
		userA.setEnabled(false);
		userA.setUpdatedOn(new Date());
		disableduserlist.add(userA);
		User userB = new User();
		userB.setStatus(Status.ACTIVE);
		userB.setEnabled(false);
		userB.setUpdatedOn(new Date());
		disableduserlist.add(userB);
		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.ACTIVE))
				.thenReturn(disableduserlist);

		assertEquals(disableduserlist.get(0).getStatus(),
				registrationServiceimp.findUserListByStatus(loggedUser, "disabled").get(0).getStatus());
		assertEquals(disableduserlist.get(1).getStatus(),
				registrationServiceimp.findUserListByStatus(loggedUser, "disabled").get(1).getStatus());
		assertEquals(disableduserlist.get(0).isEnabled(),
				registrationServiceimp.findUserListByStatus(loggedUser, "disabled").get(0).isEnabled());
		assertEquals(disableduserlist.get(1).isEnabled(),
				registrationServiceimp.findUserListByStatus(loggedUser, "disabled").get(1).isEnabled());

		assertEquals(2, registrationServiceimp.findUserListByStatus(loggedUser, "disabled").size());

	}

	@Test
	public void NegativeUserListWhenDisabledFilterIsSelectNoData() {
		List<User> disableduserlist = new ArrayList<User>();

		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.ACTIVE))
				.thenReturn(disableduserlist);

		assertEquals(true, registrationServiceimp.findUserListByStatus(loggedUser, "disabled").isEmpty());
		assertEquals(0, registrationServiceimp.findUserListByStatus(loggedUser, "disabled").size());

	}

	@Test
	public void UserListWhenDeletedFilterIsSelect() {
		List<User> deleteduserlist = new ArrayList<User>();
		User userA = new User();
		userA.setStatus(Status.DISABLED);
		userA.setEnabled(false);
		userA.setUpdatedOn(new Date());
		deleteduserlist.add(userA);
		User userB = new User();
		userB.setStatus(Status.DISABLED);
		userB.setEnabled(false);
		userB.setUpdatedOn(new Date());
		deleteduserlist.add(userB);
		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.DISABLED))
				.thenReturn(deleteduserlist);

		assertEquals(deleteduserlist.get(0).getStatus(),
				registrationServiceimp.findUserListByStatus(loggedUser, "deleted").get(0).getStatus());
		assertEquals(deleteduserlist.get(1).getStatus(),
				registrationServiceimp.findUserListByStatus(loggedUser, "deleted").get(1).getStatus());
		assertEquals(deleteduserlist.get(0).isEnabled(),
				registrationServiceimp.findUserListByStatus(loggedUser, "deleted").get(0).isEnabled());
		assertEquals(deleteduserlist.get(1).isEnabled(),
				registrationServiceimp.findUserListByStatus(loggedUser, "deleted").get(1).isEnabled());

		assertEquals(2, registrationServiceimp.findUserListByStatus(loggedUser, "deleted").size());

	}

	@Test
	public void NegativeUserListWhenDeletedFilterIsSelectNoData() {
		List<User> deleteduserlist = new ArrayList<User>();

		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.DISABLED))
				.thenReturn(deleteduserlist);

		assertEquals(true, registrationServiceimp.findUserListByStatus(loggedUser, "deleted").isEmpty());
		assertEquals(0, registrationServiceimp.findUserListByStatus(loggedUser, "deleted").size());

	}

	@Test
	public void UserListWhenAllFilterIsSelect() {
		List<User> listAll = new ArrayList<User>();
		List<User> activeUserList = new ArrayList<User>();
		activeUserList.add(loggedUser);
		User userA = new User();
		userA.setStatus(Status.ACTIVE);
		userA.setEnabled(true);
		userA.setUpdatedOn(new Date());
		activeUserList.add(userA);

		List<User> disableduserlist = new ArrayList<User>();
		User userB = new User();
		userB.setStatus(Status.ACTIVE);
		userB.setEnabled(false);
		userB.setUpdatedOn(new Date());
		disableduserlist.add(userB);

		List<User> deleteduserlist = new ArrayList<User>();
		User userC = new User();
		userC.setStatus(Status.DISABLED);
		userC.setEnabled(false);
		userC.setUpdatedOn(new Date());
		deleteduserlist.add(userC);

		listAll.addAll(activeUserList);
		listAll.addAll(disableduserlist);
		listAll.addAll(deleteduserlist);

		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(true, Status.ACTIVE))
				.thenReturn(activeUserList);

		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.ACTIVE))
				.thenReturn(disableduserlist);

		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.DISABLED))
				.thenReturn(deleteduserlist);

		assertEquals(activeUserList.get(0).getStatus(),
				registrationServiceimp.findUserListByStatus(loggedUser, "all").get(0).getStatus());
		assertEquals(disableduserlist.get(0).getStatus(),
				registrationServiceimp.findUserListByStatus(loggedUser, "all").get(1).getStatus());
		assertEquals(deleteduserlist.get(0).getStatus(),
				registrationServiceimp.findUserListByStatus(loggedUser, "all").get(2).getStatus());
		assertEquals(activeUserList.get(0).isEnabled(),
				registrationServiceimp.findUserListByStatus(loggedUser, "all").get(0).isEnabled());
		assertEquals(disableduserlist.get(0).isEnabled(),
				registrationServiceimp.findUserListByStatus(loggedUser, "all").get(1).isEnabled());
		assertEquals(disableduserlist.get(0).isEnabled(),
				registrationServiceimp.findUserListByStatus(loggedUser, "all").get(2).isEnabled());

		assertEquals(3, registrationServiceimp.findUserListByStatus(loggedUser, "all").size());

	}

	@Test
	public void NegativeUserListWhenAllFilterIsSelectNoDataFound() {
		List<User> listAll = new ArrayList<User>();
		List<User> activeUserList = new ArrayList<User>();

		activeUserList.add(loggedUser);

		List<User> disableduserlist = new ArrayList<User>();

		List<User> deleteduserlist = new ArrayList<User>();

		listAll.addAll(activeUserList);
		listAll.addAll(disableduserlist);
		listAll.addAll(deleteduserlist);

		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(true, Status.ACTIVE))
				.thenReturn(activeUserList);

		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.ACTIVE))
				.thenReturn(disableduserlist);

		Mockito.when(userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.DISABLED))
				.thenReturn(deleteduserlist);

		assertEquals(true, registrationServiceimp.findUserListByStatus(loggedUser, "all").isEmpty());
		assertEquals(0, registrationServiceimp.findUserListByStatus(loggedUser, "all").size());

	}

	// ****Test Cases On FrootDeskUser Change to Another User Type****//
	@Test
	public void PositiveUserTestCaseForUpdatePoUserToFrootDeskUser() throws ParseException {
		User userFD = new User();

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userPO.getEmail());
		createUserVo.setPostalCode("1230");
		createUserVo.setName("ChangedPOUser123");
		createUserVo.setRole("Front Desk User");
		createUserVo.setDateOfBirth("2011-05-15");

		control1.setId(9L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userPO);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleFD);

		Mockito.when(userRepository.save(userFD)).thenReturn(userFD);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userPO.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userPO.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Front Desk User", responseUserVo.getRole());
		assertEquals("ChangedPOUser123", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-15").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("1230", responseUserVo.getPostalCode());
	}

	@Test
	public void PositiveUserTestCaseForUpdatePoUserToRMS_User() throws ParseException {
		User userRMS = new User();
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userPO.getEmail());
		createUserVo.setRmsId("noida,GPO,1212");
		String[] rmsdata = createUserVo.getRmsId().split(",");
		createUserVo.setName("ChangedPOUserToRMSuserABC");
		createUserVo.setRole("RMS User");
		createUserVo.setDateOfBirth("2013-10-21");

		control1.setId(10L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userPO);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);
		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleRMS);

		Mockito.when(rmsRepository.findByRmsNameAndRmsTypeAndRmsAddress_PostalCodeAndStatus(rmsdata[0], rmsdata[1],
				Integer.parseInt(rmsdata[2]), Status.ACTIVE)).thenReturn(rmsTable);

		Mockito.when(userRepository.save(userRMS)).thenReturn(userRMS);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userPO.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userPO.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("RMS User", responseUserVo.getRole());
		assertEquals("ChangedPOUserToRMSuserABC", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2013-10-21").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("5", responseUserVo.getRmsId());// "noida,GPO,1212" this address has 5 rmsId
	}

	@Test
	public void PositiveUserTestCaseForUpdatePoUserToAdminUser() throws ParseException {

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userPO.getEmail());
		createUserVo.setPostalCode("0");
		createUserVo.setName("ChangedPOUserToAdmin");
		createUserVo.setRole("Admin");
		createUserVo.setDateOfBirth("2015-12-23");

		control1.setId(11L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userPO);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);
		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleAdmin);

		Mockito.when(userRepository.save(userAdmin)).thenReturn(userAdmin);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userPO.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userPO.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Admin", responseUserVo.getRole());
		assertEquals("ChangedPOUserToAdmin", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2015-12-23").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("0", responseUserVo.getPostalCode());

	}

	// ****Test Cases On FrootDeskUser Change to Another User Type****//

	@Test
	public void PositiveUserTestCaseForUpdateFrootDeskUserToPO_User() throws ParseException {

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userFD.getEmail());
		createUserVo.setPostalCode("1100");
		createUserVo.setName("ChangedFDUserToPoUser");
		createUserVo.setRole("Post Office User");
		createUserVo.setDateOfBirth("2015-12-23");

		control1.setId(12L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userFD);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(rolePO);

		Mockito.when(userRepository.save(userPO)).thenReturn(userPO);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userFD.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userFD.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Post Office User", responseUserVo.getRole());
		assertEquals("ChangedFDUserToPoUser", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2015-12-23").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("1100", responseUserVo.getPostalCode());

	}

	@Test
	public void PositiveUserTestCaseForUpdateFrootDeskUserToRMS() throws ParseException {
		User userRMSNew = new User();
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userFD.getEmail());
		createUserVo.setRmsId("noida,GPO,1212");
		String[] rmsdata = createUserVo.getRmsId().split(",");
		createUserVo.setName("ChangedFDUserToRMSuser");
		createUserVo.setRole("RMS User");
		createUserVo.setDateOfBirth("2013-10-21");

		control1.setId(13L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userFD);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);
		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleRMS);

		Mockito.when(rmsRepository.findByRmsNameAndRmsTypeAndRmsAddress_PostalCodeAndStatus(rmsdata[0], rmsdata[1],
				Integer.parseInt(rmsdata[2]), Status.ACTIVE)).thenReturn(rmsTable);

		Mockito.when(userRepository.save(userRMSNew)).thenReturn(userRMSNew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userFD.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userFD.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("RMS User", responseUserVo.getRole());
		assertEquals("ChangedFDUserToRMSuser", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2013-10-21").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("5", responseUserVo.getRmsId()); // "noida,GPO,1212" this address has 5 rmsId

	}

	@Test
	public void PositiveUserTestCaseForUpdateFD_To_AdminUser() throws ParseException {
		User userAdminNew = new User();
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userFD.getEmail());
		createUserVo.setPostalCode("0");
		createUserVo.setName("ChangedFDUserToAdmin");
		createUserVo.setRole("Admin");
		createUserVo.setDateOfBirth("2015-12-23");

		control1.setId(14L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userFD);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleAdmin);

		Mockito.when(userRepository.save(userAdminNew)).thenReturn(userAdminNew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userFD.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userFD.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Admin", responseUserVo.getRole());
		assertEquals("ChangedFDUserToAdmin", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2015-12-23").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("0", responseUserVo.getPostalCode());
	}

	@Test
	public void PositiveUserTestCaseForUpdateRMS_To_PO_USER() throws ParseException {

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userRMS.getEmail());
		createUserVo.setPostalCode("4343");
		createUserVo.setName("ChangedRMSUserToPOSuser");
		createUserVo.setRole("Post Office User");
		createUserVo.setDateOfBirth("2011-11-11");
		User userPONew = new User();

		control1.setId(15L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userRMS);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(rolePO);

		Mockito.when(userRepository.save(userPONew)).thenReturn(userPONew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userRMS.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userRMS.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Post Office User", responseUserVo.getRole());
		assertEquals("ChangedRMSUserToPOSuser", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2011-11-11").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("4343", responseUserVo.getPostalCode());
	}

	@Test
	public void PositiveUserTestCaseForUpdateRMS_To_FD_USER() throws ParseException {
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userRMS.getEmail());
		createUserVo.setPostalCode("2121");
		createUserVo.setName("ChangedRMSUserToFDuser1");
		createUserVo.setRole("Front Desk User");
		createUserVo.setDateOfBirth("2012-07-18");
		User userFDNew = new User();

		control1.setId(16L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userRMS);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleFD);

		Mockito.when(userRepository.save(userFDNew)).thenReturn(userFDNew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userRMS.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userRMS.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Front Desk User", responseUserVo.getRole());
		assertEquals("ChangedRMSUserToFDuser1", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2012-07-18").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("2121", responseUserVo.getPostalCode());
	}

	@Test
	public void PositiveUserTestCaseForUpdateRMS_To_Admin_USER() throws ParseException {

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userRMS.getEmail());
		createUserVo.setPostalCode("0");
		createUserVo.setName("ChangedRMSUserToAdminUser");
		createUserVo.setRole("Admin");
		createUserVo.setDateOfBirth("2012-12-12");

		control1.setId(17L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userRMS);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleAdmin);

		Mockito.when(userRepository.save(userAdmin)).thenReturn(userAdmin);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userRMS.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userRMS.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Admin", responseUserVo.getRole());
		assertEquals("ChangedRMSUserToAdminUser", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-12").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("0", responseUserVo.getPostalCode());
	}

	@Test
	public void PositiveUserTestCaseForUpdateAdmin_To_POUser() throws ParseException {

		User userPONew = new User();

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userAdmin.getEmail());
		createUserVo.setPostalCode("3333");
		createUserVo.setName("ChangedAdminUserToPOuser");
		createUserVo.setRole("Post Office User");
		createUserVo.setDateOfBirth("2011-11-11");

		control1.setId(18L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userAdmin);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(rolePO);

		Mockito.when(userRepository.save(userPONew)).thenReturn(userPONew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userAdmin.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userAdmin.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Post Office User", responseUserVo.getRole());
		assertEquals("ChangedAdminUserToPOuser", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2011-11-11").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("3333", responseUserVo.getPostalCode());

	}

	@Test
	public void PositiveUserTestCaseForUpdateAdmin_To_FDUser() throws ParseException {

		User userFDNew = new User();

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userAdmin.getEmail());
		createUserVo.setPostalCode("5353");
		createUserVo.setName("ChangedAdminUserToFDuserA");
		createUserVo.setRole("Front Desk User");
		createUserVo.setDateOfBirth("2001-03-13");

		control1.setId(19L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userAdmin);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleFD);

		Mockito.when(userRepository.save(userFDNew)).thenReturn(userFDNew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userAdmin.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userAdmin.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Front Desk User", responseUserVo.getRole());
		assertEquals("ChangedAdminUserToFDuserA", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2001-03-13").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("5353", responseUserVo.getPostalCode());
	}

	@Test
	public void PositiveUserTestCaseForUpdateAdmin_To_RMSUser() throws ParseException {
		User userRMSNew = new User();

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userFD.getEmail());
		createUserVo.setRmsId("noida,GPO,1212");
		String[] rmsdata = createUserVo.getRmsId().split(",");
		createUserVo.setName("ChangedAdminUserToRMSuser");
		createUserVo.setRole("RMS User");
		createUserVo.setDateOfBirth("2003-03-03");

		control1.setId(20L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userAdmin);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleRMS);

		Mockito.when(rmsRepository.findByRmsNameAndRmsTypeAndRmsAddress_PostalCodeAndStatus(rmsdata[0], rmsdata[1],
				Integer.parseInt(rmsdata[2]), Status.ACTIVE)).thenReturn(rmsTable);

		Mockito.when(userRepository.save(userRMSNew)).thenReturn(userRMSNew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userAdmin.getEmail(), responseUserVo.getEmail());
		assertNotEquals(userAdmin.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("RMS User", responseUserVo.getRole());
		assertEquals("ChangedAdminUserToRMSuser", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2003-03-03").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals("5", responseUserVo.getRmsId());

	}

	// **Test Case when Update Post Office User through Name Or DOB with same Role
	@Test
	public void PositiveUserTestCaseForUpdatePoUserToPOUserNoRoleChange() throws ParseException {
		User userPONew = new User();
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userPO.getEmail());
		createUserVo.setPostalCode(userPO.getPostalCode() + "");
		createUserVo.setName("newPOuserABC");// name change
		createUserVo.setRole("Post Office User");
		createUserVo.setDateOfBirth("2013-10-21"); // date change

		control1.setId(21L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userPO);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(rolePO);

		Mockito.when(userRepository.save(userPONew)).thenReturn(userPONew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userPO.getEmail(), responseUserVo.getEmail());
		assertEquals(userPO.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Post Office User", responseUserVo.getRole());
		assertEquals("newPOuserABC", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2013-10-21").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals(userPO.getPostalCode() + "", responseUserVo.getPostalCode());

	}

	// **Test Case when Update Front Desk User through Name Or DOB with same Role
	@Test
	public void PositiveUserTestCaseForUpdateFD_UserToFD_UserNoRoleChange() throws ParseException {

		User userFDNew = new User();
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userFD.getEmail());
		createUserVo.setPostalCode(userFD.getPostalCode() + "");
		createUserVo.setName("newFDuserXYZ");// name change
		createUserVo.setRole("Front Desk User");
		createUserVo.setDateOfBirth("2013-10-21"); // date change

		control1.setId(22L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userFD);

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleFD);

		Mockito.when(userRepository.save(userFDNew)).thenReturn(userFDNew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userFD.getEmail(), responseUserVo.getEmail());
		assertEquals(userFD.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Front Desk User", responseUserVo.getRole());
		assertEquals("newFDuserXYZ", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2013-10-21").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals(userFD.getPostalCode() + "", responseUserVo.getPostalCode());

	}

	// **Test Case when Update RMS User through Name Or DOB with same Role.
	@Test
	public void PositiveUserTestCaseForUpdateRMS_UserToRMS_UserNoRoleChange() throws ParseException {

		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userFD.getEmail());
		createUserVo.setRmsId("noida,GPO,1212");
		String[] rmsdata = createUserVo.getRmsId().split(",");
		createUserVo.setName("ChangedRMSnewABC");
		createUserVo.setRole("RMS User");
		createUserVo.setDateOfBirth("2001-01-01");

		User userRMSNew = new User();

		control1.setId(23L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userRMS);

		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleRMS);

		Mockito.when(rmsRepository.findByRmsNameAndRmsTypeAndRmsAddress_PostalCodeAndStatus(rmsdata[0], rmsdata[1],
				Integer.parseInt(rmsdata[2]), Status.ACTIVE)).thenReturn(rmsTable);

		Mockito.when(userRepository.save(userRMSNew)).thenReturn(userRMSNew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userRMS.getEmail(), responseUserVo.getEmail());
		assertEquals(userRMS.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("RMS User", responseUserVo.getRole());
		assertEquals("ChangedRMSnewABC", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals(userRMS.getRmsId() + "", responseUserVo.getRmsId());

	}

	// **Test Case when Update Admin User through Name Or DOB with same Role.
	@Test
	public void PositiveUserTestCaseForUpdateAdmin_To_Admin() throws ParseException {
		User userAdminNew = new User();
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userAdmin.getEmail());
		createUserVo.setPostalCode("0");
		createUserVo.setName("ChangeAdminB2");
		createUserVo.setRole("Admin");
		createUserVo.setDateOfBirth("2012-12-12");

		control1.setId(24L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userAdmin);

		Mockito.when(userRepository.save(userAdminNew)).thenReturn(userAdminNew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		CreateUserVo responseUserVo = registrationServiceimp.updateUser(createUserVo, loggedUser);
		assertEquals(createUserVo, responseUserVo);
		assertEquals(userAdmin.getEmail(), responseUserVo.getEmail());
		assertEquals(userAdmin.getRole().getDisplayName(), responseUserVo.getRole());
		assertEquals("Admin", responseUserVo.getRole());
		assertEquals("ChangeAdminB2", responseUserVo.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-12").toString(),
				responseUserVo.getDateOfBirth());
		assertEquals(userAdmin.getPostalCode() + "", responseUserVo.getPostalCode());
	}

	/// **Register User Method Email Already Exist**///
	@Test
	public void registerUserTestFrootDeskUserEmailAlreadyExist() {

		CreateUserVo createUserVo = new CreateUserVo();

		createUserVo.setEmail(userFD.getEmail());
		createUserVo.setPostalCode("1000");
		createUserVo.setName("Prabht");
		createUserVo.setRole("Front Desk User");
		createUserVo.setDateOfBirth("2011-11-11");

		control1.setId(25L);
		controlList.add(control1);

		User userFDNew = new User();

		Mockito.when(roleRepository.findOneByName("ROLE_FRONT_DESK_USER")).thenReturn(roleFD);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(userFD);

		Mockito.when(userRepository.save(userFDNew)).thenReturn(userFDNew);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		Assert.assertEquals(message.getEmailAlreadyExits(),
				registrationServiceimp.registerUser(createUserVo, loggedUser));

	}

	/// ***Negative Test Case For Update PoUser Failure Condition
	@Test
	public void NegativeUserTestCaseForUpdatePoUserToRMS_UserFailureCondition() {
		User userRMS = new User();
		CreateUserVo createUserVo = new CreateUserVo();
		createUserVo.setEmail(userFD.getEmail());
		createUserVo.setRmsId("noida,GPO,1212");
		String[] rmsdata = createUserVo.getRmsId().split(",");
		createUserVo.setName("ChangedFDUserABC2ToRMSuser");
		createUserVo.setRole("RMS User");
		createUserVo.setDateOfBirth("2013-10-21");

		control1.setId(28L);
		controlList.add(control1);

		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus(createUserVo.getEmail(), Status.ACTIVE))
				.thenReturn(null);

		Mockito.when(roleRepository.findOneByName("ROLE_PO_USER")).thenReturn(rolePO);
		Mockito.when(roleRepository.findOneByName("ROLE_RMS_USER")).thenReturn(roleRMS);

		Mockito.when(roleRepository.findOneByDisplayName(createUserVo.getRole())).thenReturn(roleRMS);

		Mockito.when(rmsRepository.findByRmsNameAndRmsTypeAndRmsAddress_PostalCodeAndStatus(rmsdata[0], rmsdata[1],
				Integer.parseInt(rmsdata[2]), Status.ACTIVE)).thenReturn(rmsTable);

		Mockito.when(userRepository.save(userRMS)).thenReturn(userRMS);
		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Mockito.when(controlRepository.save(control1)).thenReturn(control1);

		assertEquals(null, registrationServiceimp.updateUser(createUserVo, loggedUser));
	}

	@Test
	public void UserEnableAndDisableTestCaseForIfPartWhenUserIsNull() {
		User currentUser = new User();
		currentUser.setEmail("olduser@gmail.com");
		currentUser.setEnabled(false);
		currentUser.setStatus(Status.ACTIVE);
		control1.setId(6L);
		controlList.add(control1);
		Mockito.when(
				userRepository.findUserByEmailContainingIgnoreCaseAndStatus("currentuser@gmail.com", Status.ACTIVE))
				.thenReturn(null);
		assertEquals(null, registrationServiceimp.enableAndDisableUserService("currentuser@gmail.com", loggedUser));

	}

}
