package hr.java.vjezbe;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Stanje;
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
 * Klasa koja kontrolira fxml za pretragu automobila i implementira sucelje
 * Initializable
 * 
 * @author Marko
 *
 */
public class AutomobiliController implements Initializable {

	@FXML
	private TableView<Automobil> tableView;
	@FXML
	private TableColumn<Automobil, String> naslovCol;
	@FXML
	private TableColumn<Automobil, String> opisCol;
	@FXML
	private TableColumn<Automobil, BigDecimal> snagaCol;
	@FXML
	private TableColumn<Automobil, BigDecimal> cijenaCol;
	@FXML
	private TableColumn<Automobil, Stanje> stanjeCol;
	@FXML
	private TableColumn<Automobil, Stanje> stanjeColi;

	@FXML
	private TextField searchNaslov;
	@FXML
	private TextField searchOpis;
	@FXML
	private TextField searchSnaga;
	@FXML
	private TextField searchCijena;
	@FXML
	private Button pretraziButton;
	@FXML
	private BorderPane borderPane;

	ObservableList<Automobil> automobili;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		naslovCol.setCellValueFactory(new PropertyValueFactory<Automobil, String>("naslov"));
		opisCol.setCellValueFactory(new PropertyValueFactory<Automobil, String>("opis"));
		cijenaCol.setCellValueFactory(new PropertyValueFactory<Automobil, BigDecimal>("cijena"));
		snagaCol.setCellValueFactory(new PropertyValueFactory<Automobil, BigDecimal>("snagaKs"));
		stanjeCol.setCellValueFactory(new PropertyValueFactory<Automobil, Stanje>("stanje"));

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
	 * Filtrira listu artikala te ih dodaje u Observable listu koja se dodaje u
	 * tableview
	 * 
	 * @return vraca observable listu
	 * @throws IOException           Input/Outpout Exception
	 * @throws BazaPodatakaException Oznacena Custom iznimka za bazu podataka
	 */
	private ObservableList<Automobil> createTable() throws BazaPodatakaException, IOException {

		Automobil auto = null;

		List<Automobil> listItems = new ArrayList<Automobil>();

		listItems = BazaPodataka.dohvatiAutomobilePremaKriterijima(auto);

//		listItems = Datoteke.dohvatiArtikle().stream().filter(p -> p instanceof Automobil).map(sc -> (Automobil) sc)
//				.collect(Collectors.toList());

		HashSet<Automobil> set = new HashSet<>();
		set.addAll(listItems);

		automobili = FXCollections.observableArrayList(set);

		return automobili;

	}

	/**
	 * Uzima rijeci iz textfielda te filtira listu koja se slaze sa trazenim
	 * rijecima
	 */
	@FXML
	private void filter() {
		FilteredList<Automobil> filteredList = new FilteredList<>(automobili, b -> true);
		filteredList.setPredicate((data) -> {
			boolean showItem = true;
			if (!searchNaslov.getText().isEmpty()) {
				showItem = showItem && (data.getNaslov().toLowerCase().contains(searchNaslov.getText().toLowerCase()));
			}
			if (!searchOpis.getText().isEmpty()) {
				showItem = showItem && (data.getOpis().toLowerCase().contains(searchOpis.getText().toLowerCase()));
			}
			if (!searchSnaga.getText().isEmpty()) {
				showItem = showItem && (data.getSnagaKs().toString().contains(searchSnaga.getText()));
			}
			if (!searchCijena.getText().isEmpty()) {
				showItem = showItem && (data.getCijena().toString().contains(searchCijena.getText()));
			}
			return showItem;
		});

		SortedList<Automobil> sortedList = new SortedList<>(filteredList);
		sortedList.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedList);

	}

}
