package hr.java.vjezbe;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Stan;
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

/**
 * Klasa koja kontrolira fxml za pretragu stanova i implementira sucelje
 * Initializable
 * 
 * @author Marko
 *
 */
public class StanoviController implements Initializable {

	@FXML
	private TableView<Stan> tableView;
	@FXML
	private TableColumn<Stan, String> naslovCol;
	@FXML
	private TableColumn<Stan, String> opisCol;
	@FXML
	private TableColumn<Stan, Integer> kvadraturaCol;
	@FXML
	private TableColumn<Stan, BigDecimal> cijenaCol;
	@FXML
	private TableColumn<Stan, Stanje> stanjeCol;
	@FXML
	private TextField searchNaslov;
	@FXML
	private TextField searchOpis;
	@FXML
	private TextField searchKvadratura;
	@FXML
	private TextField searchCijena;
	@FXML
	private Button pretraziButton;

	ObservableList<Stan> stanovi;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		naslovCol.setCellValueFactory(new PropertyValueFactory<Stan, String>("naslov"));
		opisCol.setCellValueFactory(new PropertyValueFactory<Stan, String>("opis"));
		kvadraturaCol.setCellValueFactory(new PropertyValueFactory<Stan, Integer>("kvadratura"));
		cijenaCol.setCellValueFactory(new PropertyValueFactory<Stan, BigDecimal>("cijena"));
		stanjeCol.setCellValueFactory(new PropertyValueFactory<Stan, Stanje>("stanje"));

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
	private ObservableList<Stan> createTable() throws BazaPodatakaException, IOException {

		Stan stan = null;
		List<Stan> listItems = BazaPodataka.dohvatiStanovePremaKriterijima(stan);

//		List<Stan> listItems = Datoteke.dohvatiArtikle().stream().filter(p -> p instanceof Stan).map(sc -> (Stan) sc)
//				.collect(Collectors.toList());

		HashSet<Stan> set = new HashSet<>();
		set.addAll(listItems);

		stanovi = FXCollections.observableArrayList(set);

		return stanovi;
	}

	/**
	 * Uzima rijeci iz textfielda te filtira listu koja se slaze sa trazenim
	 * rijecima
	 */
	@FXML
	private void filter() {
		FilteredList<Stan> filteredList = new FilteredList<>(stanovi, b -> true);
		filteredList.setPredicate((data) -> {
			boolean showItem = true;
			Integer kvadratura = data.getKvadratura();
			if (!searchNaslov.getText().isEmpty()) {
				showItem = showItem && (data.getNaslov().toLowerCase().contains(searchNaslov.getText().toLowerCase()));
			}
			if (!searchOpis.getText().isEmpty()) {
				showItem = showItem && (data.getOpis().toLowerCase().contains(searchOpis.getText().toLowerCase()));
			}
			if (!searchKvadratura.getText().isEmpty()) {
				showItem = showItem && (kvadratura.toString().contains(searchKvadratura.getText()));
			}
			if (!searchCijena.getText().isEmpty()) {
				showItem = showItem && (data.getCijena().toString().contains(searchCijena.getText()));
			}
			return showItem;
		});

		SortedList<Stan> sortedList = new SortedList<>(filteredList);
		sortedList.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedList);
	}

}
