package patmat;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository {
	List<Donation> findAll();
	Optional<Donation> findById(long id);
	List<Donation> findByDonorId(long donorId); 
	Donation save(Donation donation);
	void delete(Donation donation); 
}
