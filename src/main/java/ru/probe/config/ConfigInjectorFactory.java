package ru.probe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import ru.probe.utils.Logger;

@Configuration
@PropertySources({
	@PropertySource("classpath:/application-auth.properties")
})
public class ConfigInjectorFactory {
	
	@Autowired
	private Environment env;
	
	@Bean
	public AuthConfig authConfigBean() {
		
		String KEY_NAME_TOKEN = env.getProperty("KEY_NAME_TOKEN");
		String SECRET_KEY = env.getProperty("SECRET_KEY");
		String EXPIRATION_TIME_STR = env.getProperty("EXPIRATION_TIME");
		int EXPIRATION_TIME = 0;
		
		try {
			EXPIRATION_TIME = Integer.parseInt(EXPIRATION_TIME_STR);
		} catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		AuthConfig authConfigBean = new AuthConfig(
					KEY_NAME_TOKEN,
					SECRET_KEY,
					EXPIRATION_TIME
				);
		
		return authConfigBean;
	}
	
	@Bean
	public Logger loggerBean() {
		return new Logger();
	}
}
