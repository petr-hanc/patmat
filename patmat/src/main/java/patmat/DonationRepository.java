package patmat;

import org.springframework.stereotype.Repository;

//import java.util.List;

@Repository
public interface DonationRepository {
	Iterable<Donation> findAll();
	Donation findOne(long id);
	Donation save(Donation donation);
}
