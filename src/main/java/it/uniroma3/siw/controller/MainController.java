package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CollezioneService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.utils.UtilsSiw;

@Controller
public class MainController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
		return "index.html";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHome(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	
		model.addAttribute("credentials", credentials);
		
		List<Collezione> collezioni = collezioneService.tutti();
    	Collezione[] randomColl = UtilsSiw.randomSelection(collezioni);
    	
    	UtilsSiw.cut(randomColl);
    	
    	System.out.println("\n\n\n\n\n");
    	model.addAttribute("collezioni", randomColl);

    	return "home.html";
	}
	
	@RequestMapping(value = "/getOperazioni", method = RequestMethod.GET)
	public String getOperazioni(Model model) {
		

    	return "/admin/operazioni.html";
	}
}
