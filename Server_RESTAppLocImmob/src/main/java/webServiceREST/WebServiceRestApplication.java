package webServiceREST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
	"webServiceREST.models",
})

@EnableJpaRepositories(basePackages = {
	"webServiceREST.repositories"
})


@SpringBootApplication(scanBasePackages = {
	"webServiceREST.controllers",
	"webServiceREST.data"
})

public class WebServiceRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebServiceRestApplication.class, args);
	}

}

