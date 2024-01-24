package com.services;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.vo.UserVo;

public interface SUserService {

	UserVo findUserStatus(@NotEmpty(message = "{email.not.empty}") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z]+(\\.[A-Za-z]{2,4})$", message = "{email.pattern.wrong}") String email);
	UserVo saveChangePassword(UserVo user);
}


