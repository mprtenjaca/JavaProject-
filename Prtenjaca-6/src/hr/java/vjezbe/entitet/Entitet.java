package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * Klasa Entitet koja implementira sucelje Serializable koje je potrebno za serijalizaciju
 * @author Marko
 *
 */
public abstract class Entitet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4349402761542347960L;
	
	private Long id;

	/**
	 * Konstruktor klase Entitet
	 * @param id Id klase Entitet
	 */
	public Entitet(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
