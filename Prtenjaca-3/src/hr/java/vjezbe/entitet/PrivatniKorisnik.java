package hr.java.vjezbe.entitet;

public class PrivatniKorisnik extends Korisnik {

	public String ime;
	public String prezime;

	/**
	 * Konstruktor za podklasu Korisnika - Privatni Korisnik
	 * @param ime - Ime korisnika
	 * @param prezime - Prezime korisnika
	 * @param email - Email korisnika
	 * @param telefon - Telefon korisnika 
	 */
	public PrivatniKorisnik(String ime, String prezime, String email, String telefon) {
		super(email, telefon);
		this.ime = ime;
		this.prezime = prezime;
	}

	@Override
	public String dohvatiKontakt() {
		return String.format("Osobni podaci prodavatelja: %s %s, mail: %s, tel: %s", ime, prezime, getEmail(), getTelefon());
	}

	/**
	 * Dohvaca ime korisnika 
	 * @return - Ime korisnika 
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * Postavlja ime korisnika 
	 * @param ime - Ime korisnika 
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	/**
	 * Dohvaca prezime korisnika 
	 * @return - prezime korisnika 
	 */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Postavlja prezime korisnika 
	 * @param prezime - prezime korisnika 
	 */
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

}
