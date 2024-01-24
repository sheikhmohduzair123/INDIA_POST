package com.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constants.Status;
import com.domain.User;
import com.repositories.SUserRepository;
import com.services.SUserService;
import com.util.UpdatePasswordError;
import com.vo.UserVo;

@Service
public class SUserServiceImpl implements SUserService {

	protected Logger log = LoggerFactory.getLogger(SUserServiceImpl.class);

	@Autowired
	private SUserRepository userRepository;

	@Autowired
	UpdatePasswordError updatePasswordError;

	@Override
	public UserVo findUserStatus(String email) {
		User user = userRepository.findUserByEmailAndStatus(email, Status.ACTIVE);
		UserVo userVo = new UserVo();
		userVo.setEmail(user.getEmail());
		userVo.setAccountExpired(user.isAccountExpired());
		userVo.setAccountLocked(user.isAccountLocked());
		userVo.setCredentialsExpired(user.isCredentialsExpired());
		userVo.setDob(user.getDob());
		userVo.setEnabled(user.isEnabled());
		userVo.setFirstLogin(user.isFirstLogin());
		userVo.setId(user.getId());
		userVo.setIdentificationId(user.getIdentificationId());
		userVo.setLastLogin(user.getLastLogin());
		userVo.setName(user.getName());
		userVo.setPassword(user.getPassword());
		userVo.setPostalCode(user.getPostalCode());
		userVo.setRmsId(user.getRmsId());
		userVo.setRole(user.getRole());
		userVo.setUsername(user.getUsername());
		userVo.setStatus(user.getStatus());
		userVo.setAddressLine1(user.getAddressLine1());
		userVo.setAddressLine2(user.getAddressLine2());
		userVo.setMobileNumber(user.getMobileNumber());
			

		return userVo;
	}

	@Override
	public UserVo saveChangePassword(UserVo user) {
		 log.debug("changing password in server db");

		 User u=userRepository.findUserByEmailAndStatus(user.getEmail(), Status.ACTIVE);

	     u.setPassword(user.getPassword());
		 u.setFirstLogin(false);
		 userRepository.save(u);
		 //sending back userVo
		 return user;

	}

}

