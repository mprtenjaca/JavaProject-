package hr.java.vjezbe.entitet;

public class PoslovniKorisnik extends Korisnik {

	public String naziv;
	public String web;

	public PoslovniKorisnik(String naziv, String web, String email, String telefon) {
		super(email, telefon);
		this.naziv = naziv;
		this.web = web;
	}

	@Override
	public String dohvatiKontakt() {
		return String.format("Naziv tvrtke: %s , mail: %s, tel: %s, web: %s", naziv, getEmail(), getTelefon(), web);
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

}
