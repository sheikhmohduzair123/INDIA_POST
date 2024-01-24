package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.Application;
import com.constants.Status;
import com.domain.PasswordHistory;
import com.domain.Role;
import com.domain.User;
import com.repositories.SUserPasswordRepository;
import com.repositories.SUserRepository;
import com.services.ParcelService;
import com.services.impl.UserDetailsServiceImpl;
import com.util.ServerTokenUtils;
import com.util.UpdatePassword;
import com.util.UpdatePasswordError;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class UpdatePasswordServiceTest {

	@InjectMocks
	private UserDetailsServiceImpl userDetails;

	@Mock
	private SUserRepository repository;

	@Mock
	private SUserPasswordRepository userPasswordRepository;

	@Mock
	private User user;

	@Mock
	ServerTokenUtils tokenUtils;
	
	@Mock
	private ParcelService parcelService;
	
	@Mock
	private UpdatePasswordError passwordError;

	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

	@Before
	public void init() {
		Mockito.when(repository.findUserByUsernameAndStatus("Dummy", Status.ACTIVE)).thenReturn(user);
		Mockito.when(user.getId()).thenReturn(1);
		Role roleAdmin = new Role();
		roleAdmin.setDisplayName("Admin");
		user.setEmail("adminuser11@gmail.com");
		user.setAccountExpired(false);
		user.setAccountLocked(false);
		user.setDob(new Date());
		user.setEnabled(true);
		user.setFirstLogin(true);
		user.setName("AdminUser123");
		
		user.setRole(roleAdmin);
		user.setPostalCode(0);
		user.setRmsId(null);
		
		user.setStatus(Status.ACTIVE);
		

	}

	@Test
	public void UserUpdatePasswordTest() throws Exception {

		String newPassword = "NewPassword@1";
		String oldPassword = "OldPassword@2";

		ArrayList<PasswordHistory> passwordlist = new ArrayList<PasswordHistory>();
		passwordlist.add(new PasswordHistory(user, bc.encode(oldPassword)));
		// UdpatePassword Class
		UpdatePassword updatePassword = new UpdatePassword();
		updatePassword.setNewPassword(newPassword);
		updatePassword.setOldPassword(oldPassword);
		Mockito.when(userPasswordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordlist);
		Mockito.when(user.getPassword()).thenReturn(bc.encode(oldPassword));
		// Then Return Status : Success
		assertEquals("{\"Status\": \"Success\"}", userDetails.userPasswordUpdate("Dummy", updatePassword, null));
	}

	@Test
	public void verifyNewPasswordNotEqualsOldPassword() throws Exception {

		// When both New and Old Password are same
		String newPassword = "111111@bdPost";
		String oldPassword = "111111@bdPost";

		ArrayList<PasswordHistory> passwordlist = new ArrayList<PasswordHistory>();
		passwordlist.add(new PasswordHistory(user, bc.encode(oldPassword)));

		// UdpatePassword Class
		UpdatePassword updatePassword = new UpdatePassword();
		updatePassword.setNewPassword(newPassword);
		updatePassword.setOldPassword(oldPassword);
		Mockito.when(userPasswordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordlist);
		Mockito.when(user.getPassword()).thenReturn(bc.encode(oldPassword));
		Mockito.when(passwordError.getCannotbeSameAsold()).thenReturn("New Password can not be same as Old Password!");

		// Then Return Failure Status "Password cannot be Same"
		assertEquals("{\"Status\": \"Failure\", \"Error\": \"" + passwordError.getCannotbeSameAsold() + "\"}",
				userDetails.userPasswordUpdate("Dummy", updatePassword, null));

	}

	@Test
	public void oldPasswordMatches() throws Exception {
		// User Password from field
		String newPassword = "NewPassword@bd1";
		String oldPassword = "OldPassword@bd1";

		ArrayList<PasswordHistory> passwordlist = new ArrayList<PasswordHistory>();
		passwordlist.add(new PasswordHistory(user, bc.encode(oldPassword)));

		// UdpatePassword Class
		UpdatePassword updatePassword = new UpdatePassword();

		// Setting User
		updatePassword.setNewPassword(newPassword);

		// Entering Wrong old Password
		updatePassword.setOldPassword("WrongOldPassword");
		Mockito.when(userPasswordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordlist);
		Mockito.when(user.getPassword()).thenReturn(bc.encode(oldPassword));
		Mockito.when(passwordError.getIncorrectPassword())
				.thenReturn("Incorrect Password. Please Try Again!");

		// Then Returns Failure Status "Your New Password does not matches Confirm
		// Password"
		assertEquals("{\"Status\": \"Failure\", \"Error\": \"" + passwordError.getIncorrectPassword() + "\"}",
				userDetails.userPasswordUpdate("Dummy", updatePassword, null));

	}

	@Test
	public void passwordNotMatchLast5() throws Exception {
	
		String newPassword = "newPassword@1";
		String oldPassword = "oldPassword@e2";

		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		ArrayList<PasswordHistory> passwordlist = new ArrayList<PasswordHistory>();
		passwordlist.add(new PasswordHistory(user, bc.encode("Password@1")));
		passwordlist.add(new PasswordHistory(user, bc.encode("Password@2")));
		// Setting Password history same as new Password
		passwordlist.add(new PasswordHistory(user, bc.encode("newPassword@1")));
		passwordlist.add(new PasswordHistory(user, bc.encode("Password@3")));
		passwordlist.add(new PasswordHistory(user, bc.encode("Password@4")));

		// UdpatePassword Class
		UpdatePassword updatePassword = new UpdatePassword();
		updatePassword.setNewPassword(newPassword);
		updatePassword.setOldPassword(oldPassword);
		Mockito.when(userPasswordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordlist);
		Mockito.when(user.getPassword()).thenReturn(bc.encode(oldPassword));
		Mockito.when(passwordError.getCannotusepreviouslyused())
				.thenReturn("New Password cannot be same as Previously used Passwords. Please Try Again!");

		// Then Return Failure Status "New Password cannot be same as Previous
		// Passwords"
		assertEquals("{\"Status\": \"Failure\", \"Error\": \"" + passwordError.getCannotusepreviouslyused() + "\"}",
				userDetails.userPasswordUpdate("Dummy", updatePassword, null));

	}

	@Test
	public void testIsPreviouslyUsed() throws Exception {
		ArrayList<PasswordHistory> passwordlist = new ArrayList<PasswordHistory>();
		passwordlist.add(new PasswordHistory(user, bc.encode("password1")));
		passwordlist.add(new PasswordHistory(user, bc.encode("password2")));
		// Setting Password history same as new Password
		passwordlist.add(new PasswordHistory(user, bc.encode("password3")));
		passwordlist.add(new PasswordHistory(user, bc.encode("password4")));
		passwordlist.add(new PasswordHistory(user, bc.encode("password5")));
		Mockito.when(userPasswordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordlist);
		boolean value = Whitebox.invokeMethod(userDetails, "isPreviouslyUsed", user, "password3");
		assertTrue(value);
	}




}
