package patmat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PatmatApplication implements CommandLineRunner {

    @Autowired
    DonorRepository donorRepository;

    @Autowired
    DonationRepository donationRepository;

	public static void main(String[] args) {
		SpringApplication.run(PatmatApplication.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {
	
	}
}