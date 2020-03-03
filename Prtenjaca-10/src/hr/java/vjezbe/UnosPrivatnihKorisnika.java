package hr.java.vjezbe;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.OptionalLong;
import java.util.ResourceBundle;

import hr.java.vjezbe.entitet.PrivatniKorisnik;
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

public class UnosPrivatnihKorisnika implements Initializable {

	@FXML
	private TextField unesiIme;
	@FXML
	private TextField unesiPrezime;
	@FXML
	private TextField unesiEmail;
	@FXML
	private TextField unesiTelefon;
	@FXML
	private Button unesiButton;
	@FXML
	private BorderPane borderPane;

	ObservableList<PrivatniKorisnik> privatniKorisnici;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Unos i validacija privatnog korisnika
	 * 
	 * @throws IOException           Input/Output Exception
	 * @throws BazaPodatakaException Oznacena custom iznimka baze podataka
	 */
	public void unesi() throws BazaPodatakaException, IOException {

		if (validation()) {
			PrivatniKorisnik privatni1 = null;
			List<PrivatniKorisnik> listItems = BazaPodataka.dohvatiPrivatnogKorisnikaPremaKriterijima(privatni1);
			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
			PrivatniKorisnik privatni = new PrivatniKorisnik(maxId.getAsLong() + 1, unesiIme.getText(), unesiPrezime.getText(), unesiEmail.getText(), unesiTelefon.getText());
			BazaPodataka.pohraniNovogPrivatnigKorisnika(privatni);

//			List<Korisnik> listItems = new ArrayList<Korisnik>();
//			listItems = Datoteke.dohvatiKorisnike();
//			OptionalLong maxId = listItems.stream().mapToLong(Entitet::getId).max();
//			PrivatniKorisnik privatni = new PrivatniKorisnik(maxId.getAsLong() + 1, unesiIme.getText(), unesiPrezime.getText(), unesiEmail.getText(), unesiTelefon.getText());
//			listItems.add(privatni);
//			Datoteke.zapisiDatotekuKorisnika(listItems, 1);
		}
	}

	/**
	 * Validacija text filed-ova
	 * @return Vraca true ili false
	 */
	public boolean validation() {

		StringBuilder errors = new StringBuilder();

		if (unesiIme.getText().isEmpty()) {
			errors.append("Ime je obavezan podatak!\n");
		}
		if (unesiPrezime.getText().isEmpty()) {
			errors.append("Prezime je obavezan podatak!\n");
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
