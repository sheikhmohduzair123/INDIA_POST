package com.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class UpdatePasswordError {

	@Value("${updatePassword.error.cannotbesamessold}")
	private String cannotbeSameAsold;
	
	@Value("${updatePassword.error.donotmatch}")
	private String donotmatch;

	@Value("${updatePassword.error.cannotusepreviouslyused}")
	private String cannotusepreviouslyused;
	
	@Value("${updatePassword.error.mailsent}")
	private String mailsent;
	
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
	
	public String getCannotbeSameAsold() {
		return cannotbeSameAsold;
	}

	public void setCannotbeSameAsold(String cannotbeSameAsold) {
		this.cannotbeSameAsold = cannotbeSameAsold;
	}

	public String getDonotmatch() {
		return donotmatch;
	}

	public void setDonotmatch(String donotmatch) {
		this.donotmatch = donotmatch;
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

}
