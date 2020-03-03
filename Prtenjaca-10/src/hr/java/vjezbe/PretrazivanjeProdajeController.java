package hr.java.vjezbe;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PretrazivanjeProdajeController implements Initializable{
	
	@FXML
	private TableView<Prodaja> tableView;
	@FXML
	private TableColumn<Prodaja, String> oglasCol;
	@FXML
	private TableColumn<Prodaja, String> korisnikCol;
	@FXML
	private TableColumn<Prodaja, LocalDate> datumCol;
	
	@FXML
	private ComboBox<Artikl> comboArtikli;
	@FXML
	private ComboBox<Korisnik> comboKorisnici;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private Button pretraziButton;

	ObservableList<Prodaja> observableProdaja;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		oglasCol.setCellValueFactory(new PropertyValueFactory<Prodaja, String>("artikl"));
		korisnikCol.setCellValueFactory(new PropertyValueFactory<Prodaja, String>("korisnik"));
		datumCol.setCellValueFactory(new PropertyValueFactory<Prodaja, LocalDate>("datumObjave"));
		
		try {
			tableView.setItems(createTable());
		} catch (BazaPodatakaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	/**
	 * Filtrira listu prodaje te ih dodaje u Observable listu koja se dodaje u tableview
	 * @return vraca observable listu
	 * @throws IOException Input/Output Exception
	 * @throws BazaPodatakaException Custom oznacena iznimka baze podataka
	 */
	private ObservableList<Prodaja> createTable() throws BazaPodatakaException, IOException {
		
		Prodaja prodaja = null;
		List<Prodaja> listItems = BazaPodataka.dohvatiProdajuPremaKriterijima(prodaja);
		List<Artikl> artikli = new ArrayList<Artikl>();
		List<Korisnik> korisnici = new ArrayList<Korisnik>();
		
		Automobil auto = null;
		Stan stan = null;
		Usluga usluga = null;
		List<Automobil> listAuti = BazaPodataka.dohvatiAutomobilePremaKriterijima(auto);
		List<Stan> listStan = BazaPodataka.dohvatiStanovePremaKriterijima(stan);
		List<Usluga> listUsluga = BazaPodataka.dohvatiUslugePremaKriterijima(usluga);
		
		PrivatniKorisnik privatniKorisnik = null;
		PoslovniKorisnik poslovniKorisnik = null;
		List<PrivatniKorisnik> listPrivatni = BazaPodataka.dohvatiPrivatnogKorisnikaPremaKriterijima(privatniKorisnik);
		List<PoslovniKorisnik> listPoslovni = BazaPodataka.dohvatiPoslovnogKorisnikaPremaKriterijima(poslovniKorisnik);
		
		artikli.addAll(listAuti);
		artikli.addAll(listStan);
		artikli.addAll(listUsluga);
		
		korisnici.addAll(listPrivatni);
		korisnici.addAll(listPoslovni);
		
		comboArtikli.getItems().addAll(artikli);
		comboKorisnici.getItems().addAll(korisnici);
		
		
		HashSet<Prodaja> set = new HashSet<>();
		set.addAll(listItems);
		
		observableProdaja = FXCollections.observableArrayList(set);
		
		return observableProdaja;
	}
	
	/**
	 * Uzima vrijednost iz ComboBox-eva te filtira listu koja se slaze sa trazenim rijecima
	 */
	@FXML
	private void filter() {
		FilteredList<Prodaja> filteredList = new FilteredList<>(observableProdaja, b -> true);
		filteredList.setPredicate((data) -> {
			boolean showItem = true;
			if (!(comboArtikli.getValue() == null)) {
				showItem = showItem && (data.getArtikl().toString().contains(comboArtikli.getValue().toString()));
			}
			if (!(comboKorisnici.getValue() == null)) {
				showItem = showItem && (data.getKorisnik().toString().contains(comboKorisnici.getValue().toString()));
			}
			if (!(datePicker.getValue() == null)) {
				showItem = showItem && (data.getDatumObjave().equals(datePicker.getValue()));
			}
			return showItem;
		});

		SortedList<Prodaja> sortedList = new SortedList<>(filteredList);
		sortedList.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedList);
		
	}

}
