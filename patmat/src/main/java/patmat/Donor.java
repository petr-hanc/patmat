package patmat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Donor {
	private Long donorId;
    private LocalDate createdOn;
    @Size(max=5, message="Jméno může mít nejvýš 5 znaků.")
	private String firstName;
    @NotBlank(message="Příjmení nesmí být prázdné.")
    @Size(max=5, message="Příjmení může mít nejvýš 5 znaků.")
    //@Size(min=1, max=5, message="Příjmení musí mít 1 až 5 znaků")
	private String lastName;
    @Size(max=5, message="Jméno může mít nejvýš 5 znaků.")
	private String town;
	private List<Donation> donations;

	public Donor() {
		super();
		donations =  new ArrayList<Donation>();
	}

	public Donor(String firstName, String lastName, String town, List<Donation> donations) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.town = town;
		this.donations = (donations != null) ? donations : new ArrayList<Donation>();
	}

	
	public Donor(Long donorId, LocalDate createdOn, String firstName, String lastName, String town) {
		super();
		this.donorId = donorId;
		this.createdOn = createdOn;
		this.firstName = firstName;
		this.lastName = lastName;
		this.town = town;
		donations =  new ArrayList<Donation>();
	}
	
}
