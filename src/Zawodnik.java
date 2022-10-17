
public abstract class Zawodnik {
	int kolor;
	Ruch aktualnyRuch;
	Boolean czlowiek;

	public Zawodnik(int kolor) {
		this.kolor = kolor;
	}

	public abstract Ruch wykonajRuchKonsola(Plansza plansza);

	public abstract Ruch wykonajRuch(Plansza plansza);
}
