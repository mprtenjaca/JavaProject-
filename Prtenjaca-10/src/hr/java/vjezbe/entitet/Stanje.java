package hr.java.vjezbe.entitet;

/**
 * Enum stanja artikla
 * @author Marko
 *
 */
public enum Stanje {
	
	//NOVO, IZVRSNO, RABLJENO, NESIPRAVNO;
	novo(1, "Novo jos nije raspakirano, garancija 2 godine!"), 
	izvrsno(2, "Izvrsno stanje, nije korišteno, kao novo!"),
	rabljeno(3, "Korišteno nekoliko puta, sve ispravno."), 
	neispravno(4, "U dijelova ili komplet prodaja.");

	private Integer kod;
	private String opis;

	private Stanje(Integer kod, String opis) {
		this.kod = kod;
		this.opis = opis;
	}

	public Integer getKod() {
		return kod;
	}

	public String getOpis() {
		return opis;
	}

}
