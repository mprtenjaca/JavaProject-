package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Klasa Usluga koja nasljeduje klasu Artikl
 * 
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class Usluga extends Artikl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8418379135128057504L;

	/**
	 * Konsturktor za podklasu klase Artikl - Usluga
	 * 
	 * @param naslov - Naslov usluge
	 * @param opis   - Opis usluge
	 * @param cijena - Cijena usluge
	 * @param stanje - Stanje artikla
	 * @param id     - Id eniteta
	 */
	public Usluga(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String tekstOglasa() {

		return String.format(
				"Naslov usluge: %s\n" + "Opis usluge: %s\n" + "Stanje usluge: " + getStanje() + "\nCijena usluge: %.0f",
				getNaslov(), getOpis(), getCijena());
	}

}
