import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
		makeScene(board);
	}

	@Override
	public void run() {
		displayBoard(Gameplay.getCurrentBoard());
	}

	private Parent makeScene(Board board) {
		Pane root = new Pane();
		Shape gridShape = createBoard(board);
		root.getChildren().add(gridShape);
		root.getChildren().addAll(drawBoard(board));
		root.getChildren().addAll(columnChoice(board));
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

	private List<Rectangle> columnChoice(Board board) {
		List<Rectangle> list = new ArrayList<>();
		for (int i = 0; i < board.columns; i++) {
			Rectangle tile = new Rectangle(50, board.rows * 50);
			tile.setTranslateX(i * 50);
			tile.setFill(Color.TRANSPARENT);

			tile.setOnMouseEntered(e -> tile.setFill(Color.rgb(100, 100, 100, 0.5)));
			tile.setOnMouseExited(e -> tile.setFill(Color.TRANSPARENT));
//			tile.setOnMouseClicked(eventHandler);

			list.add(tile);
		}

		return list;
	}

	private List<Rectangle> drawBoard(Board board) {
		List<Rectangle> list = new ArrayList<>();
		for (int i = 0; i < board.columns; i++) {
			for (int j = 0; j < board.rows; j++) {
				Rectangle tile = new Rectangle(40, 40);
				tile.setTranslateX(i * 50 + 5);
				tile.setTranslateY((board.rows - 1) * 50 - j * 50 + 5);
				if (board.board[i][j] == 0) {
					tile.setFill(Color.TRANSPARENT);
					tile.setStroke(Color.BLACK);
				} else if (board.board[i][j] == 1) {
					tile.setFill(Color.RED);
					tile.setStroke(Color.BLACK);
				} else if (board.board[i][j] == 2) {
					tile.setFill(Color.YELLOW);
					tile.setStroke(Color.BLACK);
				}
				list.add(tile);
			}

		}

		return list;
	}
}
