package hr.java.vjezbe;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Stan;
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

public class UnosStanovaController implements Initializable {

	@FXML
	private TextField unesiNaslov;
	@FXML
	private TextField unesiOpis;
	@FXML
	private TextField unesiKvadraturu;
	@FXML
	private TextField unesiCijena;
	@FXML
	private ComboBox<Stanje> stanje;
	@FXML
	private Button unesiButton;
	@FXML
	private BorderPane borderPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<Stanje> stanjeList = FXCollections.observableArrayList(Stanje.values());
		stanje.setItems(stanjeList);

	}

	/**
	 * Unos stanova
	 * 
	 * @throws IOException           Input/Output Exception
	 * @throws BazaPodatakaException Oznacena custom iznimka baze podataka
	 */
	public void unesi() throws BazaPodatakaException, IOException {

		if (validation()) {
			Stan stan1 = null;
			List<Stan> listItems = BazaPodataka.dohvatiStanovePremaKriterijima(stan1);
			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
			Stan stan = new Stan(maxId.getAsLong() + 1, unesiNaslov.getText(), unesiOpis.getText(),
					new BigDecimal(unesiCijena.getText()), stanje.getValue(),
					Integer.parseInt(unesiKvadraturu.getText()));
			BazaPodataka.pohraniNoviStan(stan);
//			List<Artikl> listItems = Datoteke.dohvatiArtikle();
//			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
//			Stan stan = new Stan(maxId.getAsLong() + 1, unesiNaslov.getText(), unesiOpis.getText(), new BigDecimal(unesiCijena.getText()), stanje.getValue(), Integer.parseInt(unesiKvadraturu.getText()));
//			listItems.add(stan);
//			Datoteke.zapisiDatotekuArtikala(listItems, 3);
		}
	}

	/**
	 * Validacija tex filed-ova
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
		if (unesiKvadraturu.getText().isEmpty()) {
			errors.append("Kvadratura je obavezan podatak!\n");
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
