//package hr.java.vjezbe.glavna;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.InputMismatchException;
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import hr.java.vjezbe.entitet.Artikl;
//import hr.java.vjezbe.entitet.Automobil;
//import hr.java.vjezbe.entitet.Kategorija;
//import hr.java.vjezbe.entitet.Korisnik;
//import hr.java.vjezbe.entitet.PoslovniKorisnik;
//import hr.java.vjezbe.entitet.PrivatniKorisnik;
//import hr.java.vjezbe.entitet.Prodaja;
//import hr.java.vjezbe.entitet.Stan;
//import hr.java.vjezbe.entitet.Stanje;
//import hr.java.vjezbe.entitet.Usluga;
//
///**
// * Glavna klasa u kojoj se sve implementira
// * 
// * @author Marko Prtenjaca
// * @version 1.0
// */
//public class Glavna {
//
//	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
//
//	/**
//	 * Poziva se main funkcija
//	 * 
//	 * @param args - argumenti
//	 */
//	public static void main(String[] args) {
//
//		Scanner unos = new Scanner(System.in);
//		logger.info("Program se pocinje izvoditi");
//
//		System.out.print("Unesite broj korisnika koji želite unijeti: ");
//		Integer brojKorisnika = unosInt(unos);
//		unos.nextLine();
//
//		List<Korisnik> korisnik = new ArrayList<Korisnik>();
//		for (int i = 0; i < brojKorisnika; i++) {
//			unosKorisnika(unos, korisnik, brojKorisnika, i);
//		}
//
//		System.out.print("Unesite broj kategorija koji želite unijeti: ");
//		Integer brojKategorija = unosInt(unos);
//		unos.nextLine();
//
//		List<Artikl> artikli = new ArrayList<Artikl>();
//		List<Kategorija<Artikl>> parametrizirana = new ArrayList<Kategorija<Artikl>>(brojKategorija);
//
//		Map<Kategorija<Artikl>, List<Artikl>> mapArtikli = new HashMap<Kategorija<Artikl>, List<Artikl>>();
//		for (int i = 0; i < brojKategorija; i++) {
//			unosKategorije(unos, artikli, parametrizirana, mapArtikli, i);
//		}
//		
//		List<Artikl> artikliSort = artikli.stream()
//				.sorted(Comparator.comparing(Artikl::getNaslov).thenComparing(Artikl::getOpis))
//				.collect(Collectors.toList());
//
//		System.out.print("Unesite broj artikala koji su aktivno na prodaju: ");
//		Integer artikliNaProdaju = unosInt(unos);
//		List<Prodaja> prodaja = new ArrayList<Prodaja>();
//
//		unesiArtiklezaProdaju(unos, brojKategorija, prodaja, korisnik, parametrizirana, artikliNaProdaju, artikli);
//		ispisOdabraniArtikala(prodaja);
//		ispisPoKategoriji(brojKategorija, parametrizirana, artikliSort);
//		ispisMape(mapArtikli);
//
//		unos.close();
//
//	}
//
//	/**
//	 * Metoda koja stavlja objekt artikla u parametriziranu klasu kategorije
//	 * @param zbirka objekt parametrizirane klase
//	 * @param listaArtikala lista unesenih artikala
//	 */
//	public static void pushArtikl(Kategorija<Artikl> zbirka, List<Artikl> listaArtikala) {
//		for (Artikl a : listaArtikala) {
//			zbirka.dodajArtikl(a);
//		}
//	}
//
//	/**
//	 * Metoda koja ispisuje artikle koji su uneseni za prodaju
//	 * 
//	 * @param prodaja - Lista prodaje
//	 */
//	private static void ispisOdabraniArtikala(List<Prodaja> prodaja) {
//		LocalDate datum = LocalDate.now();
//		String formattedDate = datum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
//
//		System.out.println("Trenutno su oglasi na prodaju:");
//		System.out.println("--------------------------------------------------------------------------------");
//
//		for (int i = 0; i < prodaja.size(); i++) {
//			System.out.println(prodaja.get(i).getArtikl().tekstOglasa() + "\n"
//					+ prodaja.get(i).getKorisnik().dohvatiKontakt() + "\n" + "Datum objave: " + formattedDate);
//			System.out.println("--------------------------------------------------------------------------------");
//		}
//
//	}
//
//	/**
//	 * Ispis artikala koji su spremni za prodaju
//	 * 
//	 * @param brojKategorija  - Lista prodaje
//	 * @param parametrizirana - Lista parametrizirane klase kategorije
//	 * @param artikliSort     - Lista artikala koji su sortirani
//	 */
//	public static void ispisPoKategoriji(Integer brojKategorija, List<Kategorija<Artikl>> parametrizirana,
//			List<Artikl> artikliSort) {
//		System.out.println("Ispis po kategorijama");
//		System.out.println("--------------------------------------------------------------------------------");
//		
//		for (int i = 0; i < brojKategorija; i++) {
//			System.out.println("Kategorija: " + parametrizirana.get(i).getNaziv());
//			System.out.println("--------------------------------------------------------------------------------");
//
//			for (int j = 0; j < artikliSort.size(); j++) {
//				if (parametrizirana.get(i).getListaArtikala().contains(artikliSort.get(j))) {
//					System.out.println(artikliSort.get(j).tekstOglasa());
//					System.out.println(
//							"--------------------------------------------------------------------------------");
//				}
//			}
//		}
//
////		for (int i = 0; i < brojKategorija; i++) {
////			System.out.println("Kategorija: " + sortParam.get(i).getNaziv());
////			System.out.println("--------------------------------------------------------------------------------");
////			System.out.println("SIZE: " + sortParam.get(i).getListaArtikala().size());
////			for (int j = 0; j < sortParam.get(i).getListaArtikala().size(); j++) {
////				System.out.println(sortParam.get(i).getListaArtikala().get(j).tekstOglasa());
////				System.out.println("--------------------------------------------------------------------------------");
////			}
////		}
//	}
//
//	/**
//	 * Ispis mape
//	 * 
//	 * @param kategorija          - Lista kategorija
//	 * @param artikliPoKategoriji - Mapa kategorija i njenih artikala
//	 */
//	private static void ispisMape(Map<Kategorija<Artikl>, List<Artikl>> mapArtikli) {
//		System.out.println("--------------------------------------------------------------------------------");
//		System.out.println("Ispis mape:");
//		System.out.println("--------------------------------------------------------------------------------");
//	
//		mapArtikli.forEach((k, v) -> {
//			System.out.println("Kategorija : " + k.getNaziv());
//			System.out.println("--------------------------------------------------------------------------------");
//			mapArtikli.get(k)
//					.forEach(m -> System.out.println(m.tekstOglasa()
//					+ "\n--------------------------------------------------------------------------------"));
//		});
//	}
//
//	/**
//	 * Unos svih Integer varijabli
//	 * 
//	 * @param unos - Unos podataka s tipkovnice
//	 * @return - Vraca uneseni Integer
//	 */
//	public static Integer unosInt(Scanner unos) {
//		Integer unosBroja = 0;
//		boolean ispravno = false;
//		do {
//			try {
//				unosBroja = unos.nextInt();
//				ispravno = true;
//			} catch (InputMismatchException e) {
//				logger.error("Pogreška prilikom unosa int tipa podatka!", e);
//				System.out.println("Neispravan unos!\nUnesite ponovno: ");
//				unos.nextLine();
//			}
//		} while (ispravno == false);
//
//		return unosBroja;
//	}
//
//	/**
//	 * Unos svih BigDecimal varijabli
//	 * 
//	 * @param unos - Unos podataka s tipkovnice
//	 * @return - Vraca uneseni BigDecimal
//	 */
//	public static BigDecimal unosBD(Scanner unos) {
//		BigDecimal unosBroja = new BigDecimal(0);
//		boolean ispravno = false;
//		do {
//			try {
//				unosBroja = unos.nextBigDecimal();
//				ispravno = true;
//			} catch (InputMismatchException e) {
//				logger.error("Pogreška prilikom unosa BigDecimal tipa podatka!", e);
//				System.out.println("Neispravan unos!\nUnesite ponovno: ");
//				unos.nextLine();
//			}
//		} while (ispravno == false);
//
//		return unosBroja;
//	}
//
//	/**
//	 * Metoda za unos stanja artikla
//	 * 
//	 * @param unos - Unos podataka s tipkovnice
//	 * @return - Vraca stanje artikla
//	 */
//	private static Stanje unosStanja(Scanner unos) {
//		System.out.println("Odaberi stanje artikla");
//		for (int i = 0; i < Stanje.values().length; i++) {
//			System.out.println((i + 1) + ". " + Stanje.values()[i]);
//		}
//		Integer redniBrojStanja = null;
//
//		while (true) {
//			System.out.println("Odabir stanja artikla >>");
//			redniBrojStanja = unosInt(unos);
//			unos.nextLine();
//			if (redniBrojStanja >= 1 && redniBrojStanja <= Stanje.values().length) {
//				return Stanje.values()[redniBrojStanja - 1];
//			} else {
//				System.out.println("Neispravan unos");
//			}
//		}
//	}
//
//	/**
//	 * Odabir artikala koji su spremni za prodaju
//	 * 
//	 * @param unos             - Unos podataka s tipkovnice
//	 * @param brojKategorija   - Broj unesenih kategorija 
//	 * @param prodaja          - Lista prodaje koje sadrzi podatke o korisniku,
//	 *                         kategoriji i artiklima
//	 * @param korisnik         - Lista odabranih korisnika
//	 * @param parametrizirana  - Lista odabranih kategorija
//	 * @param artikliNaProdaju - Broj artikala koji su na prodaju
//	 * @param artikli		   - Lista artikala
//	 */
//	public static void unesiArtiklezaProdaju(Scanner unos, Integer brojKategorija, List<Prodaja> prodaja,
//			List<Korisnik> korisnik, List<Kategorija<Artikl>> parametrizirana, Integer artikliNaProdaju,
//			List<Artikl> artikli) {
//
//		for (int i = 0; i < artikliNaProdaju; i++) {
//			System.out.println("Odaberite korisnika: ");
//			for (int j = 0; j < korisnik.size(); j++) {
//				System.out.println((j + 1) + ". " + korisnik.get(j).dohvatiKontakt());
//			}
//			System.out.println("Odabir >>");
//			Integer odabirKorisnika = unosInt(unos);
//			unos.nextLine();
//
//			System.out.println("Odaberite Kategoriju: ");
//			for (int j = 0; j < brojKategorija; j++) {
//				System.out.println((j + 1) + ". " + parametrizirana.get(j).getNaziv());
//			}
//			System.out.println("Odabir >>");
//			Integer odabirKategorije = unosInt(unos);
//			unos.nextLine();
//
//			System.out.println("Odaberite Artikl: ");
//			Integer k = 0;
//			List<Artikl> listaArtikala = new ArrayList<Artikl>();
//			for (Artikl artikl : parametrizirana.get(odabirKategorije - 1).getListaArtikala()) {
//				System.out.println((k + 1) + ". " + artikl.getNaslov());
//				listaArtikala.add(artikl);
//				k++;
//			}
////			for(int j = 0; j < parametrizirana.size(); j++) {
////				System.out.println((j + 1) + ". " + parametrizirana.get(j).getNaslov());
////				listaArtikala.addAll(parametrizirana.get(j).getListaArtikala());
////			}
//			System.out.println("Odabir >>");
//			Integer odabirArtikla = unosInt(unos);
//			unos.nextLine();
//			LocalDate datum = LocalDate.now();
//
//			prodaja.add(new Prodaja(listaArtikala.get(odabirArtikla - 1), korisnik.get(odabirKorisnika - 1), datum));
//
//		}
//	}
//
//	/**
//	 * Unosi podatke o stanu
//	 * 
//	 * @param unos          - Unos podataka s tipkovnice
//	 * @param listaArtikala - Lista stana
//	 * @param j             - Index odredenog stana
//	 */
//	public static void unosStana(Scanner unos, List<Artikl> listaArtikala, int j) {
//		System.out.print("Unesite naslov " + (j + 1) + " oglasa stana: ");
//		String naslovArtikla = unos.nextLine();
//		System.out.print("Unesite opis " + (j + 1) + " oglasa stana: ");
//		String opisArtikla = unos.nextLine();
//		System.out.print("Unesite kvadraturu " + (j + 1) + " oglasa stana: ");
//		int kvadratura = unosInt(unos);
//		System.out.print("Unesite cijenu " + (j + 1) + " oglasa stana: ");
//		BigDecimal cijena = unosBD(unos);
//		unos.nextLine();
//		Stanje stanje = unosStanja(unos);
//
//		listaArtikala.add(new Stan(naslovArtikla, opisArtikla, cijena, stanje, kvadratura));
//
//	}
//
//	/**
//	 * Unosi podatke o automobilu
//	 * 
//	 * @param unos          - Unos podataka s tipkovnice
//	 * @param listaArtikala - Lista automobila
//	 * @param j             - Index odredenog automobila
//	 */
//	public static void unosAutomobila(Scanner unos, List<Artikl> listaArtikala, Integer j) {
//
//		System.out.print("Unesite naslov " + (j + 1) + " oglasa automobila: ");
//		String naslovArtikla = unos.nextLine();
//		System.out.print("Unesite opis " + (j + 1) + " oglasa automobila: ");
//		String opisArtikla = unos.nextLine();
//		System.out.print("Unesite snagu " + (j + 1) + " (u ks) oglasa automobila: ");
//		BigDecimal snagaKs = unosBD(unos);
//		unos.nextLine();
//		System.out.print("Unesite cijenu " + (j + 1) + " oglasa automobila: ");
//		BigDecimal cijena = unosBD(unos);
//		unos.nextLine();
//		Stanje stanje = unosStanja(unos);
//
//		listaArtikala.add(new Automobil(naslovArtikla, opisArtikla, snagaKs, stanje, cijena));
//
//	}
//
//	/**
//	 * Unosi podatke o usluzi
//	 * 
//	 * @param unos               - Unos podataka s tipkovnice
//	 * @param listaArtikala      - Lista usluga
//	 * @param j                  - Index odredene usluge
//	 */
//	public static void unosUsluge(Scanner unos, List<Artikl> listaArtikala, Integer j) {
//		System.out.print("Unesite naslov " + (j + 1) + " oglasa usluge: ");
//		String naslovArtikla = unos.nextLine();
//		System.out.print("Unesite opis " + (j + 1) + " oglasa usluge: ");
//		String opisArtikla = unos.nextLine();
//		System.out.print("Unesite cijenu " + (j + 1) + " oglasa usluge: ");
//		BigDecimal cijena = unosBD(unos);
//		unos.nextLine();
//		Stanje stanje = unosStanja(unos);
//
//		listaArtikala.add(new Usluga(naslovArtikla, opisArtikla, cijena, stanje));
//	}
//
//	/**
//	 * Unosi podatke o kategoriji
//	 * 
//	 * @param unos                - Unos podataka s tipkovnice
//	 * @param artikli             - Lista artikala
//	 * @param parametrizirana	  - Lista parametrizirane klase kategorije
//	 * @param mapArtikli		  - Mapa parametrizirane klase i klase artikl
//	 * @param i                   - Index odredene kategorije
//	 */
//	public static void unosKategorije(Scanner unos, List<Artikl> artikli, List<Kategorija<Artikl>> parametrizirana,
//			Map<Kategorija<Artikl>, List<Artikl>> mapArtikli, Integer i) {
//
//		System.out.print("Unesite naziv " + (i + 1) + ". kategorije: ");
//		String naziv = unos.nextLine();
//
//		System.out.print("Unesite broj artikala koji želite unijeti za unesenu kategoriju: ");
//		Integer brojArtikala = unosInt(unos);
//		unos.nextLine();
//
//		System.out.println("Unesite tip " + (i + 1) + ". artikla:\n" + "1. Usluga\n" + "2. Automobil\n" + "3. Stan");
//		Integer odabirVrsteArtikla = 0;
//		do {
//			odabirVrsteArtikla = unosInt(unos);
//			unos.nextLine();
//			if (odabirVrsteArtikla < 1 || odabirVrsteArtikla > 3) {
//				System.out.println("Pogrešan unos, odaberite ponovno!");
//			}
//		} while (odabirVrsteArtikla < 1 || odabirVrsteArtikla > 3);
//
//		List<Artikl> listaArtikala = new ArrayList<Artikl>(brojArtikala);
//		Kategorija<Artikl> pomParametrizirana = null;
//
//		Map<Kategorija<Artikl>, List<Artikl>> pom = new HashMap<Kategorija<Artikl>, List<Artikl>>();;
//		for (int j = 0; j < brojArtikala; j++) {
//			if (odabirVrsteArtikla == 1) {
//				unosUsluge(unos, listaArtikala, j);
//				pomParametrizirana = new Kategorija<Artikl>(naziv, listaArtikala.get(j).getNaslov(),
//						listaArtikala.get(j).getOpis(), listaArtikala.get(j).getCijena(), listaArtikala.get(j).getStanje());
//			}
//			if (odabirVrsteArtikla == 2) {
//				unosAutomobila(unos, listaArtikala, j);
//				pomParametrizirana = new Kategorija<Artikl>(naziv, listaArtikala.get(j).getNaslov(),
//						listaArtikala.get(j).getOpis(), listaArtikala.get(j).getCijena(), listaArtikala.get(j).getStanje());
//			}
//			if (odabirVrsteArtikla == 3) {
//				unosStana(unos, listaArtikala, j);
//				pomParametrizirana = new Kategorija<Artikl>(naziv, listaArtikala.get(j).getNaslov(),
//						listaArtikala.get(j).getOpis(), listaArtikala.get(j).getCijena(), listaArtikala.get(j).getStanje());
//			}
//
//			
//			pushArtikl(pomParametrizirana, listaArtikala);
//			artikli.add(listaArtikala.get(j));
//			
//
//			if(j == brojArtikala-1) {
//				pom.put(pomParametrizirana, listaArtikala);
//			}
//	
//		}
//		mapArtikli.putAll(pom);
//		parametrizirana.add(pomParametrizirana);
//		
//
//	}
//
//	/**
//	 * Unosi podatke o poslovnom korisniku
//	 * 
//	 * @param unos     - Unos podataka s tipkovnice
//	 * @param poslovni - lista poslovnog korisnika
//	 * @param i        - Index odredenog poslovnog korisnika
//	 */
//	public static void unosPoslovnogKorisnika(Scanner unos, List<PoslovniKorisnik> poslovni, int i) {
//
//		System.out.print("Unesite naziv " + i + ". tvrtke: ");
//		String naziv = unos.nextLine();
//
//		System.out.print("Unesite e-Mail " + i + ". tvrtke: ");
//		String email = unos.nextLine();
//
//		System.out.print("Unesite web " + i + ". tvrtke: ");
//		String web = unos.nextLine();
//
//		System.out.print("Unesite telefon " + i + ". tvrtke: ");
//		String telefon = unos.nextLine();
//
//		poslovni.add(new PoslovniKorisnik(naziv, email, web, telefon));
//
//	}
//
//	/**
//	 * Unosi podatke o privatnom korisniku
//	 * 
//	 * @param unos     - Unos podataka s tipkovnice
//	 * @param privatni - lsita privatnog koirsnika
//	 * @param i        - Index odredenog privatnog korisnika
//	 */
//	public static void unosPrivatnogKorisnika(Scanner unos, List<PrivatniKorisnik> privatni, int i) {
//
//		System.out.print("Unesite ime " + i + ". osobe: ");
//		String ime = unos.nextLine();
//
//		System.out.print("Unesite prezime " + i + ". osobe: ");
//		String prezime = unos.nextLine();
//
//		System.out.print("Unesite e-Mail " + i + ". osobe: ");
//		String email = unos.nextLine();
//
//		System.out.print("Unesite telefon " + i + ". osobe: ");
//		String telefon = unos.nextLine();
//
//		privatni.add(new PrivatniKorisnik(ime, prezime, email, telefon));
//	}
//
//	/**
//	 * unosi podatke o korisniku
//	 * 
//	 * @param unos            - Unos podataka s tipkovnice
//	 * @param korisnik.size() - Broj odabranih korisnika
//	 * @param i               - Index odredenog korisnika
//	 * @param brojKorisnika   - broj korisnika
//	 */
//	public static void unosKorisnika(Scanner unos, List<Korisnik> korisnik, int brojKorisnika, int i) {
//
//		List<PrivatniKorisnik> privatni = new ArrayList<PrivatniKorisnik>(brojKorisnika);
//		List<PoslovniKorisnik> poslovni = new ArrayList<PoslovniKorisnik>(brojKorisnika);
//
//		System.out.println("Unesite tip " + (i + 1) + ". korisnika:\n" + "1. Privatni\n" + "2. Poslovni");
//		Integer odabirVrsteKorisnika = 0;
//		do {
//			odabirVrsteKorisnika = unosInt(unos);
//			unos.nextLine();
//			if (odabirVrsteKorisnika < 1 || odabirVrsteKorisnika > 2) {
//				System.out.println("Pogrešan unos, odaberite ponovno!");
//			}
//		} while (odabirVrsteKorisnika < 1 || odabirVrsteKorisnika > 2);
//
//		if (odabirVrsteKorisnika == 1) {
//			unosPrivatnogKorisnika(unos, privatni, i + 1);
//			korisnik.addAll(privatni);
//		} else {
//			unosPoslovnogKorisnika(unos, poslovni, i + 1);
//			korisnik.addAll(poslovni);
//		}
//
//	}
//
//}
