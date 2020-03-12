package hr.java.vjezbe.glavna;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;

/**
 * 
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class GlavnaDatoteke {

	public static final String SERIALIZATION_FILE_NAME = "Prodaja.dat";
	private static final Logger logger = LoggerFactory.getLogger(GlavnaDatoteke.class);

	/**
	 * Glavna main metoda koja sadrzi svu implementaciju
	 * 
	 * 
	 * @param args Polje argumenata komandne linije za aplikaciju
	 */
	public static void main(String[] args) {
		logger.info("Program se pocinje izvoditi");
		// long startTime = System.nanoTime();

//		Path korisnici = Path.of("dat/Korisnik.txt");
//		Path kategorije = Path.of("dat/Kategorija.txt");
//		Path artikli = Path.of("dat/Artikli.txt");
//		Path prodaja = Path.of("dat/Prodaja.txt");
//
//		
//
//		String datKorisnici = " ";
//		String datKategorije = " ";
//		String datArtikli = " ";
//		String datProdaja = " ";
//		
//		try {
//		datKorisnici = Files.readString(korisnici);
//		datKategorije = Files.readString(kategorije);
//		datArtikli = Files.readString(artikli);
//		datProdaja = Files.readString(prodaja);
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
//
//	String korPodaci[] = datKorisnici.split("\\r?\\n");
//	String katPodaci[] = datKategorije.split("\\r?\\n");
//	String artPodaci[] = datArtikli.split("\\r?\\n");
//	String prodPodaci[] = datProdaja.split("\\r?\\n");
//
//	for (int i = 0; i < korPodaci.length; i++) {
//		podaciKorisnika.add(korPodaci[i]);
//	}
//	for (int i = 0; i < katPodaci.length; i++) {
//		podaciKategorija.add(katPodaci[i]);
//	}
//	for (int i = 0; i < artPodaci.length; i++) {
//		podaciArtikala.add(artPodaci[i]);
//	}
//	for (int i = 0; i < prodPodaci.length; i++) {
//		podaciProdaje.add(prodPodaci[i]);
//	}

		File korisniciFile = new File("dat/Korisnik.txt");
		File kategorijeFile = new File("dat/Kategorija.txt");
		File artikliFile = new File("dat/Artikli.txt");
		File prodajaFile = new File("dat/Prodaja.txt");

		List<String> podaciKorisnika = new ArrayList<String>();
		List<String> podaciKategorija = new ArrayList<String>();
		List<String> podaciArtikala = new ArrayList<String>();
		List<String> podaciProdaje = new ArrayList<String>();

		datoteke(podaciKorisnika, korisniciFile);
		datoteke(podaciKategorija, kategorijeFile);
		datoteke(podaciArtikala, artikliFile);
		datoteke(podaciProdaje, prodajaFile);

		// Korisnik
		System.out.println("Ucitavanje korisnika...");
		List<Korisnik> korisnik = new ArrayList<Korisnik>();
		ucitavanjeKorisnika(podaciKorisnika, korisnik);

		// Artikl
		System.out.println("Ucitavanje artikala...");
		List<Artikl> artiklList = new ArrayList<Artikl>();
		dohvatiArtikle(artiklList, podaciArtikala);

		// Kategorija artikl
		System.out.println("Ucitavanje kategorija...");
		List<Kategorija<Artikl>> kategorijaArtiklList = new ArrayList<Kategorija<Artikl>>();
		Map<Kategorija<Artikl>, List<Artikl>> mapa = new HashMap<Kategorija<Artikl>, List<Artikl>>();
		dohvatiKategorije(podaciKategorija, artiklList, kategorijaArtiklList, mapa);

		// Prodaja
		System.out.println("Ucitavanje prodaje...");
		List<Prodaja> prodajaList = new ArrayList<>();
		dohvatiProdaju(prodajaList, podaciProdaje, artiklList, korisnik);

		// Ispis
		ispisProdaje(prodajaList);
		ispisPoKategoriji(kategorijaArtiklList, artiklList);

		// Serijalizacija prodaje
		srijalizacija(prodajaList);

//		for (Artikl a : artiklList) {
//			System.out.println(a.getNaslov());
//		}

		

		 List<Stan> listItems = artiklList.stream().filter(p -> p instanceof Stan).map(sc -> (Stan) sc)
				.collect(Collectors.toList());
		 
//		 for (Artikl a : listItems) {
//				System.out.println(a.tekstOglasa());
//			}

//		mapa.forEach((k, v) -> {
//			System.out.println("Kategorija : " + k.getNaziv());
//			System.out.println("--------------------------------------------------------------------------------");
//			mapa.get(k).forEach(m -> System.out.println(m.tekstOglasa()
//					+ "\n--------------------------------------------------------------------------------"));
//		});

//		long endTime = System.nanoTime();
//		long totalTime = endTime - startTime;
//		System.out.println(totalTime / 1000000.0);
	}

	/**
	 * Cita file te ga dodaje svaku liniju u ArrayList
	 * 
	 * @param podaciList String lista u koju se upisiva file
	 * @param file       Put do txt datoteke
	 */
	private static void datoteke(List<String> podaciList, File file) {

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				podaciList.add(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* METODE */

	/**
	 * Ispis artikala sortiranih po kategoriji
	 * 
	 * @param kategorijaArtiklList Lista parametrizirane klase
	 * @param artikl               Lista klase Artikl
	 */
	private static void ispisPoKategoriji(List<Kategorija<Artikl>> kategorijaArtiklList, List<Artikl> artikl) {

		List<Artikl> artikliSort = artikl.stream()
				.sorted(Comparator.comparing(Artikl::getNaslov).thenComparing(Artikl::getOpis))
				.collect(Collectors.toList());

		System.out.println("Ispis po kategorijama");
		System.out.println("--------------------------------------------------------------------------------");

		for (int i = 0; i < kategorijaArtiklList.size(); i++) {
			System.out.println("Kategorija: " + kategorijaArtiklList.get(i).getNaziv());
			System.out.println("--------------------------------------------------------------------------------");

			for (int j = 0; j < artikliSort.size(); j++) {
				if (kategorijaArtiklList.get(i).getListaArtikala().contains(artikliSort.get(j))) {
					System.out.println(artikliSort.get(j).tekstOglasa());
					System.out.println(
							"--------------------------------------------------------------------------------");
				}
			}
		}

	}

	/**
	 * Ispisuje odabranu prodaju
	 * 
	 * @param prodajaList Lista klase Podaja
	 */
	private static void ispisProdaje(List<Prodaja> prodajaList) {

		System.out.println("Trenutno su oglasi na prodaju:");
		System.out.println("--------------------------------------------------------------------------------");

		for (int i = 0; i < prodajaList.size(); i++) {
			System.out.println(prodajaList.get(i).getArtikl().tekstOglasa() + "\n"
					+ prodajaList.get(i).getKorisnik().dohvatiKontakt() + "\n" + "Datum objave: "
					+ prodajaList.get(i).getDatumObjave());
			System.out.println("--------------------------------------------------------------------------------");
		}

	}

	/**
	 * Dodaje listu stringova u listu klase Prodaja te radi novi objkt Prodaje
	 * 
	 * @param prodajaList   Lista klase Prodaja
	 * @param podaciProdaje Lista stringova koja sadrzi podatke iz txt datoteke
	 *                      prodaje
	 * @param artiklList    Lista klase Artikl
	 * @param korisnik      Lista klase Korisnik
	 */
	private static void dohvatiProdaju(List<Prodaja> prodajaList, List<String> podaciProdaje, List<Artikl> artiklList,
			List<Korisnik> korisnik) {

		for (int i = 0; i < podaciProdaje.size(); i++) {
			if (i % 4 == 0) {
				Long id = Long.parseLong(podaciProdaje.get(i));
				Integer odabirArtikla = Integer.parseInt(podaciProdaje.get(i + 1));
				Integer odabirKorisnika = Integer.parseInt(podaciProdaje.get(i + 2));
				String datumObjave = podaciProdaje.get(i + 3);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				LocalDate datum = LocalDate.parse(datumObjave, formatter);

				Prodaja prodaja = new Prodaja(id, artiklList.get(odabirArtikla - 1), korisnik.get(odabirKorisnika - 1),
						datum);

				prodajaList.add(prodaja);
			}
		}

	}

	/**
	 * Ucitava podatke za odabir stana te radi novi objekt Stana
	 * 
	 * @param podaciArtikala Lista koja sadrzi podatke iz datoteke artikala
	 * @param pomArtikli     Pomocna lista klase Artikl koja sprema novi artikl
	 * @param j              Iteroator kroz listu
	 */
	private static void ucitavanjeStana(List<String> podaciArtikala, List<Artikl> pomArtikli, int j) {

		Long id = Long.parseLong(podaciArtikala.get(j + 1));
		String naslovArtikla = podaciArtikala.get(j + 2);
		String opisArtikla = podaciArtikala.get(j + 3);
		String pomKvadratura = podaciArtikala.get(j + 4);
		Integer kvadratura = Integer.parseInt(pomKvadratura);
		String pomCijena = podaciArtikala.get(j + 5);
		BigDecimal cijena = new BigDecimal(pomCijena);
		Integer pomStanje = 3;
		Stanje stanje = Stanje.values()[pomStanje - 1];

		Stan stan = new Stan(id, naslovArtikla, opisArtikla, cijena, stanje, kvadratura);

		pomArtikli.add(stan);

	}

	/**
	 * Ucitava podatke za odabir automobila te radi novi objekt Automobila
	 * 
	 * @param podaciArtikala Lista koja sadrzi podatke iz datoteke artikala
	 * @param pomArtikli     Pomocna lista klase Artikl koja sprema novi artikl
	 * @param j              Iterator kroz listu
	 */
	private static void ucitavanjeAutomobila(List<String> podaciArtikala, List<Artikl> pomArtikli, int j) {

		Long id = Long.parseLong(podaciArtikala.get(j + 1));
		String naslovArtikla = podaciArtikala.get(j + 2);
		String opisArtikla = podaciArtikala.get(j + 3);
		String pomSnaga = podaciArtikala.get(j + 4);
		BigDecimal snagaKs = new BigDecimal(pomSnaga);
		String pomCijena = podaciArtikala.get(j + 5);
		BigDecimal cijena = new BigDecimal(pomCijena);
		Integer pomStanje = Integer.parseInt(podaciArtikala.get(j + 6));
		Stanje stanje = Stanje.values()[pomStanje - 1];

		Automobil auto = new Automobil(id, naslovArtikla, opisArtikla, snagaKs, stanje, cijena);

		pomArtikli.add(auto);
	}

	/**
	 * Ucitava podatke za odabir usluge te radi novi objekt Usluge
	 * 
	 * @param podaciArtikala Lista koja sadrzi podatke iz datoteke artikala
	 * @param pomArtikli     Pomocna lista klase Artikl koja sprema novi artikl
	 * @param j              Iterator kroz listu
	 */
	private static void ucitavanjeUsluge(List<String> podaciArtikala, List<Artikl> pomArtikli, int j) {

		Long id = Long.parseLong(podaciArtikala.get(j + 1));
		String naslovArtikla = podaciArtikala.get(j + 2);
		String opisArtikla = podaciArtikala.get(j + 3);
		String pomCijena = podaciArtikala.get(j + 4);
		BigDecimal cijena = new BigDecimal(pomCijena);
		Integer pomStanje = Integer.parseInt(podaciArtikala.get(j + 5));
		Stanje stanje = Stanje.values()[pomStanje - 1];

		Usluga usluga = new Usluga(id, naslovArtikla, opisArtikla, cijena, stanje);

		pomArtikli.add(usluga);

	}

	/**
	 * Dodaje listu datoteke Kategorija u listu klase Kategorija te radi novi objekt
	 * Kategorija
	 * 
	 * @param podaciKategorija     - Lista koja sadrzi podatke datoteke Kategorija
	 * @param artikl               Lista klase Artikl
	 * @param parametriziranaLista Lista klase Kategorija
	 * @param mapa                 Mapa klase Kategorija i Artikl
	 */
	private static void dohvatiKategorije(List<String> podaciKategorija, List<Artikl> artikl,
			List<Kategorija<Artikl>> parametriziranaLista, Map<Kategorija<Artikl>, List<Artikl>> mapa) {

		List<Integer> listaIdArtikala = new ArrayList<>();

		List<Integer> brojacArtikalaZaKategoriju = new ArrayList<>();
		for (int j = 0; j < podaciKategorija.size(); j++) {

			if (j % 3 == 0) {
				Long id = Long.parseLong(podaciKategorija.get(j));
				String nazivKategorije = podaciKategorija.get(j + 1);
				String idSkup = podaciKategorija.get(j + 2);
				idSkup = idSkup.replaceAll("\\s", "");
				brojacArtikalaZaKategoriju.add(idSkup.length());
				for (int k = 0; k < idSkup.length(); k++) {
					Integer pom = idSkup.charAt(k) - '0';
					listaIdArtikala.add(pom);
				}
				Kategorija<Artikl> pomParam = new Kategorija<Artikl>(id, nazivKategorije);
				parametriziranaLista.add(pomParam);
			}
		}

		List<Artikl> subListaArtikala = new ArrayList<Artikl>();
		int sum = 0;
		for (int j = 0; j < brojacArtikalaZaKategoriju.size(); j++) {

			if (j == 0) {
				subListaArtikala = artikl.subList(0, brojacArtikalaZaKategoriju.get(j));
			} else {
				subListaArtikala = artikl.subList(sum += brojacArtikalaZaKategoriju.get(j - 1),
						sum += brojacArtikalaZaKategoriju.get(j));
			}
			pushArtikli(parametriziranaLista.get(j), subListaArtikala);
			mapa.put(parametriziranaLista.get(j), subListaArtikala);
		}
	}

	/**
	 * Cita podatke it liste koja sadrzi podatke iz datoteke Artikl te odabire
	 * artikl iscitan it liste datoteke
	 * 
	 * @param artikl         Lista klase Artikl
	 * @param podaciArtikala Lista koja sadrzi podatke datoteke Artikl
	 */
	private static void dohvatiArtikle(List<Artikl> artikl, List<String> podaciArtikala) {

		List<Artikl> pomArtikli = new ArrayList<Artikl>();

		for (int i = 0; i < podaciArtikala.size(); i++) {
			Integer odabirArtikla = 0;
			if (i % 7 == 0) {

				odabirArtikla = Integer.parseInt(podaciArtikala.get(i));

				if (odabirArtikla == 1) {
					ucitavanjeUsluge(podaciArtikala, pomArtikli, i);
				}
				if (odabirArtikla == 2) {
					ucitavanjeAutomobila(podaciArtikala, pomArtikli, i);
				}
				if (odabirArtikla == 3) {
					ucitavanjeStana(podaciArtikala, pomArtikli, i);
				}

			}

		}
		artikl.addAll(pomArtikli);

	}

	/**
	 * Ucitava Poslovnog korisnika
	 * 
	 * @param podaciKorisnika Lista koja sadrzi podatke iz kategorije Korisnik
	 * @param korisnik        Lista klase Korisnik
	 * @param i               Iterira kroz listu
	 */
	private static void ucitavanjePoslovnogKorisnika(List<String> podaciKorisnika, List<Korisnik> korisnik, int i) {
		Long id = Long.parseLong(podaciKorisnika.get(i + 1));
		String naziv = podaciKorisnika.get(i + 2);
		String mail = podaciKorisnika.get(i + 3);
		String web = podaciKorisnika.get(i + 4);
		String telefon = podaciKorisnika.get(i + 5);

		korisnik.add(new PoslovniKorisnik(id, naziv, mail, web, telefon));
	}

	/**
	 * Ucitava Privatnog korisnika
	 * 
	 * @param podaciKorisnika Lista koja sadrzi podatke iz kategorije Korisnik
	 * @param korisnik        Lista klase Korisnik
	 * @param i               Iterira kroz listu
	 */
	private static void ucitavanjePrivatnogKorisnika(List<String> podaciKorisnika, List<Korisnik> korisnik, int i) {
		Long id = Long.parseLong(podaciKorisnika.get(i + 1));
		String ime = podaciKorisnika.get(i + 2);
		String prezime = podaciKorisnika.get(i + 3);
		String mail = podaciKorisnika.get(i + 4);
		String telefon = podaciKorisnika.get(i + 5);

		korisnik.add(new PrivatniKorisnik(id, ime, prezime, mail, telefon));
	}

	/**
	 * Dohvacanje korisnika i spremanje u listu klase Korisnik
	 * 
	 * @param podaciKorisnika String lista koja sadrzi podatke iz .txt datoteke
	 *                        Korisnik
	 * @param korisnik        Lista klase korisnik
	 */
	private static void ucitavanjeKorisnika(List<String> podaciKorisnika, List<Korisnik> korisnik) {
		// int i = 0;

		for (int i = 0; i < podaciKorisnika.size(); i++) {

			Integer odabirVrsteKorisnika = 0;

			if (i % 6 == 0) {
				odabirVrsteKorisnika = Integer.parseInt(podaciKorisnika.get(i));
			}
			if (odabirVrsteKorisnika == 1) {
				ucitavanjePrivatnogKorisnika(podaciKorisnika, korisnik, i);
			}
			if (odabirVrsteKorisnika == 2) {
				ucitavanjePoslovnogKorisnika(podaciKorisnika, korisnik, i);
			}

		}

	}

	/**
	 * Serijalizacija prodaje
	 * 
	 * @param prodajaList Lista klase Prodaja
	 */
	private static void srijalizacija(List<Prodaja> prodajaList) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZATION_FILE_NAME))) {
			out.writeObject(prodajaList);
			out.close();
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	/**
	 * Metoda za dodavanje liste artikala u parametriziranu klasu
	 * 
	 * @param zbirka        Objekt parametrizirane klase Kategorija
	 * @param listaArtikala Lista klase Artikl
	 */
	private static void pushArtikli(Kategorija<Artikl> zbirka, List<Artikl> listaArtikala) {
		for (Artikl a : listaArtikala) {
			zbirka.dodajArtikl(a);
		}
	}

}
