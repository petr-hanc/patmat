package patmat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	public HomeController() {
	}
	
	@GetMapping("/")
	public String home() {
		return "redirect:/donors/";
	}
	

}
