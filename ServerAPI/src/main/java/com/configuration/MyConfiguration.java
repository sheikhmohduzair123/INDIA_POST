package com.configuration;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class MyConfiguration {

	protected static Logger log = LoggerFactory.getLogger(MyConfiguration.class);

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;

	@Value("${spring.datasource.url}")
	private String datasourceURL;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	/**
	 * Registering the DataSource
	 *
	 * @return DataSource Object
	 */

	@Bean
	public DataSource dataSource1() {
		log.info("setting up datasource");
		org.springframework.jdbc.datasource.DriverManagerDataSource ds = new org.springframework.jdbc.datasource.DriverManagerDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setUrl(datasourceURL);
		ds.setUsername(username);
		ds.setPassword(password);
	//	log.info("Url: " + ds.getUrl()+", UserName: " + ds.getUsername()+ ", Password: " + ds.getPassword());
		return ds;
	}
}
