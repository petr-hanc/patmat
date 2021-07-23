package patmat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDonationRepository implements DonationRepository {
	
	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcDonationRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public List<Donation> findAll() {
		return jdbc.query(
				"select donat_id, created_on, donor_id, date, amount, message"
				+ "from donations join donors",
				new DonationMapper()
				);
	}
	
	@Override
	public Optional<Donation> findById(long id) {
		Donation donation = jdbc.queryForObject("select id, created_on, donor_id, date, amount, message from donations where id=?",
				new DonationMapper(), id);
		return Optional.ofNullable(donation);
	}
	
}

class DonationMapper implements RowMapper<Donation> {
	public Donation mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Donation(
				rs.getLong("donat_id"),
				rs.getDate("created_on").toLocalDate(),
				new DonorMapper().mapRow(rs, rowNum),
				rs.getDate("date").toLocalDate(),
				rs.getInt("amount"),
				rs.getString("message")
				);
	}

}