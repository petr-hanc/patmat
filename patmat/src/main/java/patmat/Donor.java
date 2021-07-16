package patmat;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Donor {
	private long id;
    private LocalDate createdOn;	
	private String firstName;
    @NotNull
    @Size(min=1, message="Příjmení musí mít aspoň 1 znak")
	private String lastName;
	private String town;
	private List<Donation> donations;

	public Donor() {
		super();
	}

	public Donor(String firstName, String lastName, String town,
			List<Donation> donations) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.town = town;
		this.donations = donations;
	}
}
