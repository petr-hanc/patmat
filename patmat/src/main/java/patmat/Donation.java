package patmat;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
	private long id;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "amount")
    private long amount;  // in CZK
    @Column(name = "message")
    private String message;
    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;
    
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
