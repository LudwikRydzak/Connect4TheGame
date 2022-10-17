import java.util.ArrayList;
import java.util.Collections;

public class Maszyna extends Zawodnik {
	int poziom;

	public Maszyna(int kolor, int poziom) {
		super(kolor);
		this.poziom = poziom;
		czlowiek = false;
	}

	public Ruch wykonajRuch(Plansza plansza) {
		return wykonajRuchKonsola(plansza);
	}

	@Override
	public Ruch wykonajRuchKonsola(Plansza plansza) {
		ArrayList<Ruch> najlepszyRuch = new ArrayList<Ruch>();
		int wartoscNajlepszegoRuchu = Integer.MIN_VALUE;
		if (kolor == 2) {
			wartoscNajlepszegoRuchu = Integer.MAX_VALUE;
		}
		int wartoscRuchu;
		for (int i = 0; i < plansza.kolumny; i++) {
			if (plansza.czyMoznaZagrac(i)) {
				Ruch ruch = new Ruch(i, this.kolor);
				Plansza nowaPlansza = new Plansza(plansza);
				nowaPlansza.zrobRuch(ruch);
				wartoscRuchu = minmax(nowaPlansza, ruch, poziom, (kolor ^ 3) == 1, kolor ^ 3);
				if (kolor == 1) {
					if (wartoscRuchu > wartoscNajlepszegoRuchu) {
						wartoscNajlepszegoRuchu = wartoscRuchu;
						najlepszyRuch.clear();

					}

					if (wartoscRuchu == wartoscNajlepszegoRuchu) {
						najlepszyRuch.add(ruch);
					}
				} else {
					if (wartoscRuchu < wartoscNajlepszegoRuchu) {
						wartoscNajlepszegoRuchu = wartoscRuchu;
						najlepszyRuch.clear();

					}

					if (wartoscRuchu == wartoscNajlepszegoRuchu) {
						najlepszyRuch.add(ruch);
					}
				}

			}
		}
		if (!najlepszyRuch.isEmpty()) {
			Collections.shuffle(najlepszyRuch);
			System.out.println("Obliczona wartosc: " + wartoscNajlepszegoRuchu);
			return najlepszyRuch.get(0);
		}
		return null;
	}

	public int minmax(Plansza plansza, Ruch ruch, int glebokosc, boolean maksymalizowanieGracza, int kolor) {
		int wartosc = funkcjaOceny(plansza);

		if (glebokosc <= 0 || plansza.czyKoniec(ruch) || plansza.czyKoniecRuchow()) {
			System.out.println(
					"Obliczona funckja oceny: " + wartosc + " glebokosc: " + glebokosc + " ruch" + ruch.indeksKolumny);
			return wartosc;
		}
		if (maksymalizowanieGracza) {
			wartosc = -100000000;// sto milionow

			for (int i = 0; i < plansza.kolumny; i++) {
				if (plansza.czyMoznaZagrac(i)) {
					Plansza nowaPlansza = new Plansza(plansza);
					Ruch nowyRuch = new Ruch(i, kolor);
					nowaPlansza.zrobRuch(nowyRuch);
					wartosc = Math.max(wartosc, minmax(nowaPlansza, nowyRuch, glebokosc - 1, false, kolor ^ 3));
				}
			}
			System.out.println("Obliczona ostatecznie funckja oceny: " + wartosc + " glebokosc: " + glebokosc);
			return wartosc;
		} else {
			wartosc = 100000000;// sto milionow

			for (int i = 0; i < plansza.kolumny; i++) {
				if (plansza.czyMoznaZagrac(i)) {
					Plansza nowaPlansza = new Plansza(plansza);
					Ruch nowyRuch = new Ruch(i, kolor);
					nowaPlansza.zrobRuch(nowyRuch);
					wartosc = Math.min(wartosc, minmax(nowaPlansza, nowyRuch, glebokosc - 1, true, kolor ^ 3));
				}
			}
			System.out.println("Obliczona ostatecznie funckja oceny: " + wartosc + " glebokosc: " + glebokosc);
			return wartosc;
		}
	}

	public int funkcjaOceny(Plansza plansza) {
		int sumaOceny = 0;

		sumaOceny += sprawdzenieKolumn(plansza, 1);
		sumaOceny -= sprawdzenieKolumn(plansza, 2);
		sumaOceny += sprawdzenieWiersza(plansza, 1);
		sumaOceny -= sprawdzenieWiersza(plansza, 2);
		sumaOceny += sprawdzeniePrzekatnej1(plansza, 1);
		sumaOceny -= sprawdzeniePrzekatnej1(plansza, 2);
		sumaOceny += sprawdzeniePrzekatnej2(plansza, 1);
		sumaOceny -= sprawdzeniePrzekatnej2(plansza, 2);

		return sumaOceny;

	}

