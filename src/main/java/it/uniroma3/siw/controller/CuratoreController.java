package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.service.CollezioneService;
import it.uniroma3.siw.service.CuratoreService;

@Controller
public class CuratoreController {
	
	@Autowired
	private CuratoreService curatoreService;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@RequestMapping(value="/addCuratoreForm", method=RequestMethod.GET)
	public String getAddCuratoreForm(Model model) {
		model.addAttribute("curatore", new Curatore());
		model.addAttribute("collezioni", this.collezioneService.tutti());
		
		return "/admin/addCuratoreForm.html";
	}
}
