package ma.donasid.recrute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaRepositories("ma.donasid.recrute.repositories")
@SpringBootApplication//(exclude = SecurityAutoConfiguration.class )
public class Application {
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
