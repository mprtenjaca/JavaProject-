package hr.java.vjezbe.baza;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

public class BazaPodataka {

	public static final String DB_Properties = "database.properties";
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);

	public static void main(String[] args) throws BazaPodatakaException, IOException {

	}

	// PRODAJA

	/**
	 * Pohranjuje nove prodaje u bazu podataka
	 * 
	 * @param prodaja Objekt Prodaje
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static void pohraniNovuProdaju(Prodaja prodaja) throws BazaPodatakaException, IOException {

		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza
					.prepareStatement("insert into prodaja(idArtikl, idKorisnik, datumObjave) " + "values (?, ?, ?);");
			preparedStatement.setLong(1, prodaja.getArtikl().getId());
			preparedStatement.setLong(2, prodaja.getKorisnik().getId());
			preparedStatement.setDate(3, Date.valueOf(prodaja.getDatumObjave()));
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	/**
	 * Dohvacanje Prodaje iz baze podataka
	 * 
	 * @param prodaja Objekt prodaje
	 * @return Vraca listu prodaje
	 * @throws BazaPodatakaException Custom iznimka bazr podataka
	 */
	public static List<Prodaja> dohvatiProdajuPremaKriterijima(Prodaja prodaja) throws BazaPodatakaException {
		List<Prodaja> listaProdaje = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"select distinct korisnik.id as idKorisnika, tipKorisnika.naziv as tipKorisnika, \r\n"
							+ "korisnik.naziv as nazivKorisnika, web, email, telefon, \r\n"
							+ "korisnik.ime, korisnik.prezime, tipArtikla.naziv as tipArtikla,\r\n"
							+ "artikl.naslov, artikl.opis, artikl.cijena, artikl.kvadratura,\r\n"
							+ "artikl.snaga, stanje.naziv as stanje, prodaja.datumObjave, artikl.id as idArtikla\r\n"
							+ "from korisnik inner join \r\n"
							+ "tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika inner join\r\n"
							+ "prodaja on prodaja.idKorisnik = korisnik.id inner join\r\n"
							+ "artikl on artikl.id = prodaja.idArtikl inner join\r\n"
							+ "tipArtikla on tipArtikla.id = artikl.idTipArtikla inner join\r\n"
							+ "stanje on stanje.id = artikl.idStanje where 1=1");
			if (Optional.ofNullable(prodaja).isEmpty() == false) {
				if (Optional.ofNullable(prodaja.getArtikl()).isPresent())
					sqlUpit.append(" AND prodaja.idArtikl = " + prodaja.getArtikl().getId());

				if (Optional.ofNullable(prodaja.getKorisnik()).isPresent())
					sqlUpit.append(" AND prodaja.idArtikl = " + prodaja.getKorisnik().getId());

				if (Optional.ofNullable(prodaja.getDatumObjave()).isPresent()) {
					sqlUpit.append(" AND prodaja.datumObjave = '"
							+ prodaja.getDatumObjave().format(DateTimeFormatter.ISO_DATE) + "'");
				}
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Korisnik korisnik = null;
				if (resultSet.getString("tipKorisnika").equals("PrivatniKorisnik")) {
					korisnik = new PrivatniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("ime"),
							resultSet.getString("prezime"), resultSet.getString("email"),
							resultSet.getString("telefon"));
				} else if (resultSet.getString("tipKorisnika").equals("PoslovniKorisnik")) {
					korisnik = new PoslovniKorisnik(resultSet.getLong("idKorisnika"), resultSet.getString("naziv"),
							resultSet.getString("web"), resultSet.getString("email"), resultSet.getString("telefon"));
				}

				Artikl artikl = null;
				if (resultSet.getString("tipArtikla").equals("Automobil")) {
					artikl = new Automobil(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("snaga"),
							resultSet.getBigDecimal("cijena"), Stanje.valueOf(resultSet.getString("stanje")));

				} else if (resultSet.getString("tipArtikla").equals("Usluga")) {
					artikl = new Usluga(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							Stanje.valueOf(resultSet.getString("stanje")));

				} else if (resultSet.getString("tipArtikla").equals("Stan")) {
					artikl = new Stan(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							Stanje.valueOf(resultSet.getString("stanje")), resultSet.getInt("kvadratura"));
				}

				Prodaja novaProdaja = new Prodaja(artikl, korisnik,
						resultSet.getTimestamp("datumObjave").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				listaProdaje.add(novaProdaja);
			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return listaProdaje;
	}

	// DOHVAT ARTIKALA
	public static List<Artikl> dohvatiArtikle(Artikl artikl)
			throws BazaPodatakaException, FileNotFoundException, IOException {
		List<Artikl> art = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder("select * from artikl");

			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {

				if (resultSet.getString("tipArtikla").equals("Automobil")) {
					artikl = new Automobil(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("snaga"),
							resultSet.getBigDecimal("cijena"), Stanje.valueOf(resultSet.getString("stanje")));

				} else if (resultSet.getString("tipArtikla").equals("Usluga")) {
					artikl = new Usluga(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							Stanje.valueOf(resultSet.getString("stanje")));

				} else if (resultSet.getString("artikl.id").equals("Stan")) {
					artikl = new Stan(resultSet.getLong("idArtikla"), resultSet.getString("naslov"),
							resultSet.getString("opis"), resultSet.getBigDecimal("cijena"),
							Stanje.valueOf(resultSet.getString("stanje")), resultSet.getInt("kvadratura"));
				}

				art.add(artikl);
			}
		} catch (SQLException | IOException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}
		return art;
	}

	// Korisnici

	/**
	 * Dohvacanje privatnog korisnika iz baze podataka
	 * 
	 * @param privatni Objekt Privatnog Korisnika
	 * @return Lista Privatnog Korisnika
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static List<PrivatniKorisnik> dohvatiPrivatnogKorisnikaPremaKriterijima(PrivatniKorisnik privatni)
			throws BazaPodatakaException, IOException {
		List<PrivatniKorisnik> listaPrivatnihKorisnika = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder("select distinct korisnik.id, ime, prezime, email, telefon "
					+ "from korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika "
					+ "where tipKorisnika.naziv = 'PrivatniKorisnik'");
			if (Optional.ofNullable(privatni).isEmpty() == false) {
				if (Optional.ofNullable(privatni).map(PrivatniKorisnik::getId).isPresent())
					sqlUpit.append(" AND korisnik.id = " + privatni.getId());
				if (Optional.ofNullable(privatni.getIme()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND koisnik.ime LIKE '%" + privatni.getIme() + "%'");
				if (Optional.ofNullable(privatni.getPrezime()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.prezime LIKE '%" + privatni.getPrezime() + "%'");
				if (Optional.ofNullable(privatni.getEmail()).map(String::isBlank).isPresent())
					sqlUpit.append(" AND korisnik.email LIKE '%" + privatni.getEmail() + "%'");
				if (Optional.ofNullable(privatni.getTelefon()).map(String::isBlank).isPresent())
					sqlUpit.append(" AND korisnik.telefon LIKE '%" + privatni.getTelefon() + "%'");
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String ime = resultSet.getString("ime");
				String prezime = resultSet.getString("prezime");
				String email = resultSet.getString("email");
				String telefon = resultSet.getString("telefon");
				PrivatniKorisnik newPrivatni = new PrivatniKorisnik(id, ime, prezime, email, telefon);
				listaPrivatnihKorisnika.add(newPrivatni);
			}
		} catch (SQLException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}

		return listaPrivatnihKorisnika;
	}

	/**
	 * Pohranjuje novog privatnog korisnika u bazu podataka
	 * 
	 * @param privatni Objekt Privatnog Korisnika
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static void pohraniNovogPrivatnigKorisnika(PrivatniKorisnik privatni)
			throws BazaPodatakaException, IOException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
					"insert into korisnik(Ime, Prezime, Email, Telefon, idTipKorisnika) " + "values (?, ?, ?, ?, 1);");
			preparedStatement.setString(1, privatni.getIme());
			preparedStatement.setString(2, privatni.getPrezime());
			preparedStatement.setString(3, privatni.getEmail());
			preparedStatement.setString(4, privatni.getTelefon());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	/**
	 * Dohvacanje privatnog korisnika iz baze podataka
	 * 
	 * @param poslovni Objekt Poslovnog Korisnika
	 * @return Lista Privatnog Korisnika
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static List<PoslovniKorisnik> dohvatiPoslovnogKorisnikaPremaKriterijima(PoslovniKorisnik poslovni)
			throws BazaPodatakaException, IOException {
		List<PoslovniKorisnik> listaPoslovnihKorisnika = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"select distinct korisnik.id, korisnik.naziv, web, telefon, email "
							+ "from korisnik inner join tipKorisnika on tipKorisnika.id = korisnik.idTipKorisnika "
							+ "where tipKorisnika.naziv = 'PoslovniKorisnik'");
			if (Optional.ofNullable(poslovni).isEmpty() == false) {
				if (Optional.ofNullable(poslovni).map(PoslovniKorisnik::getId).isPresent())
					sqlUpit.append(" AND korisnik.id = " + poslovni.getId());
				if (Optional.ofNullable(poslovni.getNaziv()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.naziv LIKE '%" + poslovni.getNaziv() + "%'");
				if (Optional.ofNullable(poslovni.getWeb()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND korisnik.web LIKE '%" + poslovni.getWeb() + "%'");
				if (Optional.ofNullable(poslovni.getTelefon()).map(String::isBlank).isPresent())
					sqlUpit.append(" AND korisnik.telefon LIKE '%" + poslovni.getTelefon() + "%'");
				if (Optional.ofNullable(poslovni.getEmail()).map(String::isBlank).isPresent())
					sqlUpit.append(" AND korisnik.email LIKE '%" + poslovni.getEmail() + "%'");

			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naziv = resultSet.getString("naziv");
				String web = resultSet.getString("web");
				String email = resultSet.getString("email");
				String telefon = resultSet.getString("telefon");
				PoslovniKorisnik newPoslovni = new PoslovniKorisnik(id, naziv, web, email, telefon);
				listaPoslovnihKorisnika.add(newPoslovni);
			}
		} catch (SQLException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}

		return listaPoslovnihKorisnika;
	}

	/**
	 * Pohranjuje novog poslovnog korisnika u bazu podataka
	 * 
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 * @param poslovni Objekt Poslovnog Korisnika
	 */
	public static void pohraniNovogPoslovnogKorisnika(PoslovniKorisnik poslovni)
			throws BazaPodatakaException, IOException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
					"insert into korisnik(Naziv, Web, Telefon, Email, idTipKorisnika) " + "values (?, ?, ?, ?, 2);");
			preparedStatement.setString(1, poslovni.getNaziv());
			preparedStatement.setString(2, poslovni.getWeb());
			preparedStatement.setString(4, poslovni.getEmail());
			preparedStatement.setString(3, poslovni.getTelefon());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	// Artikli
	/**
	 * Dohvacanje usluge iz baze podataka
	 * 
	 * @param usluga Objekt usluge
	 * @return Lista usluge
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static List<Usluga> dohvatiUslugePremaKriterijima(Usluga usluga) throws BazaPodatakaException, IOException {
		List<Usluga> listaUsluga = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id, naslov, opis, cijena, snaga, stanje.naziv "
							+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
							+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Usluga'");
			if (Optional.ofNullable(usluga).isEmpty() == false) {
				if (Optional.ofNullable(usluga).map(Usluga::getId).isPresent())
					sqlUpit.append(" AND artikl.id = " + usluga.getId());
				if (Optional.ofNullable(usluga.getNaslov()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.naslov LIKE '%" + usluga.getNaslov() + "%'");
				if (Optional.ofNullable(usluga.getOpis()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.opis LIKE '%" + usluga.getOpis() + "%'");
				if (Optional.ofNullable(usluga).map(Usluga::getCijena).isPresent())
					sqlUpit.append(" AND artikl.cijena = " + usluga.getCijena());
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				String stanje = resultSet.getString("naziv");
				Usluga newUsluga = new Usluga(id, naslov, opis, cijena, Stanje.valueOf(stanje));
				listaUsluga.add(newUsluga);
			}
		} catch (SQLException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}

		return listaUsluga;
	}

	/**
	 * Pohranjuje novu uslugu u bazu podataka
	 * 
	 * @param usluga Objekt usluge
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static void pohraniNovuUslugu(Usluga usluga) throws BazaPodatakaException, IOException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza.prepareStatement(
					"insert into artikl(Naslov, Opis, Cijena, idStanje, idTipArtikla) " + "values (?, ?, ?, ?, 2);");
			preparedStatement.setString(1, usluga.getNaslov());
			preparedStatement.setString(2, usluga.getOpis());
			preparedStatement.setBigDecimal(3, usluga.getCijena());
			preparedStatement.setLong(4, (usluga.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	/**
	 * Dohvacanje automobila iz baze podataka
	 * 
	 * @param auto Objekt Automobila
	 * @return Lista automobila
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static List<Automobil> dohvatiAutomobilePremaKriterijima(Automobil auto)
			throws BazaPodatakaException, IOException {
		List<Automobil> listaAuta = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id, naslov, opis, cijena, snaga, stanje.naziv "
							+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
							+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Automobil'");
			if (Optional.ofNullable(auto).isEmpty() == false) {
				if (Optional.ofNullable(auto).map(Automobil::getId).isPresent())
					sqlUpit.append(" AND artikl.id = " + auto.getId());
				if (Optional.ofNullable(auto.getNaslov()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.naslov LIKE '%" + auto.getNaslov() + "%'");
				if (Optional.ofNullable(auto.getOpis()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.opis LIKE '%" + auto.getOpis() + "%'");
				if (Optional.ofNullable(auto).map(Automobil::getCijena).isPresent())
					sqlUpit.append(" AND artikl.cijena = " + auto.getCijena());
				if (Optional.ofNullable(auto).map(Automobil::getSnagaKs).isPresent())
					sqlUpit.append(" AND artikl.snaga = " + auto.getSnagaKs());
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				String stanje = resultSet.getString("naziv");
				BigDecimal snaga = resultSet.getBigDecimal("snaga");
				Automobil newAuto = new Automobil(id, naslov, opis, cijena, snaga, Stanje.valueOf(stanje));
				listaAuta.add(newAuto);
			}
		} catch (SQLException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}

		return listaAuta;
	}

	/**
	 * Pohranjuje novi automobil u bazu podataka
	 * 
	 * @param auto Objekt Automobila
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static void pohraniNoviAutomobil(Automobil auto) throws BazaPodatakaException, IOException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza
					.prepareStatement("insert into artikl(Naslov, Opis, Cijena, Snaga, idStanje, idTipArtikla) "
							+ "values (?, ?, ?, ?, ?, 1);");
			preparedStatement.setString(1, auto.getNaslov());
			preparedStatement.setString(2, auto.getOpis());
			preparedStatement.setBigDecimal(3, auto.getCijena());
			preparedStatement.setBigDecimal(4, auto.getSnagaKs());
			preparedStatement.setLong(5, (auto.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	/**
	 * Dohvacanje stanova iz baze podataka
	 * 
	 * @param stan Objekt Stana
	 * @return Lista Stana
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static List<Stan> dohvatiStanovePremaKriterijima(Stan stan) throws BazaPodatakaException, IOException {
		List<Stan> listaStanova = new ArrayList<>();
		try (Connection connection = spajanjeNaBazu()) {
			StringBuilder sqlUpit = new StringBuilder(
					"SELECT distinct artikl.id, naslov, opis, cijena, kvadratura, stanje.naziv "
							+ "FROM artikl inner join stanje on stanje.id = artikl.idStanje "
							+ "inner join tipArtikla on tipArtikla.id = artikl.idTipArtikla WHERE tipArtikla.naziv = 'Stan'");
			if (Optional.ofNullable(stan).isEmpty() == false) {
				if (Optional.ofNullable(stan).map(Stan::getId).isPresent())
					sqlUpit.append(" AND artikl.id = " + stan.getId());
				if (Optional.ofNullable(stan.getNaslov()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.naslov LIKE '%" + stan.getNaslov() + "%'");
				if (Optional.ofNullable(stan.getOpis()).map(String::isBlank).orElse(true) == false)
					sqlUpit.append(" AND artikl.opis LIKE '%" + stan.getOpis() + "%'");
				if (Optional.ofNullable(stan).map(Stan::getCijena).isPresent())
					sqlUpit.append(" AND artikl.cijena = " + stan.getCijena());
				if (Optional.ofNullable(stan).map(Stan::getKvadratura).isPresent())
					sqlUpit.append(" AND artikl.kvadratura = " + stan.getKvadratura());
			}
			Statement query = connection.createStatement();
			ResultSet resultSet = query.executeQuery(sqlUpit.toString());
			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				String naslov = resultSet.getString("naslov");
				String opis = resultSet.getString("opis");
				BigDecimal cijena = resultSet.getBigDecimal("cijena");
				String stanje = resultSet.getString("naziv");
				Integer kvadratura = resultSet.getInt("kvadratura");
				Stan newStan = new Stan(id, naslov, opis, cijena, Stanje.valueOf(stanje), kvadratura);
				listaStanova.add(newStan);
			}
		} catch (SQLException e) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, e);
			throw new BazaPodatakaException(poruka, e);
		}

		return listaStanova;
	}

	/**
	 * Pohranjuje novi stan u bazu podataka
	 * 
	 * @param stan Objekt Stana
	 * @throws BazaPodatakaException Custom iznimka baze podataka
	 * @throws IOException           Input/Output Exception
	 */
	public static void pohraniNoviStan(Stan stan) throws BazaPodatakaException, IOException {
		try (Connection veza = spajanjeNaBazu()) {
			PreparedStatement preparedStatement = veza
					.prepareStatement("insert into artikl(Naslov, Opis, Cijena, Kvadratura, idStanje, idTipArtikla) "
							+ "values (?, ?, ?, ?, ?, 3);");
			preparedStatement.setString(1, stan.getNaslov());
			preparedStatement.setString(2, stan.getOpis());
			preparedStatement.setBigDecimal(3, stan.getCijena());
			preparedStatement.setInt(4, stan.getKvadratura());
			preparedStatement.setLong(5, (stan.getStanje().ordinal() + 1));
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			String poruka = "Došlo je do pogreške u radu s bazom podataka";
			logger.error(poruka, ex);
			throw new BazaPodatakaException(poruka, ex);
		}
	}

	/**
	 * Spajanje na bazu podatakaq preko properties datoteke
	 * 
	 * @return vraca stanje konekcije
	 * @throws FileNotFoundException Iznimka za nemogucnost pronalazenja properties
	 *                               datoteke
	 * @throws IOException           Input/Output Exception
	 * @throws SQLException          Iznimka u radu baze podataka
	 */
	private static Connection spajanjeNaBazu() throws FileNotFoundException, IOException, SQLException {

		Properties props = new Properties();

		props.load(new FileReader(DB_Properties));

		String urlBazePodataka = props.getProperty("bazaPodatakaUrl");
		String username = props.getProperty("dbUsername");
		String password = props.getProperty("dbPassword");

		Connection veza = DriverManager.getConnection(urlBazePodataka, username, password);

		return veza;

	}

}
