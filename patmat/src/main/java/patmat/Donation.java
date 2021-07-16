package patmat;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Donation {
	private long id;
    private LocalDate createdOn;
    private Donor donor;
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

}
