package ma.GestionCandidature.gestion.candidature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class )
public class GestionCandidatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionCandidatureApplication.class, args);
	}

}
