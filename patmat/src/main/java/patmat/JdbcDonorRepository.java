package patmat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDonorRepository implements DonorRepository {

	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcDonorRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public List<Donor> findAll() {
		
	}	

}

class DonorMapper implements RowMapper<Donor> {
	public Donor mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Donor(
				rs.getLong("donor_id"),
				rs.getDate("created_on").toLocalDate(),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getString("town")
				);
	}
}