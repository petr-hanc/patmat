package patmat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
//    List<Donor> findByName(String name);
}
