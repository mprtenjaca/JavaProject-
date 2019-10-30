package hr.java.vjezbe.glavna;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Usluga;

/**
 * Glavna klasa u kojoj se sve implementira 
 * @author Marko Prtenjaca
 *
 */
public class Glavna {
	
	
	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	/**
	 * Poziva se main funkcija
	 * @param args - argumenti
	 */
	public static void main(String[] args) {

		Scanner unos = new Scanner(System.in);
		logger.info("Program se pocinje izvoditi");

		System.out.print("Unesite broj korisnika koji želite unijeti: ");
		Integer brojKorisnika = unosInt(unos);
		unos.nextLine();

		Korisnik[] korisnik = new Korisnik[brojKorisnika];
		for (int i = 0; i < brojKorisnika; i++) {
			korisnik[i] = unosKorisnika(unos, brojKorisnika, i);
		}
		
		System.out.print("Unesite broj kategorija koji želite unijeti: ");
		Integer brojKategorija = unosInt(unos);
		unos.nextLine();

		Kategorija[] kategorija = new Kategorija[brojKategorija];
		for (int i = 0; i < brojKategorija; i++) {
			kategorija[i] = unsoKategorije(unos, i);
		}

		System.out.print("Unesite broj artikala koji su aktivno na prodaju: ");
		Integer artikliNaProdaju = unosInt(unos);
		Prodaja[] prodaja = new Prodaja[artikliNaProdaju];
		unesiArtiklezaProdaju(unos, prodaja, korisnik, kategorija);

		ispis(prodaja);
		
		unos.close();

	}
	
	

	/**
	 * Ispis artikala koji su spremni za prodaju
	 * @param prodaja - Vraca podatke o artiklima za prodaju
	 */
	public static void ispis(Prodaja[] prodaja) {
		System.out.println("Trenutno su oglasi na prodaju:");
		System.out.println("--------------------------------------------------------------------------------");
		
		LocalDate datum = LocalDate.now();
		String formattedDate = datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		
		for (int i = 0; i < prodaja.length; i++) {
			System.out.println(prodaja[i].getArtikl().tekstOglasa() 
					+ "\n" + prodaja[i].getKorisnik().dohvatiKontakt()
					+ "\n" + "Datum objave: " 
					+ formattedDate);
			System.out.println("--------------------------------------------------------------------------------");
		}
	}
	
	/**
	 * Unos svih Integer varijabli
	 * @param unos - Unos podataka s tipkovnice
	 * @return - Vraca uneseni Integer
	 */
	public static Integer unosInt(Scanner unos) {
		Integer unosBroja = 0;
		boolean ispravno = false;
		do {
			try {
				unosBroja = unos.nextInt();
				ispravno=true;
			}catch(InputMismatchException e) {
				logger.error("Pogreška prilikom unosa int tipa podatka!", e);
				System.out.println("Neispravan unos!\nUnesite ponovno: ");
				unos.nextLine();
			}
		}while(ispravno == false);
		
		return unosBroja;
	}
	
	/**
	 * Unos svih BigDecimal varijabli
	 * @param unos - Unos podataka s tipkovnice
	 * @return - Vraca uneseni BigDecimal
	 */
	public static BigDecimal unosBD(Scanner unos) {
		BigDecimal unosBroja = new BigDecimal(0);
		boolean ispravno = false;
		do {
			try {
				unosBroja = unos.nextBigDecimal();
				ispravno=true;
			}catch(InputMismatchException e) {
				logger.error("Pogreška prilikom unosa BigDecimal tipa podatka!", e);
				System.out.println("Neispravan unos!\nUnesite ponovno: ");
			}
		}while(ispravno == false);
		
		return unosBroja;
	}

