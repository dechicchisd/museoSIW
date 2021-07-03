package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class MainController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
		return "index.html";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHome(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	
		model.addAttribute("credentials", credentials);
		
		return "home1.html";
	}
}
