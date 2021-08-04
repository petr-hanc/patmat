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
import org.springframework.web.bind.annotation.RequestParam;

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
		model.addAttribute("donorId", donation.getDonor().getDonorId());  
		return "edit-donation";
	}

	@PostMapping("edit/{id}")
	public String editDonation (@PathVariable("id") long id,  @RequestParam Long donorId, @Valid Donation donation, BindingResult result, Model model) {
			if (donation == null) return "redirect:/donors/";
			if (result.hasErrors()) {
				model.addAttribute("donorId", donorId); 
				return "edit-donation";
			}
			Donor donor = repository.findById(donorId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid donor id:" + donorId + " for donation id: " + id));
			donation.setDonor(donor);
			donationRepository.save(donation);
			model.addAttribute("donor", donor);
			return "redirect:/donors/" + donor.getDonorId();
	}
}