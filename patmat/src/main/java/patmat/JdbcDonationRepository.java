package patmat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	
	/** Parameter donation: donation must have a donor (donation.donor is not null] */ 
	@Override
	public Donation save(Donation donation) {
		final long donorId;
		
		if (donation == null) {
			System.out.println("Save donation error: null parameter");
			return null;
		}
		if (donation.getDonor() != null) donorId = donation.getDonor().getDonorId();
		else {
			System.out.println("Save donation error: no donor");
			return null;
		}
		if (donation.getCreatedOn() == null) donation.setCreatedOn(LocalDate.now());
		if (donation.getDonatId() == null) { // new donation - insert
			String query = "INSERT INTO donations(amount, date_dt, message, donor_id, created_on) "
					+ "VALUES (?, ?, ?, ?, ?)";

			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbc.update(
					connection -> {
						PreparedStatement stmt = connection.prepareStatement(query, new String[]{"donat_id"});
						stmt.setLong(1, donation.getAmount());
						stmt.setDate(2, java.sql.Date.valueOf(donation.getDate()));
						stmt.setString(3, donation.getMessage());
						stmt.setLong(4, donorId);
						stmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
						return stmt;
					},
					keyHolder);

			donation.setDonatId(keyHolder.getKey().longValue());
			System.out.println("Id of new donation is: " + donation.getDonatId());
		} else { // donation update
			String query = "UPDATE donations\r\n"
					+ "SET amount = ?, date_dt = ?, message = ?, donor_id = ?, created_on = ?\r\n"
					+ "WHERE donat_id = ?";
			jdbc.update(
					query,
					donation.getAmount(),
					donation.getDate(),
					donation.getMessage(),
					donorId,
					donation.getCreatedOn(),
					donation.getDonatId()
					);
		}
		return donation;
	}
		
} // JdbcDonationRepository 

class DonationMapper implements RowMapper<Donation> {
	public Donation mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Donation(
				rs.getLong("donat_id"),
				new DonorMapper().mapRow(rs, rowNum),
				rs.getDate("dt_created_on").toLocalDate(),
				rs.getDate("date_dt").toLocalDate(),
				rs.getInt("amount"),
				rs.getString("message")
				);
	}

}