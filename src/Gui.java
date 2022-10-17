import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {

	int wiersze, kolumny, connectN;

	@Override
	public void start(Stage stage) throws IOException {
		MenuController mc = new MenuController();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
		// loader.setController(mc);
		Parent menu = loader.load();

		Scene menuScene = new Scene(menu, 500, 500);
		stage.setScene(menuScene);
		stage.show();
	}

	public void pokaz(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
		// loader.setController(mc);
		Parent menu = loader.load();

		Scene menuScene = new Scene(menu, 500, 500);
		stage.setScene(menuScene);
		stage.show();
	}

}
