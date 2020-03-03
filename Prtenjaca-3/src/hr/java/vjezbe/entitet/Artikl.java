package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Klasa artikl koja sadrzi podatke o artiklima koji se prodaju
 * @author Marko Prtenjaca
 * @version 1.0
 */
public abstract class Artikl {
	
	private String naslov;
	private String opis;
	private BigDecimal cijena;
	
	/**
	 * Konstruktor za klasu Artikl
	 * @param naslov - Naslov artikla
	 * @param opis - Opis artikla
	 * @param cijena - Cijena artikla
	 */
	public Artikl(String naslov, String opis, BigDecimal cijena) {
		super();
		this.naslov = naslov;
		this.opis = opis;
		this.cijena = cijena;
	}
	
	/**
	 * Metoda u kojoj se sastavlja izgled oglasa
	 * @return - Vraca string koji sadrzi tekst oglasa
	 */
	public abstract String tekstOglasa();

	/**
	 * Vraca naslov artikla
	 * @return String naslova
	 */
	public String getNaslov() {
		return naslov;
	}

	/**
	 * Postavlja ime naslova
	 * @param naslov - Postavlja naslov
	 */
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	/**
	 * Vraca opis artikla 
	 * @return - Opis artikla
	 */
	public String getOpis() {
		return opis;
	}

	/**
	 * Postavlja opis artikla
	 * @param opis - Opis artikla
	 */
	public void setOpis(String opis) {
		this.opis = opis;
	}

	/**
	 * Vraca cijenu artikla
	 * @return - Cijena artikla
	 */
	public BigDecimal getCijena() {
		return cijena;
	}

	/**
	 * Postavlja cijenu artikla
	 * @param cijena - Cijena artikla
	 */
	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}
	
	
	
	

}
