package fr.exorth.util;

import java.text.DecimalFormat;
import java.util.Date;

public class PSUtil {
	
	
	public static double getTaxe(double prix, int days) {
		
		DecimalFormat df = new DecimalFormat("#.00");
		double taxe; 
		double percent;
		
		switch(days){
			
		case 3:
			percent= 0.1;
			break;
		case 5:
			percent= 0.15;
			break;
		case 7:
			percent= 0.2;
			break;
		default:
			percent= 0.15;
			break;
		}
		
		
		
		taxe = Double.parseDouble(df.format(prix*percent).replace(",", "."));
		
		return taxe;
	}
	
	public static Date getExpirationDate(int days) {
		
		Date current = new Date();
		Date expiration = new Date();
		long currentm = current.getTime();
		long expirationm = currentm + (days*24*60*60*1000);
		expiration.setTime(expirationm);
		
		return expiration;
	}

}
