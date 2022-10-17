
public class Ruch {

	public int indeksWiersza;
	public int indeksKolumny;
	public int kolor;

	public Ruch(int indeksKolumny, int indeksWiersza, int kolor) {
		this.indeksKolumny = indeksKolumny;
		this.indeksWiersza = indeksWiersza;
		this.kolor = kolor;
	}

	public Ruch(int indeksKolumny, int kolor) {
		this.indeksKolumny = indeksKolumny;
		this.kolor = kolor;
	}
}
