package hr.java.vjezbe.entitet;

/**
 * Klasa Poslovni Korisnik koja nasljeduje klasu Korisnik
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class PoslovniKorisnik extends Korisnik {

	public String naziv;
	public String web;

	/**
	 * Konstruktor za podlasu korisnika - Poslovni Korisnik
	 * @param naziv - Naziv poslovnog korisnika 
	 * @param web - Web stranica poslovnog korisnika 
	 * @param email - Email poslovnog korisnika 
	 * @param telefon - Broj telefona korisnika 
	 */
	public PoslovniKorisnik(String naziv, String web, String email, String telefon) {
		super(email, telefon);
		this.naziv = naziv;
		this.web = web;
	}

	@Override
	public String dohvatiKontakt() {
		return String.format("Naziv tvrtke: %s , mail: %s, tel: %s, web: %s", naziv, getEmail(), getTelefon(), web);
	}
	
	/**
	 * Dohvaca naziv poslovnog korisnika 
	 * @return - Naziv
	 */
	public String getNaziv() {
		return naziv;
	}
	 /**
	  * Postavlja naziv poslovnog korisnika 
	  * @param naziv - Naziv poslovnog korisnika 
	  */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	/**
	 * Dohvaca web mjesto 
	 * @return - web mjesto
	 */
	public String getWeb() {
		return web;
	}
	
	/**
	 * Postavlja web mjesto
	 * @param web - Web mjesto
	 */
	public void setWeb(String web) {
		this.web = web;
	}

}
