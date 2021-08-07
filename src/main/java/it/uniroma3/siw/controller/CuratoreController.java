package it.uniroma3.siw.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.controller.validator.CuratoreValidator;
import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.service.CollezioneService;
import it.uniroma3.siw.service.CuratoreService;

@Controller
public class CuratoreController {
	
	@Autowired
	private CuratoreService curatoreService;
	
	@Autowired
	private CuratoreValidator curatoreValidator;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@RequestMapping(value="/addCuratoreForm", method=RequestMethod.GET)
	public String getAddCuratoreForm(Model model) {
		model.addAttribute("curatore", new Curatore());
		
		List<Collezione> collezioni = this.collezioneService.tutti();
		Collections.sort(collezioni);
		model.addAttribute("collezioni", collezioni);
		
		return "/admin/addCuratoreForm.html";
	}
	
	@RequestMapping(value="/addCuratore", method=RequestMethod.POST)
	public String addCuratore(Model model, 
							  @ModelAttribute("curatore") Curatore curatore,
							  BindingResult bindingResult) {
		
		this.curatoreValidator.validate(model, bindingResult);
		
//		if(!bindingResult.hasErrors()) {
			this.curatoreService.inserisci(curatore);
			return "/admin/operazioni.html";
//		}
//		
//		return "/admin/addCuratoreForm.html";
	}
	
	@RequestMapping(value="/eliminaCuratore/{id}", method=RequestMethod.GET)
	public String eliminaCuratore(@PathVariable("id") Long id, Model model){
		this.curatoreService.deleteCuratore(id);
		List<Curatore> curatori = this.curatoreService.tutti();
		Collections.sort(curatori);
		
		model.addAttribute("curatori", curatori);
		return "/admin/curatori.html";
	}
	
	@RequestMapping(value="/getCuratori", method=RequestMethod.GET)
	public String getCuratore(Model model){
		List<Curatore> curatori = this.curatoreService.tutti();
		Collections.sort(curatori);
		
		model.addAttribute("curatori", curatori);
		return "/admin/curatori.html";
	}
}
