package hr.java.vjezbe;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
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
 * Klasa koja kontrolira fxml za pretragu poslovnih korisnika i implementira sucelje Initializable
 * @author Marko
 *
 */
public class PoslovniKorisniciController implements Initializable {

	@FXML
	private TableView<PoslovniKorisnik> tableView;
	@FXML
	private TableColumn<PoslovniKorisnik, String> nazivCol;
	@FXML
	private TableColumn<PoslovniKorisnik, String> webCol;
	@FXML
	private TableColumn<PoslovniKorisnik, String> emailCol;
	@FXML
	private TableColumn<PoslovniKorisnik, String> telefonCol;
	
	@FXML
	private TextField searchNaziv;
	@FXML
	private TextField searchWeb;
	@FXML
	private TextField searchEmail;
	@FXML
	private TextField searchTelefon;
	@FXML
	private Button pretraziButton;
	@FXML
	private BorderPane borderPane;

	ObservableList<PoslovniKorisnik> poslovni;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		nazivCol.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("naziv"));
		webCol.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("web"));
		emailCol.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("email"));
		telefonCol.setCellValueFactory(new PropertyValueFactory<PoslovniKorisnik, String>("telefon"));

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
	 * @throws IOException Input/Oputput Exception
	 * @throws BazaPodatakaException Oznacena custom iznimka baze podataka
	 */
	private ObservableList<PoslovniKorisnik> createTable() throws BazaPodatakaException, IOException {
		PoslovniKorisnik poslovniKorisnik = null;
		List<PoslovniKorisnik> listItems = new ArrayList<PoslovniKorisnik>();
		listItems = BazaPodataka.dohvatiPoslovnogKorisnikaPremaKriterijima(poslovniKorisnik);
//		listItems = Datoteke.dohvatiKorisnike().stream().filter(p -> p instanceof PoslovniKorisnik).map(sc -> (PoslovniKorisnik) sc)
//				.collect(Collectors.toList());
		
		HashSet<PoslovniKorisnik> set = new HashSet<PoslovniKorisnik>();
		set.addAll(listItems);

		poslovni = FXCollections.observableArrayList(set);
		
		return poslovni;

	}

	/**
	 * Uzima rijeci iz textfielda te filtira listu koja se slaze sa trazenim rijecima
	 */
	@FXML
	private void filter() {
		FilteredList<PoslovniKorisnik> filteredList = new FilteredList<>(poslovni, b -> true);
		filteredList.setPredicate((data) -> {
			boolean showItem = true;
			if (!searchNaziv.getText().isEmpty()) {
				showItem = showItem && (data.getNaziv().toLowerCase().contains(searchNaziv.getText().toLowerCase()));
			}
			if (!searchWeb.getText().isEmpty()) {
				showItem = showItem && (data.getWeb().toLowerCase().contains(searchWeb.getText().toLowerCase()));
			}
			if (!searchEmail.getText().isEmpty()) {
				showItem = showItem && (data.getEmail().toLowerCase().contains(searchEmail.getText().toLowerCase()));
			}
			if (!searchTelefon.getText().isEmpty()) {
				showItem = showItem && (data.getTelefon().toLowerCase().contains(searchTelefon.getText().toLowerCase()));
			}
			return showItem;
		});

		SortedList<PoslovniKorisnik> sortedList = new SortedList<>(filteredList);
		sortedList.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedList);
		
	}

}
