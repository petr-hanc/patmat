package patmat;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        model.addAttribute("newDonor", new Donor());
        return "donors";
	}
    
    @PostMapping
	public String addDonor(@RequestParam String town, @Valid @ModelAttribute("newDonor") Donor newDonor, BindingResult result, Model model) {
    	if (result.hasErrors()) {
    		model.addAttribute("donors", repository.findAll());
            model.addAttribute("newDonor", newDonor);
            return "donors";
    	}
        newDonor.setDonations(null);
        repository.save(newDonor);
        model.addAttribute("donors", repository.findAll());
        model.addAttribute("newDonor", new Donor());
        return "donors";
	}
    
    @GetMapping("{id}")
	public String showDonor(@PathVariable long id, Model model) {
    	Donor donor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid donor Id:" + id));
    	List<Donation> donations = donationRepository.findByDonorId(id);
    	Donation newDonation = new Donation();		// for adding a new donation
    	donor.setDonations(donations);
        model.addAttribute("donor", donor);
        model.addAttribute("donations", donations);
        model.addAttribute("newDonation", newDonation);
        return "donor";
    }    
    
    @PostMapping("{id}")
	public String addDonation(@PathVariable Long id, @Valid @ModelAttribute("newDonation") Donation newDonation, BindingResult result, Model model) {
    	// for validation to work there is necessary @ModelAttribute("newDonation") before parameter, because type Donation and var. newDonation have different names
    	Donor donor = repository.findById(id)
    			.orElseThrow(() -> new IllegalArgumentException("Invalid donor id:" + id));
    	if (newDonation == null) {
    		System.out.println("Error of donation " + id + ": null donation");
    	}	
    	else {
    		if (result.hasErrors()) {
    			List<Donation> donations = donationRepository.findByDonorId(id);
    			donor.setDonations(donations);
    			model.addAttribute("donor", donor);
    			model.addAttribute("donations", donations);
    			model.addAttribute("newDonation", newDonation);
    			return "donor";
    		}
    		newDonation.setDonor(donor);
    		donationRepository.save(newDonation);
    	}
    	model.addAttribute("donor", donor);
    	model.addAttribute("newDonor", new Donor());
    	return "redirect:/donors/" + donor.getDonorId();
    }

    @GetMapping("del/{id}")
	public String deleteDonor (@PathVariable("id") long id, Model model) {
    	Donor donor = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid donor Id:" + id));
    	repository.delete(donor);
        model.addAttribute("donors", repository.findAll());
        model.addAttribute("newDonor", new Donor());
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
            donor.setDonorId(id);
            return "edit-donor";
    	}
    	if (donor == null) return "redirect:/donors";
    	repository.save(donor);
        model.addAttribute("donors", repository.findAll());
        model.addAttribute("newDonor", new Donor());
        return "donors";         
	}  
    

       
}