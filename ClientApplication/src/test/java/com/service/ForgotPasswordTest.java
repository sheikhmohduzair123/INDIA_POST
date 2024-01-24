package com.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import com.services.SUserService;
import com.services.impl.UserDetailsServiceImpl;
import com.util.UpdatePasswordError;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ForgotPasswordTest {

	@InjectMocks
	SUserService suserService;

	@InjectMocks
	UserDetailsServiceImpl userDetailService;

	@Mock
	SUserRepository suserRepository;

	@Mock
	User user;

	@Mock
	SUserPasswordRepository passwordRepository;

	@Mock
	UpdatePasswordError updatePasswordError;

	@Before
	public void init() {
		Mockito.when(user.getId()).thenReturn(1);

	}

	@Test
	public void findByActivationCode() {
		String code = "1";
		User u = new User();
		u.setAccountExpired(false);
		u.setAccountLocked(false);
		u.setCredentialsExpired(false);
		u.setDob(new Date());
		u.setEmail("s@gmail.com");
		u.setEnabled(true);
		u.setId(11);
		u.setName("Abc");
		u.setUsername("Abc");
		u.setPassword("12345");
		u.setActivationCode(code);
		Mockito.when(suserRepository.findUserByActivationCodeAndStatus(code, Status.ACTIVE)).thenReturn(u);
		assertThat(suserService.findUserByActivationCode("1")).isEqualTo(u);
	}

	@Test
	public void resetPassword(HttpServletResponse res) throws Exception {

		String newPassword = "8888";
		String oldPassword = "999";
		String previousPassword = "previous";
		String activationCode = "111";
		Mockito.when(user.getActivationCode()).thenReturn(activationCode);
		Mockito.when(user.getPassword()).thenReturn(oldPassword);
		ArrayList<PasswordHistory> passwordList = new ArrayList<PasswordHistory>();
		passwordList.add(new PasswordHistory(user, previousPassword));
		Mockito.when(passwordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordList);
		Mockito.when(user.getPassword()).thenReturn(newPassword);
		Mockito.when(user.getActivationCode()).thenReturn(null);
		Mockito.when(suserRepository.save(user)).thenReturn(user);
		Mockito.when(updatePasswordError.getPasswordresetsuccessful()).thenReturn("Password Reset Successful.");
		assertEquals(updatePasswordError.getPasswordresetsuccessful(),
				userDetailService.resetPassword(user, newPassword, res));
	}

	@Test
	public void notSameAsOld(HttpServletResponse res) throws Exception {

		String oldPassword = "999";
		String newPassword = oldPassword;
		String previousPassword = "previous";
		String activationCode = "111";
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		Mockito.when(user.getActivationCode()).thenReturn(activationCode);
		Mockito.when(user.getPassword()).thenReturn(bc.encode(oldPassword));
		ArrayList<PasswordHistory> passwordList = new ArrayList<PasswordHistory>();
		passwordList.add(new PasswordHistory(user, previousPassword));
		Mockito.when(passwordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordList);
		Mockito.when(updatePasswordError.getCannotbeSameAsold())
				.thenReturn("New Password can not be same as Old Password!");
		assertEquals(updatePasswordError.getCannotbeSameAsold(), userDetailService.resetPassword(user, newPassword, res));
	}

	@Test
	public void notSameAsPrevious(HttpServletResponse res) throws Exception {

		String oldPassword = "999";
		String newPassword = "1111";
		String previousPassword = newPassword;
		String previousPassword1 = "previous1";
		String previousPassword2 = "previous2";
		String previousPassword3 = "previous3";
		String previousPassword4 = "previous4";
		String activationCode = "111";
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		Mockito.when(user.getActivationCode()).thenReturn(activationCode);
		Mockito.when(user.getPassword()).thenReturn(bc.encode(oldPassword));
		ArrayList<PasswordHistory> passwordList = new ArrayList<PasswordHistory>();
		passwordList.add(new PasswordHistory(user, bc.encode(previousPassword)));
		passwordList.add(new PasswordHistory(user, bc.encode(previousPassword1)));
		passwordList.add(new PasswordHistory(user, bc.encode(previousPassword2)));
		passwordList.add(new PasswordHistory(user, bc.encode(previousPassword3)));
		passwordList.add(new PasswordHistory(user, bc.encode(previousPassword4)));
		Mockito.when(passwordRepository.findTop5ByUserIdOrderByCreatedOn(1)).thenReturn(passwordList);
		Mockito.when(updatePasswordError.getCannotusepreviouslyused())
				.thenReturn("New Password cannot be same as Previously used Passwords. Please Try Again!");
		assertEquals(updatePasswordError.getCannotusepreviouslyused(),
				userDetailService.resetPassword(user, newPassword, res));
	}

}
