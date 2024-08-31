package com.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.services.EmailServices;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailServices services;

	@Test
	void sendEmailTest() {
		services.sendEmail("kundusomnath610@gmail.com", 
			"Testing Mail Sender", 
			"Testing Mail from contact manager");
	}

}
