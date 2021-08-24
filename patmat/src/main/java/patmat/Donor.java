package patmat;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
//import javax.persistence.Transient;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
// import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "donors")
public class Donor {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
	private long id;
    @Column(name = "firstname")
	private String firstName;
    @NotNull
    @Size(min=1, message="Příjmení musí mít aspoň 1 znak")
    @Column(name = "lastname")
	private String lastName;
    @Column(name = "town")
	private String town;
    @OneToMany /* (cascade = CascadeType.ALL, orphanRemoval = true) */
    @JoinColumn(name = "donor_id")
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
