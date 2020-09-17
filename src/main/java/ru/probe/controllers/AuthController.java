package ru.probe.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import ru.probe.config.AuthConfig;
import ru.probe.dbmodels.User;
import ru.probe.repos.UserRepo;
import ru.probe.utils.MessageModel;

/**
 * handling auth process
 * @author maksim
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	@Qualifier("authConfigBean")
	private AuthConfig authConfig;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepo userRepo;
	
	/**
	 * use it for get JWT token
	 * @param user
	 * @return String token for registered user
	 */
	@PostMapping(
			value = "/sign-up"
			, produces = "application/json"
		)
	public MessageModel<Map<String, String>> signUp(
			MessageModel<Map<String, String>> mm,
			@RequestBody User user
		) {
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							user.getUsername(),
							user.getPassword()
					)
				);
			
			user = userRepo.findByUsername(user.getUsername());
			
		} catch(AuthenticationException e) {
			return mm.sendError(e.toString());
		} catch(Exception e) {
			return mm.sendError(e.toString());
		}
		
		String token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + authConfig.getEXPIRATION_TIME()))
				.sign(Algorithm.HMAC512(authConfig.getSECRET_KEY()));
		
		
		return mm.sendData(new HashMap<String, String>() {/**
			 * 
			 */
			private static final long serialVersionUID = 5987129296970678423L;

		{
			put(authConfig.getKEY_NAME_TOKEN(), token);
		}});
	}
}
