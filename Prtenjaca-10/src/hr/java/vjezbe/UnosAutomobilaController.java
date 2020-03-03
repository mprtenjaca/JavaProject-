package hr.java.vjezbe;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class UnosAutomobilaController implements Initializable {

	@FXML
	private TextField unesiNaslov;
	@FXML
	private TextField unesiOpis;
	@FXML
	private TextField unesiSnaga;
	@FXML
	private TextField unesiCijena;
	@FXML
	private ComboBox<Stanje> stanje;
	@FXML
	private Button unesiButton;
	@FXML
	private BorderPane borderPane;

	ObservableList<Automobil> automobili;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<Stanje> stanjeList = FXCollections.observableArrayList(Stanje.values());
		stanje.setItems(stanjeList);

	}

	/**
	 * Unos automobila
	 * 
	 * @throws IOException           Input/Output Exception
	 * @throws BazaPodatakaException Oznacena custom iznimka baze podataka
	 */
	public void unesi() throws BazaPodatakaException, IOException {

		if (validation()) {
			Automobil auto1 = null;
			List<Automobil> listItems = BazaPodataka.dohvatiAutomobilePremaKriterijima(auto1);
			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
			Automobil auto = new Automobil(maxId.getAsLong() + 1, unesiNaslov.getText(), unesiOpis.getText(),
					new BigDecimal(unesiSnaga.getText()), new BigDecimal(unesiCijena.getText()), stanje.getValue());
			BazaPodataka.pohraniNoviAutomobil(auto);

//			List<Artikl> listItems = new ArrayList<Artikl>();
//			listItems = Datoteke.dohvatiArtikle();
//			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
//			Automobil auto = new Automobil(maxId.getAsLong() + 1, unesiNaslov.getText(), unesiOpis.getText(), new BigDecimal(unesiSnaga.getText()), new BigDecimal(unesiCijena.getText()), stanje.getValue());
//			listItems.add(auto);
//			Datoteke.zapisiDatotekuArtikala(listItems, 2);
		}
	}

	/**
	 * Validacija text filed-ova
	 * 
	 * @return Vraca true ili false
	 */
	public boolean validation() {

		StringBuilder errors = new StringBuilder();

		if (unesiNaslov.getText().isEmpty()) {
			errors.append("Naslov je obavezan podatak!\n");
		}
		if (unesiOpis.getText().isEmpty()) {
			errors.append("Opis je obavezan podatak!\n");
		}
		if (unesiSnaga.getText().isEmpty()) {
			errors.append("Snaga je obavezan podatak!\n");
		}
		if (unesiCijena.getText().isEmpty()) {
			errors.append("Cijena je obavezan podatak!\n");
		}
		if (stanje.getValue() == null) {
			errors.append("Stanje je obavezan podatak!\n");

		}

		if (errors.length() > 0) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setContentText(errors.toString());

			alert.showAndWait();

			return false;
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText("Podaci uspjesno uneseni!");

			alert.showAndWait();
		}

		return true;

	}

}
