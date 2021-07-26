package patmat;

import java.sql.ResultSet;
import java.sql.SQLException;
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
				"SELECT dr.donor_id, first_name, last_name, town, dr.created_on, donat_id, amount, date_dt, message, dt.created_on"
				+ "FROM donors dr LEFT JOIN donations dt USING (donor_id)",
				new DonationMapper()
				);
	}
	
	@Override
	public Optional<Donation> findById(long id) {
		Donation donation = jdbc.queryForObject(
				"SELECT dr.donor_id, first_name, last_name, town, dr.created_on AS dr_created_on, donat_id, amount, date_dt, message, dt.created_on AS dt_created_on"
				+ "FROM donors dr LEFT JOIN donations dt USING (donor_id)"
				+ "WHERE donat_id = ?",
				new DonationMapper(),
				id
				);
		return Optional.ofNullable(donation);
	}
	
	@Override
	public List<Donation> findByDonorId(long donor_id) {
		return jdbc.query(
				"SELECT dr.donor_id, first_name, last_name, town, dr.created_on AS dr_created_on, donat_id, amount, date_dt, message, dt.created_on AS dt_created_on"
				+ "FROM donors dr LEFT JOIN donations dt USING (donor_id)"
				+ "WHERE dr.donor_id = ?",
				new DonationMapper(),
				donor_id
				);
	}
	
}

class DonationMapper implements RowMapper<Donation> {
	public Donation mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Donation(
				rs.getLong("donat_id"),
				rs.getDate("dt_created_on").toLocalDate(),
				new DonorMapper().mapRow(rs, rowNum),
				rs.getDate("date_dt").toLocalDate(),
				rs.getInt("amount"),
				rs.getString("message")
				);
	}

}