package hr.java.vjezbe.iznimke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *klasa iznimka ako je nemoguce odrediti prosjek studenata zbog nedovoljne ocjene
 * 
 * @author Marko Prtenjaca
 *
 */

public class NemoguceOdreditiGrupuOsiguranjaException extends Exception {
	
	
	private static final Logger logger = LoggerFactory.getLogger(NemoguceOdreditiGrupuOsiguranjaException.class);

	/**
	 * Serijalni broj verzije UID
	 */
	private static final long serialVersionUID = -7129354463190839198L;
	
	/**
	 * Iznimka za nemogucnost odredivanja osiguranja automobila
	 */
	public NemoguceOdreditiGrupuOsiguranjaException() {
		super();
		logger.info("Nema grupe osiguranja");
	}
	/**
	 * Iznimka za nemogucnost odredivanja osiguranja automobila
	 * 
	 * @param message - Poruka iznimke 
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String message) {
		super(message);
	}
	
	/**
	 * Iznimka za nemogucnost odredivanja osiguranja automobila
	 * 
	 * @param message - Poruka iznimke 
	 * @param cause - Razlog iznimke
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Iznimka za nemogucnost odredivanja osiguranja automobila
	 * 
	 * @param cause - Razlog iznimke
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(Throwable cause) {
		super(cause);
	}
}
