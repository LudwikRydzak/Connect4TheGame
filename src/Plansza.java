
public class Plansza {
	int[][] plansza;
	int kolumny;
	int wiersze;
	int connectN;

	public Plansza(int kolumny, int wiersze, int connectN) {
		this.plansza = new int[kolumny][wiersze];
		this.kolumny = kolumny;
		this.wiersze = wiersze;
		this.connectN = connectN;
	}

	public Plansza(Plansza inna) {
		this.kolumny = inna.kolumny;
		this.wiersze = inna.wiersze;
		this.connectN = inna.connectN;
		this.plansza = new int[kolumny][wiersze];

		for (int i = 0; i < this.kolumny; i++) {
			for (int j = 0; j < this.wiersze; j++) {
				this.plansza[i][j] = inna.plansza[i][j];
			}
		}
	}

	public boolean zrobRuch(Ruch ruch) {
		for (int i = 0; i < this.wiersze; i++) {
			if (this.plansza[ruch.indeksKolumny][i] == 0) {
				this.plansza[ruch.indeksKolumny][i] = ruch.kolor;
				ruch.indeksWiersza = i;
				return true;
			}
		}
		return false;
	}

	public boolean czyKoniecRuchow() {
		for (int i = 0; i < this.kolumny; i++) {
			if (this.plansza[i][this.wiersze - 1] == 0) {
				return false;
			}
		}
		return true;
	}

	public boolean czyKoniec(Ruch ruch) {
		return sprawdzenieKolumny(ruch) || sprawdzenieWiersza(ruch) || sprawdzeniePrzekatnej1(ruch)
				|| sprawdzeniePrzekatnej2(ruch);
	}

	public boolean czyMoznaZagrac(int indeksKolumny) {
		if (indeksKolumny < 0 || indeksKolumny >= kolumny) {
			return false;
		}
		if (plansza[indeksKolumny][wiersze - 1] == 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean sprawdzenieKolumny(Ruch ruch) {
		int licznik = 0;
		for (int i = 0; i < this.wiersze; i++) {
			if (this.plansza[ruch.indeksKolumny][i] == ruch.kolor) {
				licznik++;
				if (licznik == connectN) {
					return true;
				}
			} else {
				licznik = 0;
			}
		}
		return false;
	}

	private boolean sprawdzenieWiersza(Ruch ruch) {
		int licznik = 0;
		for (int i = 0; i < this.kolumny; i++) {
			if (this.plansza[i][ruch.indeksWiersza] == ruch.kolor) {
				licznik++;
				if (licznik == connectN) {
					return true;
				}
			} else {
				licznik = 0;
			}
		}
		return false;
	}

	private boolean sprawdzeniePrzekatnej1(Ruch ruch) {
		int licznik = 0;
		for (int i = 0; i <= ruch.indeksKolumny + ruch.indeksWiersza; i++) {
			if (i < this.kolumny && ruch.indeksKolumny + ruch.indeksWiersza - i < this.wiersze) {
				if (this.plansza[i][ruch.indeksKolumny + ruch.indeksWiersza - i] == ruch.kolor) {
					licznik++;
					if (licznik == connectN) {
						return true;
					}
				} else {
					licznik = 0;
				}
			}
		}
		return false;
	}

	private boolean sprawdzeniePrzekatnej2(Ruch ruch) {
		int licznik = 0;
		for (int i = 0; i < this.wiersze; i++) {
			if (ruch.indeksKolumny - ruch.indeksWiersza + i < this.kolumny
					&& ruch.indeksKolumny - ruch.indeksWiersza + i >= 0) {
				if (this.plansza[ruch.indeksKolumny - ruch.indeksWiersza + i][i] == ruch.kolor) {
					licznik++;
					if (licznik == connectN) {
						return true;
					}
				} else {
					licznik = 0;
				}
			}
		}
		return false;
	}
}