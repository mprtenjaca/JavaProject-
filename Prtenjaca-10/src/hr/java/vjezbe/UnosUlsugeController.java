package hr.java.vjezbe;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.entitet.Entitet;
import hr.java.vjezbe.entitet.Stanje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class UnosUlsugeController implements Initializable {

	@FXML
	private TextField unesiNaslov;
	@FXML
	private TextField unesiOpis;
	@FXML
	private TextField unesiCijena;
	@FXML
	private ComboBox<Stanje> stanje;
	@FXML
	private Button unesiButton;
	@FXML
	private BorderPane borderPane;

	ObservableList<Usluga> usluge;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ObservableList<Stanje> stanjeList = FXCollections.observableArrayList(Stanje.values());
		stanje.setItems(stanjeList);

	}

	/**
	 * Unos usluge
	 * 
	 * @throws IOException           Input/Output Exception
	 * @throws BazaPodatakaException Oznacena custom iznimka baze podataka
	 */
	public void unesi() throws BazaPodatakaException, IOException {

		if (validation()) {

			Usluga usluga1 = null;
			List<Usluga> listItems = BazaPodataka.dohvatiUslugePremaKriterijima(usluga1);
			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
			Usluga usluga = new Usluga(maxId.getAsLong() + 1, unesiNaslov.getText(), unesiOpis.getText(),
					new BigDecimal(unesiCijena.getText()), stanje.getValue());
			BazaPodataka.pohraniNovuUslugu(usluga);

//			List<Artikl> listItems = new ArrayList<Artikl>();
//			listItems = Datoteke.dohvatiArtikle();
//			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
//			Usluga auto = new Usluga(maxId.getAsLong() + 1, unesiNaslov.getText(), unesiOpis.getText(), new BigDecimal(unesiCijena.getText()), stanje.getValue());
//			listItems.add(auto);
//			Datoteke.zapisiDatotekuArtikala(listItems, 1);
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
