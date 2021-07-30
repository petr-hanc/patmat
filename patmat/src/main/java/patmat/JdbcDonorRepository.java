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
public class JdbcDonorRepository implements DonorRepository {

	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcDonorRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public List<Donor> findAll() {
		return jdbc.query(
				"SELECT * FROM donors",
				new DonorMapper()
				);
	}	
	
	@Override
	public Optional<Donor> findById(long donorId) {
		Donor donor = jdbc.queryForObject(
				"SELECT * FROM donors\r\n"
				+ "WHERE donor_id = ?",
				new DonorMapper(),
				donorId
				);
		return Optional.ofNullable(donor);
	}
	
	@Override
	public Donor save(Donor donor) {
		if (donor == null) {
			System.out.println("Save donor error: null parameter");
			return null;
		}
		if (donor.getDonorId() == null) { // new donor - insert
			String query = "INSERT INTO donors(created_on, first_name, last_name, town)\r\n"
					+ "VALUES (?, ?, ?, ?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbc.update(
					connection -> {
						PreparedStatement stmt = connection.prepareStatement(query, new String[]{"donor_id"});
						stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
						stmt.setString(2, donor.getFirstName());
						stmt.setString(3, donor.getLastName());
						stmt.setString(4, donor.getTown());
						return stmt;
					},
					keyHolder
			);
			donor.setDonorId(keyHolder.getKey().longValue());
			System.out.println("Id of new donor is: " + donor.getDonorId());
		} else { // donor update
			String query = "UPDATE donors\r\n"
					+ "SET first_name = ?, last_name = ?, town = ? \r\n"
					+ "WHERE donor_id = ?";
			jdbc.update(
					query,
					donor.getFirstName(),
					donor.getLastName(),
					donor.getTown(),
					donor.getDonorId()
					);			
		}
		return donor;
	}
	
	@Override
	public void delete(Donor donor) {
		if (donor == null) {
			System.out.println("Delete donor error: null parameter");
			return;
		}
		if (donor.getDonorId() == null) System.out.println("Delete donor error: null id");
		else jdbc.update("DELETE FROM donors WHERE donor_id = ?", donor.getDonorId());
	}

} // DonorRepository

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