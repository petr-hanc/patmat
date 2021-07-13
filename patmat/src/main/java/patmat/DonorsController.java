package patmat;

import java.time.LocalDate;

import javax.validation.Valid;

// import java.util.List;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/donors/")
public class DonorsController {

	@Autowired
	DonorRepository repository;

	@Autowired
	DonationRepository donationRepository;

    @GetMapping
	public String showDonorsList(Model model) {
        model.addAttribute("donors", repository.findAll());
        return "donors";
	}
    
    @PostMapping
	public String addDonor(@RequestParam String town, 
						@RequestParam String firstName, @RequestParam String lastName, Model model) {
        Donor newDonor = new Donor();
        newDonor.setTown(town);
        newDonor.setFirstName(firstName);
        newDonor.setLastName(lastName);
        repository.save(newDonor);

        model.addAttribute("donor", newDonor);
        model.addAttribute("donations", donationRepository.findAll());
        return "redirect:/donor/" + newDonor.getId();
	}
    
    @GetMapping("{id}")
	public String showDonor(@PathVariable long id, Model model) {
    	Donor donor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid donor Id:" + id));
        model.addAttribute("donor", donor);
        model.addAttribute("donations", donor.getDonations());
        return "donor";
    }    
    
    @PostMapping("{id}")
	public String addDonation(@PathVariable Long id, @RequestParam String date, 
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
            return "redirect:/donors/" + donor.getId();
    	}

        model.addAttribute("donors", repository.findAll());
        return "redirect:/donors/";
    }

    @GetMapping("del/{id}")
	public String deleteDonor (@PathVariable("id") long id, Model model) {
    	Donor donor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid donor Id:" + id));
    	repository.delete(donor);
        model.addAttribute("donors", repository.findAll());
        return "donors";            
	}  
    
    @GetMapping("edit/{id}")
	public String showEditDonorForm (@PathVariable("id") long id, Model model) {
    	Donor donor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid donor Id:" + id));
        model.addAttribute("donor", donor);
        return "edit-donor";         
	}   
    
    @PostMapping("edit/{id}")
	public String editDonor (@PathVariable("id") long id, @Valid Donor donor, BindingResult result, Model model) {
    	if (result.hasErrors()) {
            donor.setId(id);
            return "edit-donor";
    	}
    	if (donor == null) return "redirect:/donors";
    	repository.save(donor);
        model.addAttribute("donors", repository.findAll());
        return "donors";         
	}  
    

       
}