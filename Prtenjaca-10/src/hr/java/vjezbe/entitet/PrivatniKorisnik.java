package hr.java.vjezbe.entitet;

/**
 * Klasa Prtivatni Korisnik koji nasljeduje klasu Korisnik
 * 
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class PrivatniKorisnik extends Korisnik {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9082869873594885659L;
	public String ime;
	public String prezime;

	/**
	 * Konstruktor za podklasu Korisnika - Privatni Korisnik
	 * 
	 * @param ime     - Ime korisnika
	 * @param prezime - Prezime korisnika
	 * @param email   - Email korisnika
	 * @param telefon - Telefon korisnika
	 * @param id      - Id eniteta
	 */
	public PrivatniKorisnik(Long id, String ime, String prezime, String email, String telefon) {
		super(id, email, telefon);
		this.ime = ime;
		this.prezime = prezime;
	}

	@Override
	public String dohvatiKontakt() {
		return String.format("Osobni podaci prodavatelja: %s %s, mail: %s, tel: %s", ime, prezime, getEmail(),
				getTelefon());
	}

	/**
	 * Dohvaca ime korisnika
	 * 
	 * @return - Ime korisnika
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Postavlja ime korisnika
	 * 
	 * @param ime - Ime korisnika
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	/**
	 * Dohvaca prezime korisnika
	 * 
	 * @return - prezime korisnika
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Postavlja prezime korisnika
	 * 
	 * @param prezime - prezime korisnika
	 */
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	@Override
	public String toString() {
		return getIme() + ", " + getPrezime() + ", email:" + getEmail() + ", tel:" + getTelefon();
	}
	
	

}
