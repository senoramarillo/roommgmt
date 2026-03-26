package com.spring.roommgmt;

import com.spring.roommgmt.banner.CustomBanner;
import com.spring.roommgmt.enums.ApplicationBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application Entry Point.
 * Initializes the Room Management System with a custom banner.
 * This application manages buildings, rooms, and meeting scheduling.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@SpringBootApplication
public class RoomManagementApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * Configures a custom banner before starting the application context.
	 *
	 * @param args command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(RoomManagementApplication.class);
		springApplication.setBanner(new CustomBanner(ApplicationBanner.ROOMMGMT));
		springApplication.run(args);
	}

}