	/**
	 * Odabir artikala koji su spremni za prodaju
	 * @param unos - Unos podataka s tipkovnice
	 * @param prodaja - polje prodaje koje sadrzi podatke o korisniku, kategoriji i artiklima
	 * @param korisnik - polje odabranih korisnika
	 * @param kategorija - polje odabranih kategorija
	 */
	public static void unesiArtiklezaProdaju(Scanner unos, Prodaja[] prodaja, Korisnik[] korisnik,
			Kategorija[] kategorija) {

		for (int i = 0; i < prodaja.length; i++) {
			System.out.println("Odaberite korisnika: ");
			for (int j = 0; j < korisnik.length; j++) {
				System.out.println((j + 1) + ". " + korisnik[j].dohvatiKontakt());
			}
			System.out.println("Odabir >>");
			Integer odabirKorisnika = unosInt(unos);
			unos.nextLine();

			System.out.println("Odaberite Kategoriju: ");
			for (int j = 0; j < kategorija.length; j++) {
				System.out.println((j + 1) + ". " + kategorija[j].getNaziv());
			}
			System.out.println("Odabir >>");
			Integer odabirKategorije = unosInt(unos);
			unos.nextLine();

			System.out.println("Odaberite Artikl: ");
			for (int j = 0; j < kategorija[odabirKategorije - 1].getArtikli().length; j++) {
				System.out.println((j + 1) + ". " + kategorija[odabirKategorije - 1].getArtikli()[j].getNaslov());
			}
			System.out.println("Odabir >>");
			Integer odabirArtikla = unosInt(unos);
			unos.nextLine();

			LocalDate datum = LocalDate.now();
			prodaja[i] = new Prodaja(kategorija[odabirKategorije - 1].getArtikli()[odabirArtikla - 1],
					korisnik[odabirKorisnika - 1], datum);
		}
	}
	
	/**
	 * Unosi podatke o stanu
	 * @param unos - Unos podataka s tipkovnice
	 * @param j - Index odredenog stana
	 * @return - Vraca novi stan odredenog indexa
	 */
	public static Stan unosStana(Scanner unos, int j) {
		System.out.print("Unesite naslov " + (j + 1) + " oglasa stana: ");
		String naslovArtikla = unos.nextLine();
		System.out.print("Unesite opis " + (j + 1) + " oglasa stana: ");
		String opisArtikla = unos.nextLine();
		System.out.print("Unesite kvadraturu " + (j + 1) + " oglasa stana: ");
		int kvadratura = unosInt(unos);
		System.out.print("Unesite cijenu " + (j + 1) + " oglasa stana: ");
		BigDecimal cijena = unosBD(unos);
		unos.nextLine();
		
		Stan noviStan = new Stan(naslovArtikla, opisArtikla, cijena, kvadratura);

		return noviStan;
	}
	
	/**
	 * Unosi podatke o automobilu
	 * @param unos - Unos podataka s tipkovnice
	 * @param j - Index odredenog automobila
	 * @return - Vraca novog studenta odredenog indexa
	 */
	public static Automobil unosAutomobila(Scanner unos, Integer j) {
		System.out.print("Unesite naslov " + (j + 1) + " oglasa automobila: ");
		String naslovArtikla = unos.nextLine();
		System.out.print("Unesite opis " + (j + 1) + " oglasa automobila: ");
		String opisArtikla = unos.nextLine();
		System.out.print("Unesite snagu " + (j + 1) + " (u ks) oglasa automobila: ");
		BigDecimal snagaKs = unosBD(unos);
		unos.nextLine();
		System.out.print("Unesite cijenu " + (j + 1) + " oglasa automobila: ");
		BigDecimal cijena = unosBD(unos);
		unos.nextLine();

		Automobil noviAutomobil = new Automobil(naslovArtikla, opisArtikla, snagaKs, cijena);

		return noviAutomobil;
	}

	/**
	 * Unosi podatke o usluzi
	 * @param unos - Unos podataka s tipkovnice
	 * @param j - Index odredene usluge
	 * @return - Vraca novu uslugu odredenog indexa
	 */
	public static Usluga unosUsluge(Scanner unos, Integer j) {
		System.out.print("Unesite naslov " + (j + 1) + " oglasa usluge: ");
		String naslovArtikla = unos.nextLine();
		System.out.print("Unesite opis " + (j + 1) + " oglasa usluge: ");
		String opisArtikla = unos.nextLine();
		System.out.print("Unesite cijenu " + (j + 1) + " oglasa usluge: ");
		BigDecimal cijena = unosBD(unos);
		unos.nextLine();
		
		Usluga novaUsluga = new Usluga(naslovArtikla, opisArtikla, cijena);

		return novaUsluga;
	}

