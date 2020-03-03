package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Klasa Automobil koja sadrzi podatke o automobililma, nasljeduje klasu artikl i implementira sucelje Vozilo
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class Automobil extends Artikl implements Vozilo {
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	public BigDecimal snagaKs;

	/**
	 * Konstruktor za klasu automobil
	 * @param naslov - Naziv automobila
	 * @param opis - Opis automobila
	 * @param snagaKs - Snaga automobila u konjskim snagama
	 * @param cijena - Cijena automobila 
	 */
	public Automobil(String naslov, String opis, BigDecimal snagaKs, BigDecimal cijena) {
		super(naslov, opis, cijena);
		this.snagaKs = snagaKs;
	}

	@Override
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {
		double tempSnagaKs = izracunajKw(snagaKs).doubleValue();
		
		int izracunGrupe = 0;

		if (tempSnagaKs < 40) {
			izracunGrupe = 1;
		}
		if (tempSnagaKs >= 40 && tempSnagaKs < 47) {
			izracunGrupe = 1;
		}
		if (tempSnagaKs >= 47 && tempSnagaKs < 62) {
			izracunGrupe = 2;
		}
		if (tempSnagaKs >= 62 && tempSnagaKs < 80) {
			izracunGrupe = 3;
		}
		if (tempSnagaKs >= 80 && tempSnagaKs < 110) {
			izracunGrupe = 4;
		}
		if (tempSnagaKs >= 110 && tempSnagaKs < 170) {
			izracunGrupe = 5;
		}
		if (tempSnagaKs >= 170) {
			izracunGrupe = 6;
			throw new NemoguceOdreditiGrupuOsiguranjaException("Previše kw, ne mogu odrediti grupu osiguranja.");
		}

		BigDecimal result = new BigDecimal(izracunGrupe);

		return result;
	}

	/**
	 * Vraca kolicinu konjskih snaga
	 * @return - Konjska snaga
	 */
	public BigDecimal getSnagaKs() {
		return snagaKs;
	}
	/**
	 * Postavlja konjske snage
	 * @param snagaKs - Konjske snage
	 */
	public void setSnagaKs(BigDecimal snagaKs) {
		this.snagaKs = snagaKs;
	}
	
	@Override
	public String tekstOglasa() {
		String oglas;
		try {
			@SuppressWarnings("unused")
			BigDecimal grupa = izracunajGrupuOsiguranja();
			
			oglas = "Naslov automobila: " + getNaslov() + "\nOpis automobila: " + getOpis() + "\nSnaga automobila: "
					+ getSnagaKs() + "\nIzracun osiguranja automobila: " + izracunajCijenuOsiguranja()
					+ "\nCijena automobila: " + getCijena();
		} catch (NemoguceOdreditiGrupuOsiguranjaException e) {
			logger.error("Pogreška prilikom određivanja cijene osiguranja!");
			logger.error(e.getMessage(), e);
			oglas = "Naslov automobila: " + getNaslov() + "\nOpis automobila: " + getOpis() + "\nSnaga automobila: "
					+ getSnagaKs() + "\nIzracun osiguranja automobila: " + e.getMessage()
					+ "\nCijena automobila: " + getCijena();
			
		}
		return oglas;

	}


}
