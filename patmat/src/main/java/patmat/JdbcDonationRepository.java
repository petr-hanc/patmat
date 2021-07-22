package patmat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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
		return jdbc.query("select id, created_on, donor_id, date, amount, message from donations", this::mapRowToDonation);
	}
	
	@Override
	public Donation findById(long id) {
		return jdbc.queryForObject("select id, created_on, donor_id, date, amount, message from donations where id=?",
				this::mapRowToDonation, id);
	}
	
}

class DonationMapper implements RowMapper<Donation> {
	public Donation mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Donation(
				rs.getLong("donat_id"),
				rs.getDate("created_on").toLocalDate(),
				(new DonorMapper).mapRow(rs, rowNum),
				rs.getDate("date").toLocalDate(),
				rs.getInt("amount"),
				rs.getString("message")
				);
	}

}