package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Klasa Usluga koja nasljeduje klasu Artikl
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class Usluga extends Artikl {

	/**
	 * Konsturktor za podklasu klase Artikl - Usluga
	 * @param naslov - Naslov usluge
	 * @param opis - Opis usluge
	 * @param cijena - Cijena usluge 
	 */
	public Usluga(String naslov, String opis, BigDecimal cijena) {
		super(naslov, opis, cijena);
	}

	@Override
	public String tekstOglasa() {
		
		return String.format("Naslov usluge: %s\n"
				+ "Opis usluge: %s\n"
				+ "Cijena usluge: %.0f", getNaslov(), getOpis(), getCijena());
	}

}
