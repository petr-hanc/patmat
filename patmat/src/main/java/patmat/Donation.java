package patmat;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Donation {
	private Long donatId;
    private LocalDate createdOn;
    private Donor donor;
    private LocalDate date;
    private long amount;  // in CZK
    private String message;
    
    public Donation() {
		super();
    }
/*    
    public Donation(LocalDate date, long amount, String message) {
		super();
		this.date = date;
		this.amount = amount;
		this.message = message;
	}
*/
    
	public Donation(Long donatId, LocalDate createdOn, Donor donor, LocalDate date, long amount, String message) {
		super();
		this.donatId = donatId;
		this.createdOn = createdOn;
		this.donor = donor;
		this.date = date;
		this.amount = amount;
		this.message = message;
	}

    
}
