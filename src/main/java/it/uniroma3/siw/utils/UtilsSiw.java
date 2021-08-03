package it.uniroma3.siw.utils;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.service.CollezioneService;

public class UtilsSiw {
	
	@Autowired
	CollezioneService collService;

	public static String formatDate(LocalDate date) {
		int day = date.getDayOfMonth();
		int monthNum = date.getMonthValue();
		int year = date.getYear();
		
		String month = "";
		switch(monthNum) {
		
		case 1: 
			month = "Gennaio";
			break;
			
		case 2: 
			month = "Febbraio";
			break;
			
		case 3: 
			month = "Marzo";
			break;
			
		case 4: 
			month = "Aprile";
			break;
			
		case 5: 
			month = "Maggio";
			break;
			
		case 6: 
			month = "Giugno";
			break;
			
		case 7: 
			month = "Luglio";
			break;
			
		case 8: 
			month = "Agosto";
			break;
			
		case 9: 
			month = "Settembre";
			break;
			
		case 10: 
			month = "Ottobre";
			break;
			
		case 11: 
			month = "Novembre";
			break;
			
		case 12: 
			month = "Dicembre";
		}
		
		String stringDate = day + " " + month + " " + year + ",";
		
		return stringDate;
	}
	
	
	public static Collezione[] randomSelection(List<Collezione> collezioni) {
		Collezione[] randomColl = new Collezione[2];
		
		Random rand = new Random();
		for(int i=0; i<randomColl.length; i++) {
			randomColl[i]=collezioni.get(rand.nextInt(collezioni.size()));
		}
		
		return randomColl;
		
	}
}
