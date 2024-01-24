package com.services;

import com.constants.Status;
import com.domain.RmsTable;
import com.domain.User;
import com.vo.CreateUserVo;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserRegistrationService {

	String registerUser(CreateUserVo userVo,User loginedUser);

	String activateUser(String registrationCode) throws Exception;

	//String activateUser(String registrationCode, String cookieToken) throws Exception;
    List<RmsTable> findAllRms();

    void updateUserStatus(Integer id,User loginedUser);


	String resendMailUserService(Integer id,User loginedUser);


	void enableAndDisableUserService(Integer id,User loginedUser);

	List<User> findAll(User loginedUser);

	//List<User> findAllByStatus(Status valueOf,User loginedUser);

	void updateUser(CreateUserVo userVo, Integer uId,User loginedUser);

	List<User> findAllByEnableAccoutTrue(User loginedUser);

	List<User> findAllByEnableAccoutFalse(User loginedUser);

	//List<User> findAllByStatusActive(User loginedUser);

	//List<User> findAllByStatusDeactive(User loginedUser);




}
