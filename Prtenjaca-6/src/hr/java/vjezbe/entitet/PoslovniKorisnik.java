package hr.java.vjezbe.entitet;

/**
 * Klasa Poslovni Korisnik koja nasljeduje klasu Korisnik
 * 
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class PoslovniKorisnik extends Korisnik {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2500137901211440312L;
	public String naziv;
	public String web;

	/**
	 * Konstruktor za podlasu korisnika - Poslovni Korisnik
	 * 
	 * @param naziv   - Naziv poslovnog korisnika
	 * @param web     - Web stranica poslovnog korisnika
	 * @param email   - Email poslovnog korisnika
	 * @param telefon - Broj telefona korisnika
	 * @param id      - Id eniteta
	 */
	public PoslovniKorisnik(Long id, String naziv, String web, String email, String telefon) {
		super(id, email, telefon);
		this.naziv = naziv;
		this.web = web;
	}

	@Override
	public String dohvatiKontakt() {
		return String.format("Naziv tvrtke: %s , mail: %s, tel: %s, web: %s", naziv, getEmail(), getTelefon(), web);
	}

	/**
	 * Dohvaca naziv poslovnog korisnika
	 * 
	 * @return - Naziv
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Postavlja naziv poslovnog korisnika
	 * 
	 * @param naziv - Naziv poslovnog korisnika
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Dohvaca web mjesto
	 * 
	 * @return - web mjesto
	 */
	public String getWeb() {
		return web;
	}

	/**
	 * Postavlja web mjesto
	 * 
	 * @param web - Web mjesto
	 */
	public void setWeb(String web) {
		this.web = web;
	}

}
