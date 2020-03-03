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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((telefon == null) ? 0 : telefon.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Korisnik other = (Korisnik) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (telefon == null) {
			if (other.telefon != null)
				return false;
		} else if (!telefon.equals(other.telefon))
			return false;
		return true;
	}
	
	
	
	

}
