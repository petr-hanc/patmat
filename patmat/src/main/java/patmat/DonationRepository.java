package patmat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
	//public List<Donation> findByName(String name);
}
