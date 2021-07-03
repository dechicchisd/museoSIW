package it.uniroma3.siw.controller;



import org.apache.commons.text.WordUtils;
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

import it.uniroma3.siw.controller.validator.ArtistaValidator;
import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.service.ArtistaService;
import it.uniroma3.siw.service.OperaService;
import it.uniroma3.siw.utils.UtilsSiw;

@Controller
public class ArtistaController {
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private OperaService operaService;
	
	@Autowired
	private ArtistaValidator artistaValidator;
	
	@RequestMapping(value="/artisti", method=RequestMethod.GET)
	public String getArtisti(Model model) {
		model.addAttribute("artisti", this.artistaService.tutti());
		return "artisti.html";
	}
	
	@RequestMapping(value="/artista/{id}", method=RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long id, Model model) {
		Artista artista = this.artistaService.cercaArtistaPerId(id);
		
		model.addAttribute("artista", artista);
		model.addAttribute("opere", this.operaService.getOperePerArtista(artista));
		model.addAttribute("nascita", UtilsSiw.formatDate(artista.getDataDiNascita()));
		model.addAttribute("morte", UtilsSiw.formatDate(artista.getDataDiMorte()));
		return "artista.html";
	}
	
	@RequestMapping(value="/newArtista", method=RequestMethod.POST)
	public String newArtista(@ModelAttribute("artista") Artista artista,
							 @RequestParam("file") MultipartFile file,
							 BindingResult bindingResult,
							 Model model) {
		artista.setNome(WordUtils.capitalize(artista.getNome()));
		artista.setCognome(WordUtils.capitalize(artista.getCognome()));
		
		this.artistaValidator.validate(artista, bindingResult);
		
		
		if(!bindingResult.hasErrors()) {
			
			
			String parent;
			String name="";
			
			if(file!=null)
				name = file.getOriginalFilename();
			
			if(artista.getCognome() != null)
				parent = artista.getCognome().replaceAll(" ", "").toLowerCase();
			
			else
				parent = artista.getNome().replaceAll(" ", "").toLowerCase();
			
			String path = "/img/" + parent + "/" + name;
			artista.setPath(path);
			
			this.artistaService.inserisci(artista);
			model.addAttribute("artisti", this.artistaService.tutti());
			return "artisti.html";
		}
		return "/admin/addArtistaForm.html";
	}
	
	@RequestMapping(value="/addArtistaForm", method=RequestMethod.GET)
	public String getAddArtistaForm(Model model) {
		model.addAttribute("artista", new Artista());
		return "admin/addArtistaForm.html";
	}
	
	@RequestMapping(value="/eliminaArtista/{id}", method=RequestMethod.GET)
	public String eliminaArtista(@PathVariable("id") Long id, Model model){
		this.artistaService.deleteArtista(id);
		model.addAttribute("artisti", this.artistaService.tutti());
		return "artisti";
	}
	
//	@RequestMapping(value="/modificaArtista/{id}", method=RequestMethod.GET)
//	public String modificaArtista(@PathVariable("id") Long id, Model model, HttpServletRequest request){
//		Artista artista = this.artistaService.cercaArtistaPerId(id);
//		model.addAttribute("artista", artista);
//		
//		return "admin/addArtistaForm";
//	}
	
}