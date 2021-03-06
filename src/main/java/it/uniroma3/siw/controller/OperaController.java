package it.uniroma3.siw.controller;

import java.util.Collections;
import java.util.List;

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
import it.uniroma3.siw.model.Collezione;
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
	
	@RequestMapping(value="/getSave", method=RequestMethod.GET)
	public String getAddOperaForm(Model model) {
		model.addAttribute("opera", new Opera());

		List<Artista> artisti = this.artistaService.tutti();
		Collections.sort(artisti);
		model.addAttribute("artisti", artisti);
		
		List<Collezione> collezioni = this.collezioneService.tutti();
		Collections.sort(collezioni);
		model.addAttribute("collezioni", collezioni);
		
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
		
		List<Artista> artisti = this.artistaService.tutti();
		Collections.sort(artisti);
		model.addAttribute("artisti", artisti);
		
		List<Collezione> collezioni = this.collezioneService.tutti();
		Collections.sort(collezioni);
		model.addAttribute("collezioni", collezioni);
		
		return "admin/addOperaForm.html";

	}
	
	@RequestMapping(value="/opere", method=RequestMethod.GET)
	public String getOpere(Model model) {
		List<Opera> opere = this.operaService.tutti();
		
		Collections.sort(opere);
		
		model.addAttribute("opere", opere);
		
		return "opere.html";
	}
	
	@RequestMapping(value="/opera/{id}", method=RequestMethod.GET)
	public String getOpera(@PathVariable("id") Long id, Model model) {
		Opera opera = this.operaService.cercaOperaPerId(id);
		model.addAttribute("opera", opera);
		model.addAttribute("artista", opera.getArtista());
		return "opera.html";
	}
	
	@RequestMapping(value="/eliminaOpera/{id}", method=RequestMethod.GET)
	public String eliminaOpera(@PathVariable("id") Long id, Model model){
		this.operaService.deleteOpera(id);
		List<Opera> opere = this.operaService.tutti();
		
		Collections.sort(opere);

		model.addAttribute("opere", opere);
		return "opere.html";
	}
	
	@RequestMapping(value="/getEditOperaForm/{id}", method=RequestMethod.GET)
	public String getEditOpera(@PathVariable("id") Long id, Model model){
		Opera opera = operaService.cercaOperaPerId(id);
		model.addAttribute("opera", opera);
		
		List<Artista> artisti = this.artistaService.tutti();
		Collections.sort(artisti);
		model.addAttribute("artisti", artisti);

		List<Collezione> collezioni = this.collezioneService.tutti();
		Collections.sort(collezioni);
		model.addAttribute("collezioni", collezioni);
		
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
			this.operaService.deleteOpera(id);
			this.operaService.inserisci(opera);
			
			List<Opera> opere = this.operaService.tutti();
			
			Collections.sort(opere);
			
			model.addAttribute("opere", opere);

			return "opere.html";
		}
		return "admin/editOperaForm.html";
	}
	
}