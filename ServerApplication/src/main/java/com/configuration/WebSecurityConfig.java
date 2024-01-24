package com.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.services.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	protected static Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
	/**
	 * Spring Security Configuration
	 */
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	@Qualifier("dataSource1")
	private DataSource dataSource1;

	/**
	 *
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	/**
	 *
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Setting Service to find User in the database.
		// And Setting PassswordEncoder
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.eraseCredentials(false);

	}

	/**
	 *
	 * @return JdbcTokenRepositoryImpl
	 */
	@Bean
	public JdbcTokenRepositoryImpl tokenRepository() {
		JdbcTokenRepositoryImpl j = new JdbcTokenRepositoryImpl();
		j.setDataSource(dataSource1);
		log.info("DataSource: " + j.getDataSource());
		return j;
	}

	/**
	 *
	 * @return LoginSuccessHandler Object
	 */

	/*
	 * @Bean public LoginSuccessHandler loginSuccessHandler() { return new
	 * LoginSuccessHandler();// code6 }
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		// The pages does not require login
		http.authorizeRequests().antMatchers("/","/health", "/login", "/logoutSuccessful", "/user/activateUser",
				"/pbTracking/trackParcel", "/pbTracking/getDetailsWithParcelId").permitAll();

		// /userInfo page requires login as ROLE_USER or ROLE_ADMIN.
		// If no login, it will redirect to /login page.
		http.authorizeRequests().antMatchers("/userInfo").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

		// For ADMIN only.
		// http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

		// For ADMIN only.
		http.authorizeRequests().antMatchers("/po/*").access("hasAnyRole('ROLE_PO_USER')");
		http.authorizeRequests().antMatchers("/rms/*").access("hasRole('ROLE_RMS_USER')");
		http.authorizeRequests().antMatchers("/tracking/*").access("hasAnyRole('ROLE_PO_USER','ROLE_RMS_USER')");
		http.authorizeRequests().antMatchers("/server/*").access("hasRole('ROLE_ADMIN')");
		http.authorizeRequests().antMatchers("/pbTracking/*")
				.access("hasAnyRole('ROLE_ADMIN','ROLE_PO_USER','ROLE_RMS_USER')");
		http.authorizeRequests().antMatchers("/masterController/*").access("hasRole('ROLE_ADMIN')");

		// When the user has logged in as XX.
		// But access a page that requires role YY,
		// AccessDeniedException will be thrown.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Config for Login Form
		http.authorizeRequests().and().formLogin()
				// Submit URL of login page.
				.loginProcessingUrl("/j_spring_security_check") // Submit URL
				.loginPage("/login")//
				.defaultSuccessUrl("/login")//
				.failureUrl("/login?error=true")//
				.usernameParameter("username")//
				.passwordParameter("password")
				// Config for Logout Page
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logoutSuccessful"))
				.invalidateHttpSession(true).logoutSuccessUrl("/login");

		http.sessionManagement().maximumSessions(1).expiredUrl("/logoutSuccessful");

		http.headers()
	       .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self' 'unsafe-inline' 'unsafe-eval'; font-src 'self'; default-src 'self' 'unsafe-inline'; connect-src 'self'; img-src 'self'; style-src 'self' 'unsafe-inline'; object-src 'self' https:; media-src 'self' https:; data: 'unsafe-inline'; "));


		//to disable insecure methods in http header
		http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").denyAll();
		http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/**").denyAll();
		http.authorizeRequests().antMatchers(HttpMethod.HEAD, "/**").denyAll();
		http.authorizeRequests().antMatchers(HttpMethod.PATCH, "/**").denyAll();
		http.authorizeRequests().antMatchers(HttpMethod.PUT, "/**").denyAll();
		http.authorizeRequests().antMatchers(HttpMethod.TRACE, "/**").denyAll();

	}

}
