package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * Klasa Stan koja nasljeduje klasu Artikl i implemetira sucelje Nekretnina
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class Stan extends Artikl implements Nekretnina {

	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
	public int kvadratura;

	/**
	 * Konstuktor za podklasu klase Artikl - Stan
	 * 
	 * @param naslov     - Naslov oglasa stana
	 * @param opis       - Opis oglasa stana
	 * @param cijena     - Cijena stana
	 * @param kvadratura - Kvadratura stana
	 */
	public Stan(String naslov, String opis, BigDecimal cijena, int kvadratura) {
		super(naslov, opis, cijena);
		this.kvadratura = kvadratura;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Dohvaca kvadrauru stana
	 * 
	 * @return - Kvadratura stana
	 */
	public int getKvadratura() {
		return kvadratura;
	}

	/**
	 * Postavlja kvadraturu stana
	 * 
	 * @param kvadratura - Kvadratura stana
	 */
	public void setKvadratura(int kvadratura) {
		this.kvadratura = kvadratura;
	}

	@Override
	public String tekstOglasa() {

		String poruka;
		try {
			BigDecimal porez = izracunajPorez(getCijena());

			poruka = "Naslov nekretnine: " + getNaslov() + "\nOpis nekretnine: " + getOpis()
					+ "\nKvadratura nekretnine: " + getKvadratura() + "\nPorez na nekretnine: " + porez
					+ "\nCijena nekretnine: " + getCijena();

		} catch (CijenaJePreniskaException e) {
			logger.error(e.getMessage(), e);
			poruka = "Naslov nekretnine: " + getNaslov() + "\nOpis nekretnine: " + getOpis()
					+ "\nKvadratura nekretnine: " + getKvadratura() + "\nPorez na nekretnine: " + e.getMessage()
					+ "\nCijena nekretnine: " + getCijena();
		}

		return poruka;

	}

}
