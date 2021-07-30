package patmat;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository {
    List<Donor> findAll();
    Optional<Donor> findById(long donorId);
	Donor save(Donor donor);
	void delete(Donor donor);
}
