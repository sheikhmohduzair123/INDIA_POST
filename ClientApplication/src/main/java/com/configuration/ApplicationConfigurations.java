package com.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.constants.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import com.domain.Config;
import com.repositories.ConfigRepository;

@Component("applicationConfigurations")
public class ApplicationConfigurations implements EnvironmentAware {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigurations.class);

    @Autowired
    private ConfigRepository configRepository;

	@Override
	public void setEnvironment(Environment environment) {
		ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
		//Config getSchedulerTimePatter = configRepository.findTopByConfigTypeOrderByUpdatedOnDesc("schedulertimepattern");
		 Map<String, Object> propertySource = new HashMap<>();
		 List<String> configTypes = new ArrayList<>();
		 configTypes.add("schedulertimepattern");
		 configTypes.add("cleanupdurationinmonth");
		 List<Config> configApp = configRepository.findByConfigTypeInAndStatus(configTypes, Status.ACTIVE);
		 if(configApp.size()>0) {
			 
			 logger.info("Getting config from database");
			 for(Config config : configApp) {
				 propertySource.put(config.getConfigType(), config.getConfigValue());
			 }
			 
		 }else {
			 logger.info("Getting config from default");
			 propertySource.put("cleanupdurationinmonth", "6");
			 propertySource.put("schedulertimepattern", "0 0 22 * * ?");
		 }
		// configRepository.findAll().stream().forEach(config -> propertySource.put(config.getConfigType(), config.getConfigValue()));
 	
		configurableEnvironment.getPropertySources().addLast(new MapPropertySource("app-config", propertySource));
	}
}