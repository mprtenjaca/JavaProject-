package hr.java.vjezbe;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Automobil;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class UnosProdajeController implements Initializable{
	
	@FXML
	private ComboBox<Artikl> comboArtikli;
	@FXML
	private ComboBox<Korisnik> comboKorisnici;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private Button unesiButton;

	ObservableList<Prodaja> observableProdaja;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			populateComboBox();
		} catch (BazaPodatakaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void populateComboBox() throws BazaPodatakaException, IOException {
		
		Prodaja prodaja = null;
		List<Prodaja> listItems = BazaPodataka.dohvatiProdajuPremaKriterijima(prodaja);
		List<Artikl> artikli = new ArrayList<Artikl>();
		List<Korisnik> korisnici = new ArrayList<Korisnik>();
		
		Automobil auto = null;
		Stan stan = null;
		Usluga usluga = null;
		List<Automobil> listAuti = BazaPodataka.dohvatiAutomobilePremaKriterijima(auto);
		List<Stan> listStan = BazaPodataka.dohvatiStanovePremaKriterijima(stan);
		List<Usluga> listUsluga = BazaPodataka.dohvatiUslugePremaKriterijima(usluga);
		
		PrivatniKorisnik privatniKorisnik = null;
		PoslovniKorisnik poslovniKorisnik = null;
		List<PrivatniKorisnik> listPrivatni = BazaPodataka.dohvatiPrivatnogKorisnikaPremaKriterijima(privatniKorisnik);
		List<PoslovniKorisnik> listPoslovni = BazaPodataka.dohvatiPoslovnogKorisnikaPremaKriterijima(poslovniKorisnik);
		
		artikli.addAll(listAuti);
		artikli.addAll(listStan);
		artikli.addAll(listUsluga);
		
		korisnici.addAll(listPrivatni);
		korisnici.addAll(listPoslovni);
		
		comboArtikli.getItems().addAll(artikli);
		comboKorisnici.getItems().addAll(korisnici);
		
	}

	/**
	 * Unosi podatke u bazu podataka
	 * @throws BazaPodatakaException Custom Exception baze podataka
	 * @throws IOException Input/Output Exception
	 */
	public void unesi() throws BazaPodatakaException, IOException{
		
		if(validation()) {
			
			Prodaja prodaja = new Prodaja(comboArtikli.getValue(), comboKorisnici.getValue(), datePicker.getValue());
			BazaPodataka.pohraniNovuProdaju(prodaja);
			
		}
		
	}
	
	
	/**
	 * Validacija comboboxeva
	 * 
	 * @return Vraca true ili false
	 */
	public boolean validation() {

		StringBuilder errors = new StringBuilder();

		if (comboArtikli.getValue() == null) {
			errors.append("Artikl je obavezan podatak!\n");
		}
		if (comboKorisnici.getValue() == null) {
			errors.append("Korisnik je obavezan podatak!\n");
		}
		if (datePicker.getValue() == null) {
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
