package hr.java.vjezbe;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Entitet;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class UnosPoslovnihKorisnika implements Initializable {

	@FXML
	private TextField unesiNaziv;
	@FXML
	private TextField unesiWeb;
	@FXML
	private TextField unesiEmail;
	@FXML
	private TextField unesiTelefon;
	@FXML
	private Button unesiButton;
	@FXML
	private BorderPane borderPane;

	ObservableList<PoslovniKorisnik> privatniKorisnici;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Unos poslovnog korisnika
	 * 
	 * @throws IOException           Input/Output Exception
	 * @throws BazaPodatakaException Oznacena custom iznimka baze podataka
	 */
	public void unesi() throws BazaPodatakaException, IOException {

		if (validation()) {
			PoslovniKorisnik poslovni1 = null;
			List<PoslovniKorisnik> listItems = BazaPodataka.dohvatiPoslovnogKorisnikaPremaKriterijima(poslovni1);
			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
			PoslovniKorisnik poslovni = new PoslovniKorisnik(maxId.getAsLong() + 1, unesiNaziv.getText(),
					unesiWeb.getText(), unesiEmail.getText(), unesiTelefon.getText());
			BazaPodataka.pohraniNovogPoslovnogKorisnika(poslovni);

//			List<Korisnik> listItems = new ArrayList<Korisnik>();
//			listItems = Datoteke.dohvatiKorisnike();
//			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
//			PoslovniKorisnik poslovni = new PoslovniKorisnik(maxId.getAsLong() + 1, unesiNaziv.getText(), unesiWeb.getText(), unesiEmail.getText(), unesiTelefon.getText());
//			listItems.add(poslovni);
//			Datoteke.zapisiDatotekuKorisnika(listItems, 2);
		}
	}

	/**
	 * Validacija text filed-ova
	 * 
	 * @return Vraca true ili false
	 */
	public boolean validation() {

		StringBuilder errors = new StringBuilder();

		if (unesiNaziv.getText().isEmpty()) {
			errors.append("Naziv je obavezan podatak!\n");
		}
		if (unesiWeb.getText().isEmpty()) {
			errors.append("Web je obavezan podatak!\n");
		}
		if (unesiEmail.getText().isEmpty()) {
			errors.append("Email je obavezan podatak!\n");
		}
		if (unesiTelefon.getText().isEmpty()) {
			errors.append("Telefon je obavezan podatak!\n");
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
