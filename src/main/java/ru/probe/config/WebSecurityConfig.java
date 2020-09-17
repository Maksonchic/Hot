package ru.probe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.probe.config.filters.JWTAuthorizationFilter;
import ru.probe.repos.UserRepo;
import ru.probe.service.UserService;
import ru.probe.utils.Logger;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
	@Qualifier("authConfigBean")
	private AuthConfig authConfig;
    
    @Autowired
	private Logger logger;
    
    @Autowired
    private UserRepo userRepo;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.cors().and().csrf().disable().authorizeRequests()
        		.antMatchers(HttpMethod.POST, "/auth/sign-up").permitAll()
        		.antMatchers(HttpMethod.GET, "/info/general/**").permitAll()
	        	.anyRequest().authenticated()
        	.and()
                .rememberMe()
	        .and()
	        	.addFilter(new JWTAuthorizationFilter(
	        			authenticationManager,
	        			authConfig,
	        			userRepo,
	        			logger))
            	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    
    // switch off the filters for specific url
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/auth/sign-up");
    }
    
    @Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}