	private int sprawdzenieKolumn(Plansza plansza, int kolor) {
		int licznikMozliwosci = 0;
		int licznikFaktycznych = 0;
		int suma = 0;
		for (int i = 0; i < plansza.kolumny; i++) {
			for (int j = 0; j < plansza.wiersze; j++) {
				if (plansza.plansza[i][j] == kolor) {
					licznikMozliwosci++;
					licznikFaktycznych++;
				} else if (plansza.plansza[i][j] == 0) {
					licznikMozliwosci++;
				} else {
					if (licznikMozliwosci >= plansza.connectN) {
						suma++;
					}
					if (licznikFaktycznych >= plansza.connectN) {
						suma += 1000000;
					}
					licznikMozliwosci = 0;
					licznikFaktycznych = 0;
				}
			}
			if (licznikMozliwosci >= plansza.connectN) {
				suma++;
			}
			if (licznikFaktycznych >= plansza.connectN) {
				suma += 1000000;
			}
			licznikMozliwosci = 0;
			licznikFaktycznych = 0;
		}
		return suma;
	}

	private int sprawdzenieWiersza(Plansza plansza, int kolor) {
		int licznikMozliwosci = 0;
		int licznikFaktycznych = 0;
		int suma = 0;
		for (int i = 0; i < plansza.wiersze; i++) {
			for (int j = 0; j < plansza.kolumny; j++) {
				if (plansza.plansza[j][i] == kolor) {
					licznikMozliwosci++;
					licznikFaktycznych++;
				} else if (plansza.plansza[j][i] == 0) {
					licznikMozliwosci++;
				} else {
					if (licznikMozliwosci >= plansza.connectN) {
						suma++;
					}
					if (licznikFaktycznych >= plansza.connectN) {
						suma += 1000000;
					}
					licznikMozliwosci = 0;
					licznikFaktycznych = 0;
				}
			}
			if (licznikMozliwosci >= plansza.connectN) {
				suma++;
			}
			if (licznikFaktycznych >= plansza.connectN) {
				suma += 1000000;
			}
			licznikMozliwosci = 0;
			licznikFaktycznych = 0;
		}
		return suma;
	}

	private int sprawdzeniePrzekatnej1(Plansza plansza, int kolor) {
		int licznikMozliwosci = 0;
		int licznikFaktycznych = 0;
		int suma = 0;
		for (int i = 0; i < plansza.kolumny + plansza.wiersze - 1; i++) {
			for (int j = 0; j < plansza.wiersze; j++) {
				if (i - j >= 0 && i - j < plansza.kolumny) {
					if (plansza.plansza[i - j][j] == kolor) {
						licznikMozliwosci++;
						licznikFaktycznych++;
					} else if (plansza.plansza[i - j][j] == 0) {

					} else {
						if (licznikMozliwosci >= plansza.connectN) {
							suma++;
						}
						if (licznikFaktycznych >= plansza.connectN) {
							suma += 1000000;
						}
						licznikMozliwosci = 0;
						licznikFaktycznych = 0;
					}
				} else {
					if (licznikMozliwosci >= plansza.connectN) {
						suma++;
					}
					if (licznikFaktycznych >= plansza.connectN) {
						suma += 1000000;
					}
					licznikMozliwosci = 0;
					licznikFaktycznych = 0;
				}
			}

		}
		return suma;
	}

	private int sprawdzeniePrzekatnej2(Plansza plansza, int kolor) {
		int licznikMozliwosci = 0;
		int licznikFaktycznych = 0;
		int suma = 0;
		for (int i = 0; i < plansza.kolumny + plansza.wiersze - 1; i++) {
			for (int j = 0; j < plansza.wiersze; j++) {
				if (i - plansza.wiersze + 1 + j >= 0 && i - plansza.wiersze + 1 + j < plansza.kolumny) {
					if (plansza.plansza[i - plansza.wiersze + 1 + j][j] == kolor) {
						licznikMozliwosci++;
						licznikFaktycznych++;
					} else if (plansza.plansza[i - plansza.wiersze + 1 + j][j] == 0) {

					} else {
						if (licznikMozliwosci >= plansza.connectN) {
							suma++;
						}
						if (licznikFaktycznych >= plansza.connectN) {
							suma += 1000000;
						}
						licznikMozliwosci = 0;
						licznikFaktycznych = 0;
					}
				} else {
					if (licznikMozliwosci >= plansza.connectN) {
						suma++;
					}
					if (licznikFaktycznych >= plansza.connectN) {
						suma += 1000000;
					}
					licznikMozliwosci = 0;
					licznikFaktycznych = 0;
				}
			}

		}
		return suma;
	}

}
