package patmat;

import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
		List<Donation> donations = new LinkedList<Donation>();
		List<Donor> donors = new LinkedList<Donor>();
		donations.add(new Donation(LocalDate.parse("2020-11-05"), 500000 , "Nezapomeňte!"));
		donations.add(new Donation(LocalDate.parse("2021-01-01"), 200000, "Prémie :)"));
		donationRepository.saveAll(donations);
		donors.add(new Donor("Ivo", "Balenta", "Uherské Hradiště", donations));
		donations= new LinkedList<Donation>();
		donations.add(new Donation(LocalDate.parse("2020-10-23"), 1000000 , "My budem tovarysici"));
		donations.add(new Donation(LocalDate.parse("2020-12-22"), 3000, ""));
		donations.add(new Donation(LocalDate.parse("2020-09-15"), 5000, ""));
		donationRepository.saveAll(donations);
		donors.add(new Donor("", "Lucoil Aviation Czech", "Praha", donations));
		donations= new LinkedList<Donation>();
		donors.add(new Donor("Hurvínek", "Spejblův", "Nymburk", donations));
		donations= new LinkedList<Donation>();
		donors.add(new Donor("Včelí", "Medvídci", "Díra u Malé Čermné", donations));
		donations= new LinkedList<Donation>();
		donors.add(new Donor("Jarek", "Kmotr", "Přerov", donations));
		donations= new LinkedList<Donation>();
		donors.add(new Donor("Petr", "Hanč", "Červený Kostelec", donations));
		donations= new LinkedList<Donation>();
		donorRepository.saveAll(donors);
	}

}