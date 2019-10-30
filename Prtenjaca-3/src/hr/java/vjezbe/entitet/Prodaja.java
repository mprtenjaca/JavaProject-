package hr.java.vjezbe.entitet;

import java.time.LocalDate;

public class Prodaja {
	
	Artikl artikl;
	Korisnik korisnik;
	LocalDate datumObjave;
	
	/**
	 * Konstruktor za klasu Prodaja 
	 * @param artikl - Objekt klase Artikl koji sadrzi odabrane artikle za prodaju
	 * @param korisnik - Objekt klase Korisnik koji sadtzi podatke o odabranom korisniku
	 * @param datumObjave - Datum objave artikala na prodaju
	 */
	public Prodaja(Artikl artikl, Korisnik korisnik, LocalDate datumObjave) {
		super();
		this.artikl = artikl;
		this.korisnik = korisnik;
		this.datumObjave = datumObjave;
	}

	/**
	 * Dohvaca objekt artiklala
	 * @return - Objekt artikala
	 */
	public Artikl getArtikl() {
		return artikl;
	}

	/**
	 * Postavlja objekt artiklala
	 * @param artikl - Objekt artikala
	 */
	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}

	/**
	 * Dohvaca objekt korisnika
	 * @return - Objekt korisnika
	 */
	public Korisnik getKorisnik() {
		return korisnik;
	}

	/**
	 * Postavlja objekt korisnika
	 * @param korisnik -Objekt korisnika
	 */
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	/**
	 * Dohvaca datum objave oglasa
	 * @return - Datum objave 
	 */
	public LocalDate getDatumObjave() {
		return datumObjave;
	}

	/**
	 * Postavlja datum objave oglasa
	 * @param datumObjave - Datum objave oglasa
	 */
	public void setDatumObjave(LocalDate datumObjave) {
		this.datumObjave = datumObjave;
	}
	
}
