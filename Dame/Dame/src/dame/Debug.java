package dame;

import java.util.Scanner;

public class Debug {

    // Arrays zur Speicherung der schwarzen und weißen Figuren
    private static Figuren[] schwarzeFiguren;
    private static Figuren[] weißeFiguren;

    // Scanner-Objekte zur Eingabe von Benutzerdaten
    Scanner scanner = new Scanner(System.in);
    Scanner scanner2 = new Scanner(System.in);
    Scanner scanner3 = new Scanner(System.in);

    // Zähler für die Anzahl der gesetzten weißen und schwarzen Figuren
    private int weiß = 0;
    private int schwarz = 0;
    
    // Kontrollvariable, um die Platzierung von Figuren zu steuern
    private boolean placeFiguren = true;

    // Konstruktor der Debug-Klasse
    Debug() {
        // Initialisiert die Arrays für die Figuren
        schwarzeFiguren = new Figuren[12];
        weißeFiguren = new Figuren[12];

        // Erstellt ein neues Spielfeld und setzt es zurück
        new Board();
        Board.feldReset();

        // Schleife zur Platzierung der Figuren auf dem Brett
        while (placeFiguren) {
            SetFiguren();
        }

        // Setzt die verbleibenden Steine auf das Spielfeld
        setzeRestlicheSteine(weiß, schwarz);

        // Initialisiert die Kontrolle für das Damenspiel
        new DameControl(true);
        DameControl.setSchwarzeFiguren(schwarzeFiguren);
        DameControl.setWeißeFiguren(weißeFiguren);

        // Zeigt das Spielfeld zur Überprüfung
        DameControl.BoardDebug();
    }

    // Methode zur Auswahl und Platzierung der Figuren auf dem Brett
    private void SetFiguren() {

        System.out.println("Welche Figur willst du setzen");
        System.out.println("1 : weißer Stein");
        System.out.println("2 : schwarzer Stein");
        System.out.println("3 : weiße Dame");
        System.out.println("4 : schwarze Dame");
        System.out.println("5 : Spiel simulieren");

        int input = scanner.nextInt();

        int zeile;
        int spalte;

        // Auswahl der Figur basierend auf der Benutzereingabe
        switch (input) {
            case 1:
                // Platzierung eines weißen Steins
                System.out.println("Gib Zeile an:");
                zeile = scanner2.nextInt();
                System.out.println("Gib die Spalte an:");
                spalte = scanner3.nextInt();
                if (checkSchwarzeFarbe(zeile, spalte)) {
                    platziereWeißenStein(zeile, spalte, weiß);
                    weiß++;
                } else {
                    System.out.println("kein schwarzes Feld, Figur wurde nicht platziert");
                }
                break;
            case 2:
                // Platzierung eines schwarzen Steins
                System.out.println("Gib Zeile an:");
                zeile = scanner2.nextInt();
                System.out.println("Gib die Spalte an:");
                spalte = scanner3.nextInt();
                if (checkSchwarzeFarbe(zeile, spalte)) {
                    platziereSchwarzenStein(zeile, spalte, schwarz);
                    schwarz++;
                } else {
                    System.out.println("kein schwarzes Feld, Figur wurde nicht platziert");
                }
                break;
            case 3:
                // Platzierung einer weißen Dame
                System.out.println("Gib Zeile an:");
                zeile = scanner2.nextInt();
                System.out.println("Gib die Spalte an:");
                spalte = scanner3.nextInt();
                if (checkSchwarzeFarbe(zeile, spalte)) {
                    platziereWeißeDame(zeile, spalte, weiß);
                    weiß++;
                } else {
                    System.out.println("kein schwarzes Feld, Figur wurde nicht platziert");
                }
                break;
            case 4:
                // Platzierung einer schwarzen Dame
                System.out.println("Gib Zeile an:");
                zeile = scanner2.nextInt();
                System.out.println("Gib die Spalte an:");
                spalte = scanner3.nextInt();
                if (checkSchwarzeFarbe(zeile, spalte)) {
                    platziereSchwarzeDame(zeile, spalte, schwarz);
                    schwarz++;
                } else {
                    System.out.println("kein schwarzes Feld, Figur wurde nicht platziert");
                }
                break;
            case 5:
                // Beendet das Setzen der Figuren und schließt die Scanner
                scanner.close();
                scanner2.close();
                scanner3.close();
                placeFiguren = false;
                break;
            default:
                // Ausgabe bei falscher Eingabe
                System.out.println("Gib eine richtige Option (Zahl) ein");
                break;
        }
    }

    // Methode zur Überprüfung, ob das ausgewählte Feld schwarz ist
    private boolean checkSchwarzeFarbe(int zeile, int spalte) {
        return Board.getFeldfarbe()[zeile][spalte] != 'w';
    }

    // Methode zur Platzierung eines weißen Steins auf dem Spielfeld
    private void platziereWeißenStein(int zeile, int spalte, int counter) {
        weißeFiguren[counter] = new Figuren("SteinWeiß", zeile, spalte);
        Board.getFeld()[zeile][spalte] = "SteinWeiß";
        //BoardController.createIcon(zeile -1, spalte -1, Color.WHITE, false);
    }

    // Methode zur Platzierung eines schwarzen Steins auf dem Spielfeld
    private void platziereSchwarzenStein(int zeile, int spalte, int counter) {
        schwarzeFiguren[counter] = new Figuren("SteinSchwarz", zeile, spalte);
        Board.getFeld()[zeile][spalte] = "SteinSchwarz";
        //BoardController.createIcon(zeile -1, spalte -1, Color.BLACK, false);
    }

    // Methode zur Platzierung einer weißen Dame auf dem Spielfeld
    private void platziereWeißeDame(int zeile, int spalte, int counter) {
        weißeFiguren[counter] = new Figuren("SteinWeiß", zeile, spalte);
        weißeFiguren[counter].setDame(true);
        Board.getFeld()[zeile][spalte] = "SteinWeiß";
        //BoardController.createIcon(zeile -1, spalte -1, Color.WHITE, true);
    }

    // Methode zur Platzierung einer schwarzen Dame auf dem Spielfeld
    private void platziereSchwarzeDame(int zeile, int spalte, int counter) {
        schwarzeFiguren[counter] = new Figuren("SteinSchwarz", zeile, spalte);
        schwarzeFiguren[counter].setDame(true);
        Board.getFeld()[zeile][spalte] = "SteinSchwarz";
        //BoardController.createIcon(zeile -1, spalte -1, Color.BLACK, true);
    }

    // Methode zum Setzen der restlichen (nicht platzierten) Steine auf dem Spielfeld
    private void setzeRestlicheSteine(int weiß, int schwarz) {
        System.out.println("Jetzt startet");

        // Setzt nicht platzierte weiße Steine auf "tote" Positionen
        for (int i = weiß; i < 12; i++) {
            weißeFiguren[i] = new Figuren("SteinWeiß", 1000, 1000); // tote Steine
            System.out.println("i:" + i);
        }

        // Setzt nicht platzierte schwarze Steine auf "tote" Positionen
        for (int j = schwarz; j < 12; j++) {
            schwarzeFiguren[j] = new Figuren("SteinSchwarz", 1000, 1000); // tote Steine
            System.out.println("j:" + j);
        }
    }
}