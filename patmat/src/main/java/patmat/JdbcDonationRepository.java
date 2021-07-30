package patmat;

import java.sql.Date;
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
				+ " FROM donors dr LEFT JOIN donations dt USING (donor_id)",
				new DonationMapper()
				);
	}
	
	@Override
	public Optional<Donation> findById(long id) {
		Donation donation = jdbc.queryForObject(
				"SELECT dr.donor_id, first_name, last_name, town, dr.created_on AS dr_created_on, donat_id, amount, date_dt, message, dt.created_on AS dt_created_on"
				+ " FROM donors dr LEFT JOIN donations dt USING (donor_id)"
				+ " WHERE donat_id = ?",
				new DonationMapper(),
				id
				);
		return Optional.ofNullable(donation);
	}
	
	@Override
	public List<Donation> findByDonorId(long donorId) {
		List<Donation> donations = jdbc.query(
				"SELECT dr.donor_id, first_name, last_name, town, dr.created_on AS dr_created_on, donat_id, amount, date_dt, message, dt.created_on AS dt_created_on"
				+ " FROM donors dr LEFT JOIN donations dt USING (donor_id)"
				+ " WHERE dr.donor_id = ?",
				new DonationMapper(),
				donorId
				);
		if (donations.size() == 1 && donations.iterator().next() == null)
			return null;	// query returned List with 1 null record
		else return donations;
	}
	
	/** Parameter donation: donation must have a donor (donation.donor is not null]
	 *  Returns null if there is a problem or the same object as the parameter. 
	 */
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
			String query = "INSERT INTO donations(amount, date_dt, message, donor_id, created_on)"
					+ " VALUES (?, ?, ?, ?, ?)";
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
					keyHolder
					);
			donation.setDonatId(keyHolder.getKey().longValue());
			System.out.println("Id of new donation is: " + donation.getDonatId());
		} else { // donation update
			String query = "UPDATE donations\r\n"
					+ "SET amount = ?, date_dt = ?, message = ?, donor_id = ?\r\n"
					+ "WHERE donat_id = ?";
			jdbc.update(
					query,
					donation.getAmount(),
					donation.getDate(),
					donation.getMessage(),
					donorId,
					donation.getDonatId()
					);
		}
		return donation;
	}

	@Override
	public void delete(Donation donation) {
		if (donation == null) {
			System.out.println("Delete donation error: null parameter");
			return;
		}
		if (donation.getDonatId() == null) System.out.println("Delete donation error: null id");
		else jdbc.update("DELETE FROM donations WHERE donat_id = ?", donation.getDonatId());
	}
		
} // JdbcDonationRepository

class DonorJoinMapper implements RowMapper<Donor> {
	public Donor mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Donor(
				rs.getLong("donor_id"),
				rs.getDate("dr_created_on").toLocalDate(),
				rs.getString("first_name"),
				rs.getString("last_name"),
				rs.getString("town")
				);
	}
}

class DonationMapper implements RowMapper<Donation> {
	public Donation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long donatId = rs.getObject("donat_id", Long.class);	// getLong() would return 0 if NULL
		if (donatId == null) return null;
		else {
			Date createdOn = rs.getDate("created_on");
			Date date = rs.getDate("date_dt");
			return new Donation(
					donatId,
					new DonorJoinMapper().mapRow(rs, rowNum),
					(createdOn == null) ? null : createdOn.toLocalDate(),
					(date == null) ? null : date.toLocalDate(),
					rs.getInt("amount"),
					rs.getString("message")
					);			
		}
		
	}

}