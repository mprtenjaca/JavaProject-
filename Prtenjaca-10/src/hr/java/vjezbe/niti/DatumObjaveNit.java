package hr.java.vjezbe.niti;

import java.util.ArrayList;
import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.util.Duration;

/**
 * Klasa Niti
 * @author Marko
 *
 */
public class DatumObjaveNit implements Runnable {

	

	@Override
	public void run() {
		

		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				Platform.runLater(new DatumObjaveNit() {
					@Override
					public void run() {
						

						Prodaja prodaja = null;
						List<Prodaja> lista = new ArrayList<>();
						try {
							lista = BazaPodataka.dohvatiProdajuPremaKriterijima(prodaja);
						} catch (BazaPodatakaException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setContentText(lista.get(lista.size()-1).getArtikl() + " " + lista.get(lista.size()-1).getKorisnik() + " " + lista.get(lista.size()-1).getDatumObjave());

						alert.showAndWait();
					}
				}); 
			}
		}));
		fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fiveSecondsWonder.play();

	}
}
