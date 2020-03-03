package hr.java.vjezbe;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * Klasa koja kontrolira fxml za pretragu privatnih korisnika i implementira sucelje Initializable
 * @author Marko
 *
 */
public class PrivatniKorisniciController implements Initializable {

	@FXML
	private TableView<PrivatniKorisnik> tableView;
	@FXML
	private TableColumn<PrivatniKorisnik, String> imeCol;
	@FXML
	private TableColumn<PrivatniKorisnik, String> prezimeCol;
	@FXML
	private TableColumn<PrivatniKorisnik, String> emailCol;
	@FXML
	private TableColumn<PrivatniKorisnik, String> telefonCol;
	
	@FXML
	private TextField searchIme;
	@FXML
	private TextField searchPrezime;
	@FXML
	private TextField searchEmail;
	@FXML
	private TextField searchTelefon;
	@FXML
	private Button pretraziButton;
	@FXML
	private BorderPane borderPane;

	ObservableList<PrivatniKorisnik> privatni;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		imeCol.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("ime"));
		prezimeCol.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("prezime"));
		emailCol.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("email"));
		telefonCol.setCellValueFactory(new PropertyValueFactory<PrivatniKorisnik, String>("telefon"));

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
	 * Filtrira listu korisnika te ih dodaje u Observable listu koja se dodaje u tableview
	 * @return vraca observable listu
	 * @throws IOException Input/Output Exception
	 * @throws BazaPodatakaException Custom oznacena iznimka baze podataka
	 */
	private ObservableList<PrivatniKorisnik> createTable() throws BazaPodatakaException, IOException {
		
		PrivatniKorisnik privatniKorisnik = null;
		List<PrivatniKorisnik> listItems = new ArrayList<PrivatniKorisnik>();
		listItems = BazaPodataka.dohvatiPrivatnogKorisnikaPremaKriterijima(privatniKorisnik);
		
//		listItems = Datoteke.dohvatiKorisnike().stream().filter(p -> p instanceof PrivatniKorisnik).map(sc -> (PrivatniKorisnik) sc)
//				.collect(Collectors.toList());
		
		HashSet<PrivatniKorisnik> set = new HashSet<PrivatniKorisnik>();
		set.addAll(listItems);

		privatni = FXCollections.observableArrayList(set);
		
		return privatni;

	}

	/**
	 * Uzima rijeci iz textfielda te filtira listu koja se slaze sa trazenim rijecima
	 */
	@FXML
	private void filter() {
		FilteredList<PrivatniKorisnik> filteredList = new FilteredList<>(privatni, b -> true);
		filteredList.setPredicate((data) -> {
			boolean showItem = true;
			if (!searchIme.getText().isEmpty()) {
				showItem = showItem && (data.getIme().toLowerCase().contains(searchIme.getText().toLowerCase()));
			}
			if (!searchPrezime.getText().isEmpty()) {
				showItem = showItem && (data.getPrezime().toLowerCase().contains(searchPrezime.getText().toLowerCase()));
			}
			if (!searchEmail.getText().isEmpty()) {
				showItem = showItem && (data.getEmail().toLowerCase().contains(searchEmail.getText().toLowerCase()));
			}
			if (!searchTelefon.getText().isEmpty()) {
				showItem = showItem && (data.getTelefon().toLowerCase().contains(searchTelefon.getText().toLowerCase()));
			}
			return showItem;
		});

		SortedList<PrivatniKorisnik> sortedList = new SortedList<>(filteredList);
		sortedList.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedList);
		
	}

}
