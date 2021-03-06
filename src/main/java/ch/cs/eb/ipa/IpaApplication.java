package ch.cs.eb.ipa;

import ch.cs.eb.ipa.repository.CUserRepository;
import ch.cs.eb.ipa.repository.UAuthorityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author: Elijon Berisha
 * date: 10.03.2022
 * class: IpaApplication.java
 */

@SpringBootApplication
public class IpaApplication {

	public static void main(String[] args) {
		CUserRepository userRepository = new CUserRepository();
		UAuthorityRepository authorityRepository = new UAuthorityRepository();

		authorityRepository.populateTable();
		userRepository.createAdmin();

		SpringApplication.run(IpaApplication.class, args);
	}

}
