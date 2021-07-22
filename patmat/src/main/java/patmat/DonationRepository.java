package patmat;

import java.util.List;

import org.springframework.stereotype.Repository;

//import java.util.List;

@Repository
public interface DonationRepository {
	List<Donation> findAll();
	Donation findOne(long id);
	Donation save(Donation donation);
}
