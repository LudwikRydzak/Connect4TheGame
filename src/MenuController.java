import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class MenuController {

	Board board = null;
	Player player1 = null;
	Player player2 = null;
	Player currentPlayer = player1;
	Stage stage;
	int liczbaRund;
	@FXML
	TextField kolumnyTextField;

	@FXML
	TextField wierszeTextField;

	@FXML
	TextField connectNTextField;

	@FXML
	TextField gracz1TextField;

	@FXML
	TextField gracz2TextField;

	@FXML
	ToggleButton gracz1ZawodnikButton;

	@FXML
	ToggleButton gracz2ZawodnikButton;

	@FXML
	ToggleButton gracz1AlgorytmButton;

	@FXML
	ToggleButton gracz2AlgorytmButton;

	public MenuController() {
	}

	public void gracz1onActionButton(ActionEvent event) {
		if (gracz1ZawodnikButton.isSelected()) {
			gracz1TextField.setDisable(false);
			gracz1AlgorytmButton.setDisable(false);
		} else {
			gracz1TextField.setDisable(true);
			gracz1AlgorytmButton.setDisable(true);
		}
	}

	public void gracz2onActionButton(ActionEvent event) {
		if (gracz2ZawodnikButton.isSelected()) {
			gracz2TextField.setDisable(false);
			gracz2AlgorytmButton.setDisable(false);
		} else {
			gracz2TextField.setDisable(true);
			gracz2AlgorytmButton.setDisable(true);
		}
	}


	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
		Move currentMove;

		@Override
		public void handle(MouseEvent e) {
			currentMove = new Move((int) (e.getSceneX() / 50), currentPlayer.colour);
		}
	};

	public void onAction(ActionEvent event) {
		board = new Board(Integer.parseInt(kolumnyTextField.getText()),
				Integer.parseInt(wierszeTextField.getText()), Integer.parseInt(connectNTextField.getText()));
		if (!gracz1ZawodnikButton.isSelected()) {
			player1 = new HumanPlayer(1);
		} else {
			System.out.println("Podaj strength AI");
			int poziom = Integer.parseInt(gracz1TextField.getText());
			player1 = new ComputerPlayer(1, poziom);
		}
		if (!gracz2ZawodnikButton.isSelected()) {
			player2 = new HumanPlayer(2);
		} else {
			int poziom = Integer.parseInt(gracz1TextField.getText());
			player2 = new ComputerPlayer(2, poziom);
		}
		liczbaRund = board.columns * board.rows;
		currentPlayer = player1;
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		if (currentPlayer.ifHumanPlayer) {
			gameplayHuman();
		} else {
			gameplayAI();
		}

	}

	public boolean gameplayHuman() {
		stage.setScene(new Scene(makeScene(board)));
		stage.show();

		return true;
	}

}
