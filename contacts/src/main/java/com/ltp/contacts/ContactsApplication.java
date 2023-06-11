package com.ltp.contacts;

import com.ltp.contacts.pojo.User;
import com.ltp.contacts.pojo.UserDescription;
import com.ltp.contacts.repository.UserDescriptionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ltp.contacts.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@SpringBootApplication
@AllArgsConstructor
public class ContactsApplication  {


	public static void main(String[] args) {
		SpringApplication.run(ContactsApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}



}
