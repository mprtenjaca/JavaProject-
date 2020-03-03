package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Klasa artikl koja sadrzi podatke o artiklima koji se prodaju
 * 
 * @author Marko Prtenjaca
 * @version 1.0
 */
public abstract class Artikl extends Entitet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3072856779337823701L;
	private String naslov;
	private String opis;
	private BigDecimal cijena;
	private Stanje stanje;

	/**
	 * Konstruktor za klasu Artikl
	 * 
	 * @param naslov - Naslov artikla
	 * @param opis   - Opis artikla
	 * @param cijena - Cijena artikla
	 * @param stanje - Stanje artikla
	 * @param id      - Id eniteta
	 */
	public Artikl(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id);
		this.naslov = naslov;
		this.opis = opis;
		this.cijena = cijena;
		this.stanje = stanje;
	}
	
	/**
	 * Metoda u kojoj se sastavlja izgled oglasa
	 * 
	 * @return - Vraca string koji sadrzi tekst oglasa
	 */
	public abstract String tekstOglasa();

	/**
	 * Vraca naslov artikla
	 * 
	 * @return String naslova
	 */
	public String getNaslov() {
		return naslov;
	}

	/**
	 *Hash metoda artikla
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cijena == null) ? 0 : cijena.hashCode());
		result = prime * result + ((naslov == null) ? 0 : naslov.hashCode());
		result = prime * result + ((opis == null) ? 0 : opis.hashCode());
		result = prime * result + ((stanje == null) ? 0 : stanje.hashCode());
		return result;
	}

	/**
	 * Equals metoda artikla
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artikl other = (Artikl) obj;
		if (cijena == null) {
			if (other.cijena != null)
				return false;
		} else if (!cijena.equals(other.cijena))
			return false;
		if (naslov == null) {
			if (other.naslov != null)
				return false;
		} else if (!naslov.equals(other.naslov))
			return false;
		if (opis == null) {
			if (other.opis != null)
				return false;
		} else if (!opis.equals(other.opis))
			return false;
		if (stanje != other.stanje)
			return false;
		return true;
	}

	/**
	 * Postavlja ime naslova
	 * 
	 * @param naslov - Postavlja naslov
	 */
	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	/**
	 * Vraca opis artikla
	 * 
	 * @return - Opis artikla
	 */
	public String getOpis() {
		return opis;
	}

	/**
	 * Postavlja opis artikla
	 * 
	 * @param opis - Opis artikla
	 */
	public void setOpis(String opis) {
		this.opis = opis;
	}

	/**
	 * Vraca cijenu artikla
	 * 
	 * @return - Cijena artikla
	 */
	public BigDecimal getCijena() {
		return cijena;
	}

	/**
	 * Postavlja cijenu artikla
	 * 
	 * @param cijena - Cijena artikla
	 */
	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}

	/**
	 * Dohvaca stanje artikla
	 * @return - Stanje artikla
	 */
	public Stanje getStanje() {
		return stanje;
	}

	/**
	 * Postavlja stanje artikla
	 * @param stanje - Stanje artikla
	 */
	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
	}

}
