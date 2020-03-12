package hr.java.vjezbe.glavna;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Kategorija;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;

public class Glavna {

	public static void main(String[] args) {

		Scanner unos = new Scanner(System.in);
		

		System.out.print("Unesite broj korisnika koji želite unijeti: ");
		Integer brojKorisnika = unos.nextInt();
		unos.nextLine();

		Korisnik[] korisnik = new Korisnik[brojKorisnika];
		for (int i = 0; i < brojKorisnika; i++) {
			korisnik[i] = unosKorisnika(unos, i + 1);
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
		unos.nextLine();

		Prodaja[] prodaja = new Prodaja[artikliNaProdaju];
		//unesiArtiklezaProdaju(unos, prodaja, korisnik, kategorija);
		for(int i = 0; i < artikliNaProdaju; i++) {
			prodaja[i] = artikli(unos, prodaja, korisnik, kategorija);
		}

		ispis(prodaja);
		
		unos.close();

	}

	private static void ispis(Prodaja[] prodaja) {
		System.out.println("Trenutno su oglasi na prodaju:");
		LocalDate datum = LocalDate.now();
		String stringDatum = datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		
		for (int i = 0; i < prodaja.length; i++) {
			
			System.out.println("Naslov: " + prodaja[i].getArtikl().getNaslov() + "\n" 
							+ "Opis: " + prodaja[i].getArtikl().getOpis() + "\n"
							+ "Cijena: " + prodaja[i].getArtikl().getCijena() + "\n"
							+ "Datum objave: " + stringDatum);


			System.out.println("Kontakt podaci: " + prodaja[i].getKorisnik().getIme() + " "
					+ prodaja[i].getKorisnik().getPrezime() + ", mail:  "
					+ prodaja[i].getKorisnik().getEmail() + ", tel: "
					+ prodaja[i].getKorisnik().getTelefon());
		}
		
	}
	
	private static Prodaja artikli(Scanner unos, Prodaja[] prodaja, Korisnik[] korisnik, Kategorija[] kategorija) {
		
		System.out.println("Odaberite korisnika: ");
		for (int j = 0; j < korisnik.length; j++) {
			System.out.println((j + 1) + ". " + korisnik[j].getIme() + " " + korisnik[j].getPrezime());
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
		
		Prodaja novaProdaja = new Prodaja(kategorija[odabirKategorije - 1].getArtikli()[odabirArtikla - 1], korisnik[odabirKorisnika - 1], datum);
	
	return novaProdaja;
}

//	private static void unesiArtiklezaProdaju(Scanner unos, Prodaja[] prodaja, Korisnik[] korisnik, Kategorija[] kategorija) {
//		
//		for (int i = 0; i < prodaja.length; i++) {
//			System.out.println("Odaberite korisnika: ");
//			for (int j = 0; j < korisnik.length; j++) {
//				System.out.println((j + 1) + ". " + korisnik[j].getIme() + " " + korisnik[j].getPrezime());
//			}
//			System.out.println("Odabir >>");
//			Integer odabirKorisnika = unos.nextInt();
//			unos.nextLine();
//
//			System.out.println("Odaberite Kategoriju: ");
//			for (int j = 0; j < kategorija.length; j++) {
//				System.out.println((j + 1) + ". " + kategorija[j].getNaziv());
//			}
//			System.out.println("Odabir >>");
//			Integer odabirKategorije = unos.nextInt();
//			unos.nextLine();
//
//			System.out.println("Odaberite Artikl: ");
//			for (int j = 0; j < kategorija[odabirKategorije - 1].getArtikli().length; j++) {
//				System.out.println((j + 1) + ". " + kategorija[odabirKategorije - 1].getArtikli()[j].getNaslov());
//			}
//			System.out.println("Odabir >>");
//			Integer odabirArtikla = unos.nextInt();
//			unos.nextLine();
//
//			LocalDate datum = LocalDate.now();
//			prodaja[i] = new Prodaja(kategorija[odabirKategorije - 1].getArtikli()[odabirArtikla - 1],
//					korisnik[odabirKorisnika - 1], datum);
//		}
//		
//	}

	private static Artikl unosArtikla(Scanner unos, Integer j) {
		System.out.print("Unesite naslov " + (j + 1) + " oglasa artikla: ");
		String naslovArtikla = unos.nextLine();
		System.out.print("Unesite opis " + (j + 1) + " oglasa artikla: ");
		String opisArtikla = unos.nextLine();
		System.out.print("Unesite cijenu " + (j + 1) + " oglasa artikla: ");
		String pomCijena = unos.nextLine();	
		BigDecimal cijenaArtikla = new BigDecimal(pomCijena.replaceAll(",", "."));
		
		Artikl noviArtikl = new Artikl(naslovArtikla, opisArtikla, cijenaArtikla);

		return noviArtikl;
	}

	private static Kategorija unsoKategorije(Scanner unos, Integer i) {

		System.out.print("Unesite naziv " + (i + 1) + ". kategorije: ");
		String naziv = unos.nextLine();

		System.out.print("Unesite broj artikala koji želite unijeti za unesenu kategoriju: ");
		Integer brojArtikala = unos.nextInt();
		unos.nextLine();

		Artikl[] poljeArtikala = new Artikl[brojArtikala];

		for (int j = 0; j < brojArtikala; j++) {
			poljeArtikala[j] = unosArtikla(unos, j);
		}

		Kategorija novaKategorija = new Kategorija(naziv, poljeArtikala);

		return novaKategorija;
	}

	private static Korisnik unosKorisnika(Scanner unos, int i) {
		System.out.print("Unesite ime " + i + ". korisnika: ");
		String ime = unos.nextLine();

		System.out.print("Unesite prezime " + i + ". korisnika: ");
		String prezime = unos.nextLine();

		System.out.print("Unesite e-Mail " + i + ". korisnika: ");
		String email = unos.nextLine();

		System.out.print("Unesite telefon " + i + ". korisnika: ");
		String telefon = unos.nextLine();

		Korisnik noviKorisnik = new Korisnik(ime, prezime, email, telefon);

		return noviKorisnik;
	}

}
