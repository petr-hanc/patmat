package patmat;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

//import java.util.List;

@Repository
public interface DonationRepository {
	List<Donation> findAll();
	Optional<Donation> findById(long id);
	Donation save(Donation donation);
	void deleteById(long id);
}
