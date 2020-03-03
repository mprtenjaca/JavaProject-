package hr.java.vjezbe.iznimke;

public class BazaPodatakaException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6186263838409046177L;
	
	/**
	 * Defaultna iznimka
	 */
	public BazaPodatakaException() {
		super();
	}
	/**
	 * Iznimka za nemogucnost pristupanja bazi podataka
	 * 
	 * @param message - Poruka iznimke 
	 */
	public BazaPodatakaException(String message) {
		super(message);
	}
	
	/**
	 * Iznimka za nemogucnost pristupanja bazi podataka
	 * 
	 * @param message - Poruka iznimke 
	 * @param cause - Razlog iznimke
	 */
	public BazaPodatakaException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Iznimka za nemogucnost pristupanja bazi podataka
	 * 
	 * @param cause - Razlog iznimke
	 */
	public BazaPodatakaException(Throwable cause) {
		super(cause);
	}

}
