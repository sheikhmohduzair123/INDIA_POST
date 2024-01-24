package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.constants.Status;
import com.domain.PasswordHistory;
import com.domain.User;
import com.repositories.SUserPasswordRepository;
import com.repositories.SUserRepository;
import com.services.impl.UserDetailsServiceImpl;
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
	private UpdatePasswordError passwordError;

	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();

	@Before
	public void init() {
		Mockito.when(repository.findUserByUsernameAndStatus("Dummy", Status.ACTIVE)).thenReturn(user);
		Mockito.when(user.getId()).thenReturn(1);

	}

	@Test
	public void UserUpdatePasswordTest() {

		String newPassword = "NewPassword@123";
		String oldPassword = "OldPassword@123";

		ArrayList<PasswordHistory> passwordlist = new ArrayList<PasswordHistory>();
		passwordlist.add(new PasswordHistory(user, bc.encode(oldPassword)));
		// UdpatePassword Class
		UpdatePassword updatePassword = new UpdatePassword();
		updatePassword.setNewPassword(newPassword);
		updatePassword.setOldPassword(oldPassword);
		Mockito.when(userPasswordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordlist);
		Mockito.when(user.getPassword()).thenReturn(bc.encode(oldPassword));
		// Then Return Status : Success
		assertEquals("{\"Status\": \"Success\"}", userDetails.userPasswordUpdate("Dummy", updatePassword));
	}
	
	@Test
	public void verifyNewPasswordNotEqualsOldPassword() {

		//User user=new User();
		
		// When both New and Old Password are same
		String newPassword = "1@Aa11";
		String oldPassword = "1@Aa11";

		ArrayList<PasswordHistory> passwordlist = new ArrayList<PasswordHistory>();
		passwordlist.add(new PasswordHistory(user, bc.encode(oldPassword)));

		// UdpatePassword Class
		UpdatePassword updatePassword = new UpdatePassword();
		updatePassword.setNewPassword(newPassword);
		updatePassword.setOldPassword(oldPassword);
	//	sUserRepository.findUserByUsernameAndStatus(loginedUser, Status.ACTIVE);
		Mockito.when(userPasswordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordlist);
		Mockito.when(userPasswordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordlist);
		Mockito.when(user.getPassword()).thenReturn(bc.encode(oldPassword));
		Mockito.when(passwordError.getCannotbeSameAsold()).thenReturn("New Password can not be same as Old Password!");

		// Then Return Failure Status "Password cannot be Same"
		assertEquals("{\"Status\": \"Failure\", \"Error\": \"" + passwordError.getCannotbeSameAsold() + "\"}",
				userDetails.userPasswordUpdate("Dummy", updatePassword));

	}

	@Test
	public void oldPasswordMatches() {
		// User Password from field
		String newPassword = "NewPassword@12";
		String oldPassword = "OldPassword@12";

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
				userDetails.userPasswordUpdate("Dummy", updatePassword));

	}
	
	@Test
	public void passwordNotMatchLast5() {

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
				userDetails.userPasswordUpdate("Dummy", updatePassword));

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
