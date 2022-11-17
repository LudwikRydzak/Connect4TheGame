import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiController extends Application implements Display {

	int wiersze, kolumny, connectN;

	@Override
	public void start(Stage stage) throws IOException {
		MenuController mc = new MenuController();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
		// loader.setController(mc);
		Parent menu = loader.load();

		Scene menuScene = new Scene(menu, 250, 280);
		stage.setScene(menuScene);
		stage.show();
	}

	@Override
	public void displayBoard(Board board) {

	}
}
