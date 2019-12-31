package com.gyumin.jwtauthentication.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	private ApplicationUserRepository applicationUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(final ApplicationUserRepository applicationUserRepository,
						  final BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.applicationUserRepository = applicationUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@PostMapping("/sign-up")
	public void signUp(@RequestBody ApplicationUser applicationUser) {
		applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
		applicationUserRepository.save(applicationUser);
	}
}
