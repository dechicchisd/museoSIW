package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.CollezioneValidator;
import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.model.Opera;
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
	
	@RequestMapping(value="/collezione/{id}", method=RequestMethod.GET)
	public String getCollezione(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("collezione", this.collezioneService.cercaCollezionePerId(id));
		return "collezione.html";
	}
	
	@RequestMapping(value="/collezioni")
	public String getCollezioni(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "collezioni.html";
	}
	
	@RequestMapping(value="/getEditCollezioneForm/{id}", method=RequestMethod.GET)
	public String getEditCollezioneForm(Model model, @PathVariable("id") Long id) {
		model.addAttribute("collezione", this.collezioneService.cercaCollezionePerId(id));
		model.addAttribute("curatori", this.curatoreService.tutti());
		return "/admin/editCollezioneForm.html";
	}
	
	@RequestMapping(value="/editCollezione/{id}", method=RequestMethod.POST)
	public String modificaOpera(@ModelAttribute("collezione") Collezione collezione, 
								@PathVariable("id") Long id,
								BindingResult bindinResult,
								Model model){
		
		this.collezioneValidator.validate(collezione, bindinResult);
		
		if(!bindinResult.hasErrors()) {
			
			this.collezioneService.deleteCollezione(id);
			this.collezioneService.inserisci(collezione);
			
			model.addAttribute("collezioni", this.collezioneService.tutti());

			return "collezioni.html";
		}
		return "admin/editCollezioneForm.html";
	}
	
	
	@RequestMapping(value="/eliminaCollezione/{id}", method=RequestMethod.GET)
	public String eliminaOpera(@PathVariable("id") Long id, Model model){
		this.collezioneService.deleteCollezione(id);
		model.addAttribute("opere", this.collezioneService.tutti());
		return "opere.html";
	}
}