	/**
	 * Unosi podatke o kategoriji
	 * @param unos - Unos podataka s tipkovnice
	 * @param i - Index odredene kategorije
	 * @return - Vraca novu kategoriju odredenog indexa
	 */
	public static Kategorija unsoKategorije(Scanner unos, Integer i) {

		System.out.print("Unesite naziv " + (i + 1) + ". kategorije: ");
		String naziv = unos.nextLine();

		System.out.print("Unesite broj artikala koji želite unijeti za unesenu kategoriju: ");
		Integer brojArtikala = unosInt(unos);
		unos.nextLine();

		System.out.println("Unesite tip " + (i + 1) + ". artikla:\n" + "1. Usluga\n" + "2. Automobil\n" + "3. Stan");
		Integer odabirVrsteArtikla = 0;
		do {
			odabirVrsteArtikla = unosInt(unos);
			unos.nextLine();
			if (odabirVrsteArtikla < 1 || odabirVrsteArtikla > 3) {
				System.out.println("Pogrešan unos, odaberite ponovno!");
			}
		} while (odabirVrsteArtikla < 1 || odabirVrsteArtikla > 3);

		Usluga[] usluga = new Usluga[brojArtikala];
		Automobil[] automobil = new Automobil[brojArtikala];
		Stan[] stan = new Stan[brojArtikala];
		Artikl[] poljeArtikala = new Artikl[brojArtikala];

		for (int j = 0; j < brojArtikala; j++) {
			if (odabirVrsteArtikla == 1) {
				usluga[j] = unosUsluge(unos, j);
				poljeArtikala[j] = usluga[j];

			}if(odabirVrsteArtikla == 2) {
				automobil[j] = unosAutomobila(unos, j);
				poljeArtikala[j] = automobil[j];
			}else {
				stan[j] = unosStana(unos, j);
				poljeArtikala[j] = stan[j];
			}
		}

		Kategorija novaKategorija = new Kategorija(naziv, poljeArtikala);

		return novaKategorija;
	}

	/**
	 * Unosi podatke o poslovnom korisniku
	 * @param unos - Unos podataka s tipkovnice
	 * @param i - Index odredenog poslovnog korisnika
	 * @return - Vraca novog poslovnog korisnika odredenog indexa
	 */
	public static PoslovniKorisnik unosPoslovnogKorisnika(Scanner unos, int i) {

		System.out.print("Unesite naziv " + i + ". tvrtke: ");
		String naziv = unos.nextLine();

		System.out.print("Unesite e-Mail " + i + ". tvrtke: ");
		String email = unos.nextLine();

		System.out.print("Unesite web " + i + ". tvrtke: ");
		String web = unos.nextLine();

		System.out.print("Unesite telefon " + i + ". tvrtke: ");
		String telefon = unos.nextLine();

		PoslovniKorisnik noviPoslovniKorisnik = new PoslovniKorisnik(naziv, email, web, telefon);

		return noviPoslovniKorisnik;
	}

	/**
	 * Unosi podatke o privatnom korisniku
	 * @param unos - Unos podataka s tipkovnice
	 * @param i - Index odredenog privatnog korisnika
	 * @return - Vraca novog privatnog korisnika odredenog indexa
	 */
	public static PrivatniKorisnik unosPrivatnogKorisnika(Scanner unos, int i) {

		System.out.print("Unesite ime " + i + ". osobe: ");
		String ime = unos.nextLine();

		System.out.print("Unesite prezime " + i + ". osobe: ");
		String prezime = unos.nextLine();

		System.out.print("Unesite e-Mail " + i + ". osobe: ");
		String email = unos.nextLine();

		System.out.print("Unesite telefon " + i + ". osobe: ");
		String telefon = unos.nextLine();

		PrivatniKorisnik noviPrivatniKorisnik = new PrivatniKorisnik(ime, prezime, email, telefon);

		return noviPrivatniKorisnik;
	}

	/**
	 * unosi podatke o korisniku
	 * @param unos - Unos podataka s tipkovnice
	 * @param brojKorisnika - Broj odabranih korisnika
	 * @param i - Index odredenog korisnika
	 * @return - Vraca novog korisnika
	 */
	public static Korisnik unosKorisnika(Scanner unos, int brojKorisnika, int i) {

		PrivatniKorisnik[] privatni = new PrivatniKorisnik[brojKorisnika];
		PoslovniKorisnik[] poslovni = new PoslovniKorisnik[brojKorisnika];
		Korisnik noviKorisnik;

		System.out.println("Unesite tip " + (i + 1) + ". korisnika:\n" + "1. Privatni\n" + "2. Poslovni");
		Integer odabirVrsteKorisnika = 0;
		do {
			odabirVrsteKorisnika = unosInt(unos);
			unos.nextLine();
			if (odabirVrsteKorisnika < 1 || odabirVrsteKorisnika > 2) {
				System.out.println("Pogrešan unos, odaberite ponovno!");
			}
		} while (odabirVrsteKorisnika < 1 || odabirVrsteKorisnika > 2);

		if (odabirVrsteKorisnika == 1) {
			privatni[i] = unosPrivatnogKorisnika(unos, i + 1);
			noviKorisnik = privatni[i];
		} else {
			poslovni[i] = unosPoslovnogKorisnika(unos, i + 1);
			noviKorisnik = poslovni[i];
		}

		return noviKorisnik;
	}

}
