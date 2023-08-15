package br.com.pedro.dev.ariesapiproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing()
public class AriesApiProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AriesApiProductsApplication.class, args);
	}

}
