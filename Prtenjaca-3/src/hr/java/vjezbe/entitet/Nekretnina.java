package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * Sucelje Nekretnina koja sluzi za izracunavanje poreza na nkretninu 
 * @author Marko Prtenjaca
 * @version 1.0
 */
public interface Nekretnina {
	
	static final Logger logger = LoggerFactory.getLogger(Nekretnina.class);

	/**
	 * Racuna porez od 3% na osnovu unesene cijene
	 * @param cijena - Unesena cijena 
	 * @return - Vraca izracun poreza
	 */
	default BigDecimal izracunajPorez (BigDecimal cijena){
		BigDecimal porez = new BigDecimal(3);
		BigDecimal zbroj = null;
		if(cijena.doubleValue() >= 10000) {
			zbroj = porez.divide(new BigDecimal(100)).multiply(cijena);
		}else {
			logger.error("Pogreška prilikom određivanja iznosa poreza!");
			throw new CijenaJePreniskaException("Cijena ne smije biti manja od 10000kn");
		}
		
		return zbroj;
	}

}
