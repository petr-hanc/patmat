package patmat;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Donation {
	private Long donatId;
    private Donor donor;
    private LocalDate createdOn;
    private LocalDate date;
    private long amount;  // in CZK
    private String message;
    
    public Donation() {
		super();
    }
    
    public Donation(LocalDate date, long amount, String message) {
		super();
		this.date = date;
		this.amount = amount;
		this.message = message;
	}

    
	public Donation(Long donatId, Donor donor, LocalDate createdOn, LocalDate date, long amount, String message) {
		super();
		this.donatId = donatId;
		this.donor = donor;
		this.createdOn = createdOn;
		this.date = date;
		this.amount = amount;
		this.message = message;
	}

    
}
