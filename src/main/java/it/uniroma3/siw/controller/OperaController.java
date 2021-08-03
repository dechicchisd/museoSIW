package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import it.uniroma3.siw.controller.validator.OperaValidator;
import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.CollezioneService;
import it.uniroma3.siw.service.OperaService;
import it.uniroma3.siw.utils.UtilsSiw;

@Controller
public class OperaController {
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private CollezioneService collezioneService;
	
	@Autowired
	private OperaValidator operaValidator;
	
	@RequestMapping(value="getSave/{id}", method=RequestMethod.GET)
	public String getAddOperaForm(Model model, @PathVariable("id") Long id) {
		model.addAttribute("opera", new Opera());
		model.addAttribute("artista", artistaService.cercaArtistaPerId(id));
		model.addAttribute("artisti", this.artistaService.tutti());
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "admin/addOperaForm.html";
	}
	
	@RequestMapping(value="/opera", method=RequestMethod.POST)
	public String newOpera(@ModelAttribute("opera") Opera opera, 
						   @RequestParam("file") MultipartFile file,
						   BindingResult bindinResult,
						   Model model) {
		
		this.operaValidator.validate(opera, bindinResult);
		
		if(!bindinResult.hasErrors()) {
			Long idArtista = opera.getArtista().getId();
			Artista artista = artistaService.cercaArtistaPerId(idArtista);
			model.addAttribute("artista", artista);
			
			String parent;
			String name = file.getOriginalFilename();
			if(artista.getCognome() != null)
				parent = artista.getCognome().replaceAll(" ", "").toLowerCase();
			
			else
				parent = artista.getNome().replaceAll(" ", "").toLowerCase();
			
			String path = "/img/" + parent + "/" + name;
			opera.setPath(path);
			opera.setArtista(artista);
			artista.addOpera(opera);
			this.operaService.inserisci(opera);
			model.addAttribute("artista", artista);
			model.addAttribute("opere", this.operaService.getOperePerArtista(artista));
			model.addAttribute("nascita", UtilsSiw.formatDate(artista.getDataDiNascita()));
			model.addAttribute("morte", UtilsSiw.formatDate(artista.getDataDiMorte()));
			return "artista.html";
		}
		return "admin/addArtistaForm.html";
	}
	
	@RequestMapping(value="/opere", method=RequestMethod.GET)
	public String getOpere(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "opere.html";
	}
	
	@RequestMapping(value="/opera/{id}", method=RequestMethod.GET)
	public String getOpera(@PathVariable("id") Long id, Model model) {
		Opera opera = this.operaService.cercaOperaPerId(id);
		model.addAttribute("opera", opera);
		model.addAttribute("art", opera.getArtista());
		return "opera.html";
	}
	
	@RequestMapping(value="/eliminaOpera/{id}", method=RequestMethod.GET)
	public String eliminaOpera(@PathVariable("id") Long id, Model model){
		this.operaService.deleteOpera(id);
		model.addAttribute("opere", this.operaService.tutti());
		return "opere.html";
	}
	
	@RequestMapping(value="/getEditOperaForm/{id}", method=RequestMethod.GET)
	public String getEditOpera(@PathVariable("id") Long id, Model model){
		Opera opera = operaService.cercaOperaPerId(id);
		model.addAttribute("opera", opera);
		model.addAttribute("artisti", this.artistaService.tutti());
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "admin/editOperaForm.html";
	}
	
	@RequestMapping(value="/editOpera/{id}", method=RequestMethod.POST)
	public String modificaOpera(@ModelAttribute("newOpera") Opera opera, 
								@RequestParam("file") MultipartFile file,
								@PathVariable("id") Long id,
								BindingResult bindinResult,
								Model model){
		
		this.operaValidator.validate(opera, bindinResult);
		
		if(!bindinResult.hasErrors()) {
			Long idArtista = opera.getArtista().getId();
			Artista artista = artistaService.cercaArtistaPerId(idArtista);
			
			String parent;
			String name = file.getOriginalFilename();
			if(artista.getCognome() != null)
				parent = artista.getCognome().replaceAll(" ", "").toLowerCase();
			
			else
				parent = artista.getNome().replaceAll(" ", "").toLowerCase();
			
			String path = "/img/" + parent + "/" + name;
			opera.setPath(path);
			opera.setArtista(artista);
			this.operaService.updateOpera(opera, id);
			
			model.addAttribute("opere", this.operaService.tutti());

			return "opere.html";
		}
		return "admin/editOperaForm.html";
	}
	
	
	
	
	
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveOpera(@ModelAttribute("opera") Opera opera, 
								//@RequestParam("file") MultipartFile file,
								//BindingResult bindinResult,
								Model model){
		
		
		this.operaService.inserisci(opera);
		model.addAttribute("opere", this.operaService.tutti());

		return "opere.html";
	}
	
//	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
//	public String showEditOpera(@PathVariable(name = "id") Long id, Model model) {
//		model.addAttribute("opera", operaService.cercaOperaPerId(id));
//		return "/admin/editProva";
//	}
//	
//	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
//	public String edit(@PathVariable("id") Long id, @ModelAttribute("opera") Opera opera, Model model) {
//		System.out.println("EDIT" + id + opera.getNome() + "\n\n\n\n\n\n");
//		operaService.updateOpera(opera);
//		model.addAttribute("opere", this.operaService.tutti());
//		return "opere.html";
//	}
//	
	
}
