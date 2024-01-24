package com.services;

import java.util.List;

import com.domain.RmsTable;
import com.domain.User;
import com.vo.CreateUserVo;
import com.vo.UserVo;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserRegistrationService {

	String registerUser(CreateUserVo userVo,User loginedUser);

	String activateUser(String registrationCode) throws Exception;

	//String activateUser(String registrationCode, String cookieToken) throws Exception;
    List<RmsTable> findAllRms();

    UserVo updateUserStatus(String email,User loginedUser);

	String resendMailUserService(String email,User loginedUser);

	UserVo enableAndDisableUserService(String email,User loginedUser);

///	List<User> findAll(User loginedUser);

	//List<User> findAllByStatus(Status valueOf,User loginedUser);

	CreateUserVo updateUser(CreateUserVo userVo, User loginedUser);

  ///	List<User> findAllByEnableAccoutTrue(User loginedUser);

  ///	List<User> findAllByEnableAccoutFalse(User loginedUser);

	List<UserVo> findUserListByStatus(User loginedUser, String status);

	//List<User> findAllByStatusActive(User loginedUser);

	//List<User> findAllByStatusDeactive(User loginedUser);




}
