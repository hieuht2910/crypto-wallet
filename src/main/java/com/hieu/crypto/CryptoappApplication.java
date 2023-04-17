package com.hieu.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.hieu.crypto"})
@EnableScheduling
public class CryptoappApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoappApplication.class, args);
	}

}
