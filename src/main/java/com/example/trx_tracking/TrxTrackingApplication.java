package com.example.trx_tracking;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class TrxTrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrxTrackingApplication.class, args);
	}

	
}
