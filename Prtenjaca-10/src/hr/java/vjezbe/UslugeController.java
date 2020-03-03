package hr.java.vjezbe;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
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
 * Klasa koja kontrolira fxml za pretragu usluga i implementira sucelje
 * Initializable
 * 
 * @author Marko
 *
 */
public class UslugeController implements Initializable {

	@FXML
	private TableView<Usluga> tableView;
	@FXML
	private TableColumn<Usluga, String> naslovCol;
	@FXML
	private TableColumn<Usluga, String> opisCol;
	@FXML
	private TableColumn<Usluga, BigDecimal> cijenaCol;
	@FXML
	private TableColumn<Usluga, Stanje> stanjeCol;

	@FXML
	private TableColumn<Usluga, Stanje> stanjeColi;

	@FXML
	private TextField searchNaslov;
	@FXML
	private TextField searchOpis;
	@FXML
	private TextField searchCijena;
	@FXML
	private Button pretraziButton;
	@FXML
	private BorderPane borderPane;

	ObservableList<Usluga> usluga;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		naslovCol.setCellValueFactory(new PropertyValueFactory<Usluga, String>("naslov"));
		opisCol.setCellValueFactory(new PropertyValueFactory<Usluga, String>("opis"));
		cijenaCol.setCellValueFactory(new PropertyValueFactory<Usluga, BigDecimal>("cijena"));
		stanjeCol.setCellValueFactory(new PropertyValueFactory<Usluga, Stanje>("stanje"));

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
	private ObservableList<Usluga> createTable() throws BazaPodatakaException, IOException {
		Usluga usluge = null;

		List<Usluga> listItems = new ArrayList<Usluga>();

		listItems = BazaPodataka.dohvatiUslugePremaKriterijima(usluge);

//		listItems = Datoteke.dohvatiArtikle().stream().filter(p -> p instanceof Usluga).map(sc -> (Usluga) sc)
//				.collect(Collectors.toList());

		HashSet<Usluga> set = new HashSet<>();
		set.addAll(listItems);

		usluga = FXCollections.observableArrayList(set);

		return usluga;

	}

	/**
	 * Uzima rijeci iz textfielda te filtira listu koja se slaze sa trazenim
	 * rijecima
	 */
	@FXML
	private void filter() {
		FilteredList<Usluga> filteredList = new FilteredList<>(usluga, b -> true);
		filteredList.setPredicate((data) -> {
			boolean showItem = true;
			if (!searchNaslov.getText().isEmpty()) {
				showItem = showItem && (data.getNaslov().toLowerCase().contains(searchNaslov.getText().toLowerCase()));
			}
			if (!searchOpis.getText().isEmpty()) {
				showItem = showItem && (data.getOpis().toLowerCase().contains(searchOpis.getText().toLowerCase()));
			}
			if (!searchCijena.getText().isEmpty()) {
				showItem = showItem && (data.getCijena().toString().contains(searchCijena.getText()));
			}
			return showItem;
		});

		SortedList<Usluga> sortedList = new SortedList<>(filteredList);
		sortedList.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedList);

	}

}
