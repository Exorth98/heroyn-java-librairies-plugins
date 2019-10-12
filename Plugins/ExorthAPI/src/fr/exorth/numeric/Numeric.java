package fr.exorth.numeric;

public class Numeric {
	
	public static boolean isInteger(String nbr) {
		
		try {	
			
			Integer.parseInt(nbr);			
		}
		
		catch(NumberFormatException nfe){
			
			return false;
		}
		
		return true;
		
	}
	
	public static boolean isDouble(String nbr) {
		
		try {	
			
			Double.parseDouble(nbr);			
		}
		
		catch(NumberFormatException nfe){
			
			return false;
		}
		
		return true;
		
	}
	
	public static boolean isFloat(String nbr) {
		
		try {	
			
			Float.parseFloat(nbr);			
		}
		
		catch(NumberFormatException nfe){
			
			return false;
		}
		
		return true;
		
	}
	
	

}
