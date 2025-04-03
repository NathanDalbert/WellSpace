package com.WellSpace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.WellSpace.modules.salas")
public class WellSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WellSpaceApplication.class, args);
	}

}
