import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
//		Plansza plansza = new Plansza(7, 6, 4);
//		Wyswietlacz.wyswietlPlansze(plansza);
		inicjacja(1);
	}

	public static boolean inicjacja(int konsola) {
		Plansza plansza = null;
		Zawodnik zawodnik1 = null;
		Zawodnik zawodnik2 = null;

		if (konsola == 1) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Podaj liczbe kolumn");
			int kolumny = scan.nextInt();
			System.out.println("Podaj liczbe wierszy");
			int wiersze = scan.nextInt();
			System.out.println("connect N, podaj N");
			int connectN = scan.nextInt();
			plansza = new Plansza(kolumny, wiersze, connectN);
			int opcja1;
			do {
				System.out.println("Zawodnik1: 1 Czlowiek, 2 Maszyna");
				opcja1 = scan.nextInt();
			} while (opcja1 != 1 && opcja1 != 2);
			if (opcja1 == 1) {

				zawodnik1 = new Czlowiek(1);
			} else {
				System.out.println("Podaj poziom AI");
				int poziom = scan.nextInt();
				zawodnik1 = new Maszyna(1, poziom);
			}

			int opcja2;
			do {
				System.out.println("Zawodnik2: 1 Czlowiek, 2 Maszyna");
				opcja2 = scan.nextInt();
			} while (opcja2 != 1 && opcja2 != 2);
			if (opcja2 == 1) {
				zawodnik2 = new Czlowiek(2);
			} else {
				System.out.println("Podaj poziom AI");
				int poziom = scan.nextInt();
				zawodnik2 = new Maszyna(2, poziom);
			}
			rozgrywka(plansza, zawodnik1, zawodnik2, null);

		} else {
			launch();
		}

		return true;

	}

	public static boolean rozgrywka(Plansza plansza, Zawodnik zawodnik1, Zawodnik zawodnik2, Stage stage) {
		for (int i = 0; i < (plansza.kolumny * plansza.wiersze + 1) / 2; i++) {

///////////////////////////////////////////////////////////////////////////////////////////gracz1
			Wyswietlacz.wyswietlPlansze(plansza);
			if (stage != null) {
				stage.show();
			}
			Ruch aktualnyRuch;
			do {
				aktualnyRuch = zawodnik1.wykonajRuchKonsola(plansza);
				if (aktualnyRuch == null) {
					System.out.println("Gracz1 nie mogl wykonac ruchu");
					return false;
				}

			} while (!plansza.czyMoznaZagrac(aktualnyRuch.indeksKolumny));

			if (plansza.zrobRuch(aktualnyRuch)) {
				if (plansza.czyKoniec(aktualnyRuch)) {
					System.out.println("Wygral gracz 1");
					Wyswietlacz.wyswietlPlansze(plansza);
					return true;
				}
			} else {
				System.out.println("Nie mozna bylo wykonac ruchu");
			}
/////////////////////////////////////////////////////////////////////////////////////////gracz2
			Wyswietlacz.wyswietlPlansze(plansza);
			do {
				aktualnyRuch = zawodnik2.wykonajRuchKonsola(plansza);
				if (aktualnyRuch == null) {
					System.out.println("Gracz2 nie mogl wykonac ruchu");
					return false;
				}
			} while (!plansza.czyMoznaZagrac(aktualnyRuch.indeksKolumny));

			if (plansza.zrobRuch(aktualnyRuch)) {
				if (plansza.czyKoniec(aktualnyRuch)) {
					System.out.println("Wygral gracz 2");
					Wyswietlacz.wyswietlPlansze(plansza);
					return true;
				}
			} else {
				System.out.println("Nie mozna bylo wykonac ruchu");
			}

		}

		System.out.println("Remis!!!");
		Wyswietlacz.wyswietlPlansze(plansza);
		return false;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Gui gui = new Gui();
		gui.start(primaryStage);

	}

}
