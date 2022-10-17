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

	Plansza plansza = null;
	Zawodnik zawodnik1 = null;
	Zawodnik zawodnik2 = null;
	Zawodnik aktualnyGracz = zawodnik1;
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
		System.out.println("Konstruktor domyslny!!!!!!!");
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

	private Parent zrobScene(Plansza plansza) {
		Pane root = new Pane();
		Shape gridShape = zrobPlansze(plansza);
		root.getChildren().add(gridShape);
		root.getChildren().addAll(rysujPlansze(plansza));
		root.getChildren().addAll(wyborKolumny(plansza));
		return root;

	}

	private Shape zrobPlansze(Plansza plansza) {
		Shape shape = new Rectangle(plansza.kolumny * 50, plansza.wiersze * 50);
		for (int i = 0; i < plansza.kolumny; i++) {
			for (int j = 0; j < plansza.wiersze; j++) {
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

	private List<Rectangle> wyborKolumny(Plansza plansza) {
		List<Rectangle> list = new ArrayList<>();
		for (int i = 0; i < plansza.kolumny; i++) {
			Rectangle pole = new Rectangle(50, plansza.wiersze * 50);
			pole.setTranslateX(i * 50);
			pole.setFill(Color.TRANSPARENT);

			pole.setOnMouseEntered(e -> pole.setFill(Color.rgb(100, 100, 100, 0.5)));
			pole.setOnMouseExited(e -> pole.setFill(Color.TRANSPARENT));
			pole.setOnMouseClicked(eventHandler);

			list.add(pole);
		}

		return list;
	}

	private List<Rectangle> rysujPlansze(Plansza plansza) {
		List<Rectangle> list = new ArrayList<>();
		for (int i = 0; i < plansza.kolumny; i++) {
			for (int j = 0; j < plansza.wiersze; j++) {
				Rectangle pole = new Rectangle(40, 40);
				pole.setTranslateX(i * 50 + 5);
				pole.setTranslateY((plansza.wiersze - 1) * 50 - j * 50 + 5);
				if (plansza.plansza[i][j] == 0) {
					pole.setFill(Color.TRANSPARENT);
					pole.setStroke(Color.BLACK);
				} else if (plansza.plansza[i][j] == 1) {
					pole.setFill(Color.RED);
					pole.setStroke(Color.BLACK);
				} else if (plansza.plansza[i][j] == 2) {
					pole.setFill(Color.YELLOW);
					pole.setStroke(Color.BLACK);
				}
				list.add(pole);
			}

		}

		return list;
	}

	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
		Ruch aktualnyRuch;

		@Override
		public void handle(MouseEvent e) {
			Ruch ruch = new Ruch((int) (e.getSceneX() / 50), aktualnyGracz.kolor);
			if (plansza.czyMoznaZagrac(ruch.indeksKolumny)) {

				if (plansza.zrobRuch(ruch)) {
					if (plansza.czyKoniec(ruch)) {
						koniec = true;
						System.out.println("Wygra³ gracz " + aktualnyGracz.kolor);
						Pane root = new Pane();
						Shape gridShape = zrobPlansze(plansza);
						root.getChildren().add(gridShape);
						root.getChildren().addAll(rysujPlansze(plansza));
						stage.setScene(new Scene(root));
						stage.show();
					}

					if (aktualnyGracz == zawodnik1) {
						aktualnyGracz = zawodnik2;
					} else {
						aktualnyGracz = zawodnik1;
					}
				} else {
					System.out.println("Nie mozna bylo wykonac ruchu");
				}
			}
			if (!koniec) {
				if (!koniec) {
					if (aktualnyGracz.czlowiek) {
						rozgrywkaCzlowiek();
					} else {
						try {
							rozgrywkaMaszyna();
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
		plansza = new Plansza(Integer.parseInt(kolumnyTextField.getText()),
				Integer.parseInt(wierszeTextField.getText()), Integer.parseInt(connectNTextField.getText()));
		if (!gracz1ZawodnikButton.isSelected()) {
			zawodnik1 = new Czlowiek(1);
		} else {
			System.out.println("Podaj poziom AI");
			int poziom = Integer.parseInt(gracz1TextField.getText());
			zawodnik1 = new Maszyna(1, poziom);
		}
		if (!gracz2ZawodnikButton.isSelected()) {
			zawodnik2 = new Czlowiek(2);
		} else {
			int poziom = Integer.parseInt(gracz1TextField.getText());
			zawodnik2 = new Maszyna(2, poziom);
		}
		liczbaRund = plansza.kolumny * plansza.wiersze;
		aktualnyGracz = zawodnik1;
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		if (aktualnyGracz.czlowiek) {
			rozgrywkaCzlowiek();
		} else {
			rozgrywkaMaszyna();
		}

	}

	public boolean rozgrywkaMaszyna() throws InterruptedException {

		Pane root = new Pane();
		Shape gridShape = zrobPlansze(plansza);
		root.getChildren().add(gridShape);
		root.getChildren().addAll(rysujPlansze(plansza));

		stage.setScene(new Scene(root));
		stage.show();
		Thread.sleep(1000);

		Ruch ruch = aktualnyGracz.wykonajRuch(plansza);
		if (plansza.czyMoznaZagrac(ruch.indeksKolumny)) {

			if (plansza.zrobRuch(ruch)) {
				if (plansza.czyKoniec(ruch)) {

					koniec = true;
					System.out.println("Wygra³ gracz " + aktualnyGracz.kolor);
					Pane rootKoniec = new Pane();
					Shape gridShapeKoniec = zrobPlansze(plansza);
					rootKoniec.getChildren().add(gridShapeKoniec);
					rootKoniec.getChildren().addAll(rysujPlansze(plansza));
					stage.setScene(new Scene(rootKoniec));
					// stage.show();
				} else if (plansza.czyKoniecRuchow()) {
					koniec = true;
					System.out.println("REMIS!!!!!!!");
					Pane rootKoniec = new Pane();
					Shape gridShapeKoniec = zrobPlansze(plansza);
					rootKoniec.getChildren().add(gridShapeKoniec);
					rootKoniec.getChildren().addAll(rysujPlansze(plansza));
					stage.setScene(new Scene(rootKoniec));
				}

				if (aktualnyGracz == zawodnik1) {
					aktualnyGracz = zawodnik2;
				} else {
					aktualnyGracz = zawodnik1;
				}
			} else {
				System.out.println("Nie mozna bylo wykonac ruchu");
			}
		}
		if (!koniec) {
			if (aktualnyGracz.czlowiek) {
				rozgrywkaCzlowiek();
			} else {
				rozgrywkaMaszyna();
			}

		}
		return true;

	}

	public boolean rozgrywkaCzlowiek() {
		stage.setScene(new Scene(zrobScene(plansza)));
		stage.show();

		return true;
	}

}
