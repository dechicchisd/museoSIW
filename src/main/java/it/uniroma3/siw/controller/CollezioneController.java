package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.validator.CollezioneValidator;
import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.service.CollezioneService;
import it.uniroma3.siw.service.CuratoreService;

@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
	
	@Autowired
	private CuratoreService curatoreService;
	
	@Autowired
	private CollezioneValidator collezioneValidator;
	
	@RequestMapping(value="/addCollezioneForm", method=RequestMethod.GET)
	public String getAddCollezioneForm(Model model) {
		model.addAttribute("collezione", new Collezione());
		model.addAttribute("curatori", this.curatoreService.tutti());
		return "/admin/addCollezioneForm.html";
	}
	
	@RequestMapping(value="/collezione", method=RequestMethod.POST)
	public String newCollezione(@ModelAttribute("collezione") Collezione collezione,
								BindingResult bindingResult,
								Model model) {
		
		this.collezioneValidator.validate(collezione, bindingResult);
			
		if(!bindingResult.hasErrors()) {
			this.collezioneService.inserisci(collezione);
			model.addAttribute("collezioni", this.collezioneService.tutti());
			return "collezioni.html";
			}
		
		return "addCollezioneForm";
	}
	
	@RequestMapping(value="/collezioni")
	public String getCollezioni(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "collezioni.html";
	}
}
