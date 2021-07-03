package it.uniroma3.siw.utils;

import java.time.LocalDate;

public class UtilsSiw {

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
}
