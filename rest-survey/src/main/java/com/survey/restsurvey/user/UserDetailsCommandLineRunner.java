package com.survey.restsurvey.user;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public UserDetailsCommandLineRunner(UserDetailsRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	private UserDetailsRepository userRepo;
	
	@Override
	public void run(String... args) throws Exception {
		logger.info(Arrays.toString(args));
		userRepo.save(new UserDetails("vinayak", "admin"));
		userRepo.save(new UserDetails("ravi", "admin"));
		userRepo.save(new UserDetails("john", "manager"));
		
		List<UserDetails> users = userRepo.findAll();
		users.stream().forEach(System.out :: println);
		System.out.println("=========================================");
		List<UserDetails> userAdmin = userRepo.findByRole("admin");
		userAdmin.stream().forEach(System.out :: println);
	}

}
