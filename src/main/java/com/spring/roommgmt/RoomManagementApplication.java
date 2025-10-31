package com.spring.roommgmt;

import com.spring.roommgmt.banner.CustomBanner;
import com.spring.roommgmt.enums.ApplicationBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoomManagementApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(RoomManagementApplication.class);
		springApplication.setBanner(new CustomBanner(ApplicationBanner.ROOMMGMT));
		springApplication.run(args);
	}

}
