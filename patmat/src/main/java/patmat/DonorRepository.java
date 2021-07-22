package patmat;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DonorRepository {
    List<Donor> findAll();
}
