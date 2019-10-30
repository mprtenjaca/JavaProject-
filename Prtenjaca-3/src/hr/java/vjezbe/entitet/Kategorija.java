package hr.java.vjezbe.entitet;

public class Kategorija {
	
	private String naziv;
	Artikl[] artikli;
	
	/**
	 * Konstruktor za klasu Kategorija
	 * @param naziv - Naziv kategorije
	 * @param artikli - Polje artikala odredene kategorije
	 */
	public Kategorija(String naziv, Artikl[] artikli) {
		super();
		this.naziv = naziv;
		this.artikli = artikli;
	}

	/**
	 * Dohvaca naziv kategorije 
	 * @return - Naziv kategorije 
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Postavlja naziv kategorije 
	 * @param naziv - Naziv kategorije 
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Dohvaca polje artikala kategorije
	 * @return - Polje artikala
	 */
	public Artikl[] getArtikli() {
		return artikli;
	}
	
	/**
	 * Postavlja polje artiklala kategorije 
	 * @param artikli - Polje artikala
	 */
	public void setArtikli(Artikl[] artikli) {
		this.artikli = artikli;
	}


}
