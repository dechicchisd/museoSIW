package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AutoreController {

	@RequestMapping(value="/autore", method=RequestMethod.GET)
	public String getAutore(Model model) {
		return "autore";
	}
}
