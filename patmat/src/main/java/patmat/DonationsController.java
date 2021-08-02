package patmat;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donations/")
public class DonationsController {
	@Autowired
	DonorRepository repository;
	@Autowired
	DonationRepository donationRepository;

	@GetMapping("del/{id}")		
	public String deleteDonation (@PathVariable("id") long id, Model model) {
		Donation donation = donationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid donation Id:" + id));
		Donor donor = donation.getDonor();
		donationRepository.delete(donation);
		model.addAttribute("donors", repository.findAll());  
		return "redirect:/donors/" + donor.getDonorId();
	} 

	@GetMapping("edit/{id}")
	public String showEditDonationForm (@PathVariable("id") long id, Model model) {
		Donation donation = donationRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid donation Id:" + id));
		model.addAttribute("donation", donation);  
		return "edit-donation";
	}

	@PostMapping("edit/{id}")
	public String editDonation (@PathVariable("id") long id, @Valid Donation donation, BindingResult result, Model model) {
		try {
			Donation origDonation = donationRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Invalid donation id:" + id));
			Donor donor = origDonation.getDonor();
			if (donation == null) return "redirect:/donors/";
			donation.setDonatId(id);
			if (result.hasErrors()) {
				return "edit-donation";
			}
			if (donor == null) throw new Exception("Invalid donor (null) for donation id:" + id); 
			donation.setDonor(donor);
			donationRepository.save(donation);
			model.addAttribute("donor", donor);
			model.addAttribute("donations", donor.getDonations());
			return "redirect:/donors/" + donor.getDonorId();
		}
		catch (Exception e) {
			e.printStackTrace();
			return "redirect:/donors/";   
		}
	}
}