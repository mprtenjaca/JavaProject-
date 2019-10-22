package hr.java.vjezbe.entitet;

public class PrivatniKorisnik extends Korisnik {

	public String ime;
	public String prezime;

	public PrivatniKorisnik(String ime, String prezime, String email, String telefon) {
		super(email, telefon);
		this.ime = ime;
		this.prezime = prezime;
	}

	@Override
	public String dohvatiKontakt() {
		return String.format("Osobni podaci prodavatelja: %s %s, mail: %s, tel: %s", ime, prezime, getEmail(), getTelefon());
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

}
