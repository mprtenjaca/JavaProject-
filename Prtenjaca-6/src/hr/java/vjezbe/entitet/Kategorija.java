package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa kategorija za svaki artikl
 * 
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class Kategorija<T extends Artikl> {

	private String naziv;
	public List<T> listaArtikala;

	/**
	 * Konstruktor paremetrizirane klase Kategorija koja nasljeduje apstraktnu klasu
	 * Artikl
	 * 
	 * @param id    - Id eniteta
	 * @param naziv - Naziv kategorije
	 */
	public Kategorija(Long id, String naziv) {
		this.naziv = naziv;
		listaArtikala = new ArrayList<T>();
	}

	/**
	 * Dodaje nove artikle u listu
	 * 
	 * @param dodaniArtikl - Dodana lista artikala
	 */
	public void dodajArtikl(T dodaniArtikl) {
		listaArtikala.add(dodaniArtikl);
	}

	/**
	 * Dohvaca odabranu listu artikala
	 * 
	 * @param index - Redni broj liste
	 * @return - Lista
	 */
	public T dohvatiArtikl(int index) {
		return listaArtikala.get(index);
	}

	/**
	 * Dohvaca listu artikala
	 * 
	 * @return - Lista artikala
	 */
	public List<T> getListaArtikala() {
		return listaArtikala;
	}

	/**
	 * Postavlja novu listu artikala
	 * 
	 * @param listaArtikala - Lista artikala
	 */
	public void setListaArtikala(List<T> listaArtikala) {
		this.listaArtikala = listaArtikala;
	}

	/**
	 * Dohvaca naziv kategorije
	 * 
	 * @return - Naziv kategorije
	 */
	public String getNaziv() {
		return naziv;
	}

	/**
	 * Postavlja naziv kategorije
	 * 
	 * @param naziv - Naziv kategorije
	 */
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

}
