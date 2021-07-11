package patmat;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DonationsController {
	
	@Autowired
	DonorRepository repository;

	@Autowired
	DonationRepository donationRepository;


    @RequestMapping(value="/donor/{id}/donations", method=RequestMethod.POST)
	public String donorAddDonation(@PathVariable Long id, @RequestParam String date, 
			@RequestParam long amount, @RequestParam String message, Model model) {
    	Donation newDonation = new Donation(LocalDate.parse(date), amount, message);
    	
    	Donor donor = repository.findById(id)
    			.orElseThrow(() -> new IllegalArgumentException("Invalid donor Id:" + id));

    	if (donor != null) {
    		donationRepository.save(newDonation);
    		donor.getDonations().add(newDonation);
    		repository.save(donor);
            model.addAttribute("donor", repository.findById(id));
            model.addAttribute("donations", donor.getDonations());
            return "redirect:/donor/" + donor.getId();
    	}

        model.addAttribute("donors", repository.findAll());
        return "redirect:/donors";
    }
    
        @RequestMapping(value="donations/del/{id}",method=RequestMethod.GET)
    	public String deleteDonation (@PathVariable("id") long id, Model model) {
        	Donation donation = donationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid donation Id:" + id));
        	Donor donor = donation.getDonor();
        	donationRepository.delete(donation);
            model.addAttribute("donors", repository.findAll());  
            return "redirect:/donor/" + donor.getId();
    	} 
        
        @RequestMapping(value="donations/edit/{id}",method=RequestMethod.GET)
    	public String editDonation (@PathVariable("id") long id, Model model) {
        	Donation donation = donationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid donation Id:" + id));
        	//Donor donor = donation.getDonor();
            model.addAttribute("donation", donation);  
            //model.addAttribute("donor", donor);  
            return "edit-donation";
        }
        
        @RequestMapping(value="/donations/edited/{id}",method=RequestMethod.POST)
    	public String donationEdited (@PathVariable("id") long id, @Valid Donation donation, BindingResult result, Model model) {
        	/* donation = donationRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid donation Id:" + id)); */
        	if (result.hasErrors()) {
                //donation.setId(id);
                return "edit-donation";
        	}
        	donationRepository.save(donation);
        	//Donor donor = donation.getDonor();
        	//if (donor == null) return "redirect:/donors";
            //model.addAttribute("donor", donor);
            //model.addAttribute("donor", repository.findById(id));
            //model.addAttribute("donations", donor.getDonations());
            return "redirect:/donors";    
    	}  
}
