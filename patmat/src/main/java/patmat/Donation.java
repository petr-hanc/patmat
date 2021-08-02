package patmat;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Donation {
	private Long donatId;
    private Donor donor;
    private LocalDate createdOn;
    @DateTimeFormat(pattern = "d. M. yyyy", fallbackPatterns = { "yyyy-MM-dd", "dd.MM.yyyy" })
    private LocalDate date;
    private Long amount;  // in CZK
    private String message;
    
    public Donation() {
		super();
    }
    
    public Donation(LocalDate date, Long amount, String message) {
		super();
		this.date = date;
		this.amount = amount;
		this.message = message;
	}

    
	public Donation(Long donatId, Donor donor, LocalDate createdOn, LocalDate date, Long amount, String message) {
		super();
		this.donatId = donatId;
		this.donor = donor;
		this.createdOn = createdOn;
		this.date = date;
		this.amount = amount;
		this.message = message;
	}

    
}
