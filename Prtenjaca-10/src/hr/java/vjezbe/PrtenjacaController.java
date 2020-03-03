package hr.java.vjezbe;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;

/**
 * Klasa odgovorna za pocetnu scenu
 * 
 * @author Marko
 *
 */
public class PrtenjacaController {

	@FXML
	MenuBar mainMenuBar;
	@FXML
	private BorderPane mainBorderPane;

	/**
	 * Prikazuje fxml za automobile
	 * 
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziPretraguAutomobila(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Automobili.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}

	/**
	 * Prikazuje fxml za stanove
	 * 
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziPretraguStanova(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Stanovi.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}

	/**
	 * Prikazuje fxml za usluge
	 * 
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziPretraguUsluga(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("Usluge.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}

	/**
	 * Prikazuje fxml za privatne korisnike
	 * 
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziPretraguPrivatnihKorisnika(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("PrivatniKorisnici.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}

	/**
	 * Prikazuje fxml za poslovne korisnike
	 * 
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziPretraguPoslovnihKorisnika(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("PoslovniKorisnici.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}
	
	
	/**
	 * Prikazuje scenu za unos automobila
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziUnosAutomobila(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("UnosAutomobila.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}
	
	/**
	 * Prikazuje scenu za unos stanova
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziUnosStanova(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("UnosStanova.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}
	
	/**
	 * Prikazuje scenu za unos usluge
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziUnosUsluge(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("UnosUsluge.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}
	
	/**
	 * Prikazuje scenu za unos privatnih korisnika
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziUnosPrivatniKorisnika(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("UnosPrivatnihKorisnika.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}
	
	/**
	 * Prikazuje scenu za unos poslovnih korisnika
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziUnosPoslovnihKorisnika(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("UnosPoslovnihKorisnika.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}
	
	
	
	/**
	 * Prikazuje fxml za prodaju
	 * 
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziPregledProdaje(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("PrtragaProdaje.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}
	
	/**
	 * Prikazuje scenu za unos prodaje
	 * @param event Event koji se desi kad se pozove funkcija
	 * @throws Exception Iznimka koja sluzi da uhvati greske
	 */
	@FXML
	private void prikaziUnosProdaje(ActionEvent event) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("UnosProdaje.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainBorderPane.setCenter(root);
	}

}