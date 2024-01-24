package com.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class UpdatePasswordError {

	@Value("${updatePassword.error.cannotbesamessold}")
	private String cannotbeSameAsold;

	@Value("${updatePassword.error.oldpassworddonotmatch}")
	private String incorrectPassword;

	@Value("${updatePassword.error.cannotusepreviouslyused}")
	private String cannotusepreviouslyused;

	@Value("${updatePassword.error.mailsent}")
	private String mailsent;

	@Value("${user.error.resentmail}")
	private String resentmail;

	@Value("${updatePassword.error.linkbroken}")
	private String linkbroken;

	@Value("${updatePassword.error.wrongemail}")
	private String wrongemail;

	@Value("${updatePassword.error.cannotchangepassword}")
	private String cannotchangepassword;

	@Value("${updatePassword.error.passwordresetsuccessful}")
	private String passwordresetsuccessful;

	@Value("${email.already.exsits}")
	private String emailAlreadyExits;

	@Value("${some.error.occoured}")
	private String somethingGotWrong;

	@Value("${user.already.active}")
	private String userAlreadyActive;

	@Value("${password.validation.failure}")
	private String passwordValidationFailure;

	@Value("${forgot.password.mail}")
	private String forgotPasswordMail;

	@Value("${email.not.empty}")
	private String emptyEmail;

	@Value("${is.old.user}")
	private String isOldUser;

	public String getCannotbeSameAsold() {
		return cannotbeSameAsold;
	}

	public void setCannotbeSameAsold(String cannotbeSameAsold) {
		this.cannotbeSameAsold = cannotbeSameAsold;
	}

	public String getCannotusepreviouslyused() {
		return cannotusepreviouslyused;
	}

	public void setCannotusepreviouslyused(String cannotusepreviouslyused) {
		this.cannotusepreviouslyused = cannotusepreviouslyused;
	}

	public String getMailsent() {
		return mailsent;
	}

	public void setMailsent(String mailsent) {
		this.mailsent = mailsent;
	}

	public String getLinkbroken() {
		return linkbroken;
	}

	public void setLinkbroken(String linkbroken) {
		this.linkbroken = linkbroken;
	}

	public String getWrongemail() {
		return wrongemail;
	}

	public void setWrongemail(String wrongemail) {
		this.wrongemail = wrongemail;
	}

	public String getCannotchangepassword() {
		return cannotchangepassword;
	}

	public void setCannotchangepassword(String cannotchangepassword) {
		this.cannotchangepassword = cannotchangepassword;
	}

	public String getPasswordresetsuccessful() {
		return passwordresetsuccessful;
	}

	public void setPasswordresetsuccessful(String passwordresetsuccessful) {
		this.passwordresetsuccessful = passwordresetsuccessful;
	}

    public String getEmailAlreadyExits() {
        return emailAlreadyExits;
    }

    public void setEmailAlreadyExits(String emailAlreadyExits) {
        this.emailAlreadyExits = emailAlreadyExits;
    }

    public String getSomethingGotWrong() {
        return somethingGotWrong;
    }

    public void setSomethingGotWrong(String somethingGotWrong) {
        this.somethingGotWrong = somethingGotWrong;
	}

	public String getUserAlreadyActive() {
        return userAlreadyActive;
    }


    public void setUserAlreadyActive(String userAlreadyActive) {
        this.userAlreadyActive = userAlreadyActive;
    }

    public String getResentmail() {
		return resentmail;
	}

    public void setResentmail(String resentmail) {
		this.resentmail = resentmail;
	}

	public String getPasswordValidationFailure() {
		return passwordValidationFailure;
	}

	public void setPasswordValidationFailure(String passwordValidationFailure) {
		this.passwordValidationFailure = passwordValidationFailure;
	}

	public String getForgotPasswordMail() {
		return forgotPasswordMail;
	}

	public void setForgotPasswordMail(String forgotPasswordMail) {
		this.forgotPasswordMail = forgotPasswordMail;
	}

	public String getEmptyEmail() {
		return emptyEmail;
	}

	public void setEmptyEmail(String emptyEmail) {
		this.emptyEmail = emptyEmail;
	}

	public String getIsOldUser() {
		return isOldUser;
	}

	public void setIsOldUser(String isOldUser) {
		this.isOldUser = isOldUser;
	}
	public String getIncorrectPassword() {
		return incorrectPassword;
	}

	public void setIncorrectPassword(String incorrectPassword) {
		this.incorrectPassword = incorrectPassword;
	}


}
