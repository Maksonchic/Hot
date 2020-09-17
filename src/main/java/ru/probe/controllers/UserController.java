package ru.probe.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.probe.dbmodels.User;
import ru.probe.repos.UserRepo;
import ru.probe.utils.Logger;
import ru.probe.utils.MessageModel;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private Logger logger;
	
	private final String logName = "users";
	
	/**
	 * main request handler for the registration of new user
	 * @param user
	 * @param mm
	 * @return MessageModel<User, Map<String, String>> some register result
	 */
	@PostMapping(value = "/registration", produces = "application/json")
	@PreAuthorize("hasAuthority('ADMIN')")
	public MessageModel<User> registration(
			@RequestBody @Valid User user,
			MessageModel<User> mm
		) {
		
		if (userRepo.findByUsername(user.getUsername()) != null) {
			return mm.sendError("userERROR", "Current user already exists");
		}
		
		if (!user.isCorrect()) {
			return mm.sendError("userERROR", "User data is not correct");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user);
		
		user.setPassword(null);
		logger.log(String.format("user created: %s", user.getUsername()), this.logName);
		
		return mm.sendData(user);
	}
}
