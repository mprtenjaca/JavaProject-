package hr.java.vjezbe.entitet;

/**
 * Klasa Korisnik koja sadrzi podatke o korisniku
 * 
 * @author Marko Prtenjaca
 * @version 1.0
 */
public abstract class Korisnik extends Entitet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2817314347483451116L;
	private String email;
	private String telefon;

	/**
	 * Konstruktor za klasu Korisnik
	 * 
	 * @param email   - Email korisnika
	 * @param telefon - Broj telefona korisnika
	 * @param id      - Id eniteta
	 */
	public Korisnik(Long id, String email, String telefon) {
		super(id);
		this.email = email;
		this.telefon = telefon;
	}

	/**
	 * Metoda koja sadrzi podatke o kontakt podacima
	 * 
	 * @return - Vraca string koji sadrzi kontakt podatke korisnika
	 */
	public abstract String dohvatiKontakt();

	/**
	 * Dohvaca email korisnika
	 * 
	 * @return - Email korisnika
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Postavlja email korisnika
	 * 
	 * @param email - Email korisnika
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Dohvaca telefon korisnika
	 * 
	 * @return - Telefon korisnika
	 */
	public String getTelefon() {
		return telefon;
	}

	/**
	 * Postavlja telefon korisnika
	 * 
	 * @param telefon - Telefon korisnika
	 */
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

}
