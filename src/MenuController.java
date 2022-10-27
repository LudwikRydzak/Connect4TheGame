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
	boolean koniec = false;
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

	private Parent makeScene(Board board) {
		Pane root = new Pane();
		Shape gridShape = createBoard(board);
		root.getChildren().add(gridShape);
		root.getChildren().addAll(rysujPlansze(board));
		root.getChildren().addAll(wyborKolumny(board));
		return root;

	}

	private Shape createBoard(Board board) {
		Shape shape = new Rectangle(board.columns * 50, board.rows * 50);
		for (int i = 0; i < board.columns; i++) {
			for (int j = 0; j < board.rows; j++) {
				Rectangle pole = new Rectangle(40, 40);
				pole.setStroke(Color.BLACK);
				pole.setTranslateX(i * 50 + 5);
				pole.setTranslateY(j * 50 + 5);
				shape = Shape.subtract(shape, pole);
			}
		}
		shape.setFill(Color.rgb(200, 200, 50, 0.3));
		return shape;
	}

	private List<Rectangle> wyborKolumny(Board board) {
		List<Rectangle> list = new ArrayList<>();
		for (int i = 0; i < board.columns; i++) {
			Rectangle pole = new Rectangle(50, board.rows * 50);
			pole.setTranslateX(i * 50);
			pole.setFill(Color.TRANSPARENT);

			pole.setOnMouseEntered(e -> pole.setFill(Color.rgb(100, 100, 100, 0.5)));
			pole.setOnMouseExited(e -> pole.setFill(Color.TRANSPARENT));
			pole.setOnMouseClicked(eventHandler);

			list.add(pole);
		}

		return list;
	}

	private List<Rectangle> rysujPlansze(Board board) {
		List<Rectangle> list = new ArrayList<>();
		for (int i = 0; i < board.columns; i++) {
			for (int j = 0; j < board.rows; j++) {
				Rectangle pole = new Rectangle(40, 40);
				pole.setTranslateX(i * 50 + 5);
				pole.setTranslateY((board.rows - 1) * 50 - j * 50 + 5);
				if (board.board[i][j] == 0) {
					pole.setFill(Color.TRANSPARENT);
					pole.setStroke(Color.BLACK);
				} else if (board.board[i][j] == 1) {
					pole.setFill(Color.RED);
					pole.setStroke(Color.BLACK);
				} else if (board.board[i][j] == 2) {
					pole.setFill(Color.YELLOW);
					pole.setStroke(Color.BLACK);
				}
				list.add(pole);
			}

		}

		return list;
	}

	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
		Move aktualnyMove;

		@Override
		public void handle(MouseEvent e) {
			Move move = new Move((int) (e.getSceneX() / 50), currentPlayer.colour);
			if (board.ifMovePossible(move.columnIndex)) {

				if (board.makeMove(move)) {
					if (board.ifEnd(move)) {
						koniec = true;
						System.out.println("Wygra³ gracz " + currentPlayer.colour);
						Pane root = new Pane();
						Shape gridShape = createBoard(board);
						root.getChildren().add(gridShape);
						root.getChildren().addAll(rysujPlansze(board));
						stage.setScene(new Scene(root));
						stage.show();
					}

					if (currentPlayer == player1) {
						currentPlayer = player2;
					} else {
						currentPlayer = player1;
					}
				} else {
					System.out.println("Nie mozna bylo wykonac ruchu");
				}
			}
			if (!koniec) {
				if (!koniec) {
					if (currentPlayer.ifHumanPlayer) {
						gameplayHuman();
					} else {
						try {
							gameplayAI();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			}
		}
	};

	public void onAction(ActionEvent event) throws IOException, InterruptedException {
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

	public boolean gameplayAI() throws InterruptedException {

		Pane root = new Pane();
		Shape gridShape = createBoard(board);
		root.getChildren().add(gridShape);
		root.getChildren().addAll(rysujPlansze(board));

		stage.setScene(new Scene(root));
		stage.show();
		Thread.sleep(1000);

		Move move = currentPlayer.makeMove(board);
		if (board.ifMovePossible(move.columnIndex)) {

			if (board.makeMove(move)) {
				if (board.ifEnd(move)) {

					koniec = true;
					System.out.println("Wygra³ gracz " + currentPlayer.colour);
					Pane rootEnd = new Pane();
					Shape gridShapeKoniec = createBoard(board);
					rootEnd.getChildren().add(gridShapeKoniec);
					rootEnd.getChildren().addAll(rysujPlansze(board));
					stage.setScene(new Scene(rootEnd));
					// stage.show();
				} else if (board.ifNoMoreMoves()) {
					koniec = true;
					System.out.println("REMIS!!!!!!!");
					Pane rootEnd = new Pane();
					Shape gridShapeKoniec = createBoard(board);
					rootEnd.getChildren().add(gridShapeKoniec);
					rootEnd.getChildren().addAll(rysujPlansze(board));
					stage.setScene(new Scene(rootEnd));
				}

				if (currentPlayer == player1) {
					currentPlayer = player2;
				} else {
					currentPlayer = player1;
				}
			} else {
				System.out.println("Nie mozna bylo wykonac ruchu");
			}
		}
		if (!koniec) {
			if (currentPlayer.ifHumanPlayer) {
				gameplayHuman();
			} else {
				gameplayAI();
			}

		}
		return true;

	}

	public boolean gameplayHuman() {
		stage.setScene(new Scene(makeScene(board)));
		stage.show();

		return true;
	}

}
