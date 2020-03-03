package hr.java.vjezbe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.niti.DatumObjaveNit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main metoda koja u kojoj se poziva program
 * @author Marko Prtenjaca
 * @version 1.0
 */
public class Main extends Application {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	Stage pomStage;

	public static void main(String[] args) {
		logger.info("Program se pocinje izvoditi");
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("Prtenjaca.fxml"));
			Scene scene = new Scene(root, 600, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			DatumObjaveNit nit = new DatumObjaveNit();
			
			ExecutorService ex = Executors.newFixedThreadPool(1);
			
			ex.execute(nit);
			
			ex.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
