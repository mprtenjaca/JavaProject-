package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Sucelje Vozilo u kojem se pozivaju funkcije za obradu vozila
 * @author Marko Prtenjaca
 * @version 1.0
 */
public interface Vozilo {

	/**
	 * Metoda za pretvaranje konjskih snaga u kilowatte(kw)
	 * 
	 * @param ks - Unesena konjska snaga za auto
	 * @return - Vraca pretvorene kilowatte
	 */
	default BigDecimal izracunajKw(BigDecimal ks) {
		BigDecimal kw = ks.multiply(new BigDecimal(0.73549875));
		return kw;
	}

	/**
	 * Metoda za izracunavanje grupe osiguranja na osnovu konjskih snaga ili kw
	 * 
	 * @return - Vraca broj izracunate grupe
	 * @throws NemoguceOdreditiGrupuOsiguranjaException - Iznimka koja se baca ako
	 *                                                  je broj kw prevelik
	 */
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException;

	/**
	 * Metoda za izracunavanje cijene osiguranja na osnovu grupe osiguranja
	 * 
	 * @return - Vraca cijenu osiguranja auta
	 * @throws NemoguceOdreditiGrupuOsiguranjaException - Iznimka koja se baca ako
	 *                                                  je broj kw prevelik uza
	 *                                                  izracun cijene
	 */
	default BigDecimal izracunajCijenuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {

		BigDecimal tempGrupa;
		int tempResult = 0;

		tempGrupa = izracunajGrupuOsiguranja();

		int value = tempGrupa.intValue();

		switch (value) {

		case 1:
			tempResult = 2000;
			break;
		case 2:
			tempResult = 2500;
			break;
		case 3:
			tempResult = 2700;
			break;
		case 4:
			tempResult = 3000;
			break;
		case 5:
			tempResult = 3500;
			break;
		default:
			break;
		}

		BigDecimal result = new BigDecimal(tempResult);

		return result;

	}

}
