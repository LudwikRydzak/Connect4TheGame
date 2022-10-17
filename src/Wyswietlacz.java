
public class Wyswietlacz {

	public static void wyswietlPlansze(Plansza plansza) {
		for (int i = plansza.wiersze - 1; i >= 0; i--) {
			for (int n = 0; n < plansza.kolumny; n++) {
				System.out.print("----");
			}
			System.out.print("--");
			System.out.println();
			for (int j = 0; j < plansza.kolumny; j++) {
				System.out.print(" | ");
				System.out.print(plansza.plansza[j][i]);

			}
			System.out.println(" |");
		}
		for (int n = 0; n < plansza.kolumny; n++) {
			System.out.print("----");
		}
		System.out.println("--");
	}
}
