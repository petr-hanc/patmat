package patmat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
//    List<Donor> findByName(String name);
}
