import java.util.Scanner;

public class Czlowiek extends Zawodnik {

	public Czlowiek(int kolor) {
		super(kolor);
		czlowiek = true;
	}

	public Ruch wykonajRuch(Plansza plansza) {
		return wykonajRuchKonsola(plansza);
	}

	public Ruch wykonajRuchKonsola(Plansza plansza) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Podaj kolumne");
		int kolumna = scan.nextInt();
		aktualnyRuch = new Ruch(kolumna, kolor);
		return aktualnyRuch;
	}
}
