package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Usluga;

public class Glavna {

	public static void main(String[] args) {

		Scanner unos = new Scanner(System.in);

		System.out.print("Unesite broj korisnika koji želite unijeti: ");
		Integer brojKorisnika = unos.nextInt();
		unos.nextLine();

		Korisnik[] korisnik = new Korisnik[brojKorisnika];
		for (int i = 0; i < brojKorisnika; i++) {
			korisnik[i] = unosKorisnika(unos, brojKorisnika, i);
		}
		
		System.out.print("Unesite broj kategorija koji želite unijeti: ");
		Integer brojKategorija = unos.nextInt();
		unos.nextLine();

		Kategorija[] kategorija = new Kategorija[brojKategorija];
		for (int i = 0; i < brojKategorija; i++) {
			kategorija[i] = unsoKategorije(unos, i);
		}

		System.out.print("Unesite broj artikala koji su aktivno na prodaju: ");
		Integer artikliNaProdaju = unos.nextInt();
		Prodaja[] prodaja = new Prodaja[artikliNaProdaju];
		unesiArtiklezaProdaju(unos, prodaja, korisnik, kategorija);

		ispis(prodaja);
		
		unos.close();

	}

	private static void ispis(Prodaja[] prodaja) {
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

	private static void unesiArtiklezaProdaju(Scanner unos, Prodaja[] prodaja, Korisnik[] korisnik,
			Kategorija[] kategorija) {

		for (int i = 0; i < prodaja.length; i++) {
			System.out.println("Odaberite korisnika: ");
			for (int j = 0; j < korisnik.length; j++) {
				System.out.println((j + 1) + ". " + korisnik[j].dohvatiKontakt());
			}
			System.out.println("Odabir >>");
			Integer odabirKorisnika = unos.nextInt();
			unos.nextLine();

			System.out.println("Odaberite Kategoriju: ");
			for (int j = 0; j < kategorija.length; j++) {
				System.out.println((j + 1) + ". " + kategorija[j].getNaziv());
			}
			System.out.println("Odabir >>");
			Integer odabirKategorije = unos.nextInt();
			unos.nextLine();

			System.out.println("Odaberite Artikl: ");
			for (int j = 0; j < kategorija[odabirKategorije - 1].getArtikli().length; j++) {
				System.out.println((j + 1) + ". " + kategorija[odabirKategorije - 1].getArtikli()[j].getNaslov());
			}
			System.out.println("Odabir >>");
			Integer odabirArtikla = unos.nextInt();
			unos.nextLine();

			LocalDate datum = LocalDate.now();
			prodaja[i] = new Prodaja(kategorija[odabirKategorije - 1].getArtikli()[odabirArtikla - 1],
					korisnik[odabirKorisnika - 1], datum);
		}
	}

	private static Automobil unosAutomobila(Scanner unos, Integer j) {
		System.out.print("Unesite naslov " + (j + 1) + " oglasa automobila: ");
		String naslovArtikla = unos.nextLine();
		System.out.print("Unesite opis " + (j + 1) + " oglasa automobila: ");
		String opisArtikla = unos.nextLine();
		System.out.print("Unesite snagu " + (j + 1) + " (u ks) oglasa automobila: ");
		BigDecimal snagaKs = unos.nextBigDecimal();
		unos.nextLine();
		System.out.print("Unesite cijenu " + (j + 1) + " oglasa automobila: ");
		String pomCijena = unos.nextLine();
		BigDecimal cijenaArtikla = new BigDecimal(pomCijena.replaceAll(",", "."));

		Automobil noviAutomobil = new Automobil(naslovArtikla, opisArtikla, snagaKs, cijenaArtikla);

		return noviAutomobil;
	}

	private static Usluga unosUsluge(Scanner unos, Integer j) {
		System.out.print("Unesite naslov " + (j + 1) + " oglasa usluge: ");
		String naslovArtikla = unos.nextLine();
		System.out.print("Unesite opis " + (j + 1) + " oglasa usluge: ");
		String opisArtikla = unos.nextLine();
		System.out.print("Unesite cijenu " + (j + 1) + " oglasa usluge: ");
		String pomCijena = unos.nextLine();
		BigDecimal cijenaArtikla = new BigDecimal(pomCijena.replaceAll(",", "."));

		Usluga novaUsluga = new Usluga(naslovArtikla, opisArtikla, cijenaArtikla);

		return novaUsluga;
	}

	private static Kategorija unsoKategorije(Scanner unos, Integer i) {

		System.out.print("Unesite naziv " + (i + 1) + ". kategorije: ");
		String naziv = unos.nextLine();

		System.out.print("Unesite broj artikala koji želite unijeti za unesenu kategoriju: ");
		Integer brojArtikala = unos.nextInt();
		unos.nextLine();

		System.out.println("Unesite tip " + (i + 1) + ". artikla:\n" + "1. Usluga\n" + "2. Automobil");
		Integer odabirVrsteArtikla = 0;
		do {
			odabirVrsteArtikla = unos.nextInt();
			unos.nextLine();
			if (odabirVrsteArtikla < 1 || odabirVrsteArtikla > 2) {
				System.out.println("Pogrešan unos, odaberite ponovno!");
			}
		} while (odabirVrsteArtikla < 1 || odabirVrsteArtikla > 2);

		Usluga[] usluga = new Usluga[brojArtikala];
		Automobil[] automobil = new Automobil[brojArtikala];
		Artikl[] poljeArtikala = new Artikl[brojArtikala];

		for (int j = 0; j < brojArtikala; j++) {
			if (odabirVrsteArtikla == 1) {
				usluga[j] = unosUsluge(unos, j);
				poljeArtikala[j] = usluga[j];

			} else {
				automobil[j] = unosAutomobila(unos, j);
				poljeArtikala[j] = automobil[j];
			}
		}

		Kategorija novaKategorija = new Kategorija(naziv, poljeArtikala);

		return novaKategorija;
	}

	private static PoslovniKorisnik unosPoslovnogKorisnika(Scanner unos, int i) {

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

	private static PrivatniKorisnik unosPrivatnogKorisnika(Scanner unos, int i) {

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

	private static Korisnik unosKorisnika(Scanner unos, int brojKorisnika, int i) {

		PrivatniKorisnik[] privatni = new PrivatniKorisnik[brojKorisnika];
		PoslovniKorisnik[] poslovni = new PoslovniKorisnik[brojKorisnika];
		Korisnik noviKorisnik;

		System.out.println("Unesite tip " + (i + 1) + ". korisnika:\n" + "1. Privatni\n" + "2. Poslovni");
		Integer odabirVrsteKorisnika = 0;
		do {
			odabirVrsteKorisnika = unos.nextInt();
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
