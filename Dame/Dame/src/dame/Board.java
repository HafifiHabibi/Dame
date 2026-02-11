package dame;

public class Board {
    
    // 2D-Array, das das Spielfeld repräsentiert. Einträge sind Strings, die den Namen der Figuren enthalten.
    private static String[][] feld = new String[9][9];
    // feld[i][j] == null bedeutet, dass das Feld leer ist.
    // Man hätte auch boolean verwenden können, aber String macht es übersichtlicher, da wir direkt den Figurennamen speichern können.

    // 2D-Array, das die Farbe der Felder speichert. 'w' steht für ein weißes Feld, 's' für ein schwarzes Feld.
    private static char[][] feldfarbe = new char[9][9];
    
    // Konstruktor, der die Felder initialisiert.
    public Board() {
        Board.feldfarbe = Board.fillFeldfarbe(); // Füllt das Spielfeld mit den entsprechenden Farben.
        Board.feld = Board.fillFeld(); // Füllt das Spielfeld mit den Figuren in ihrer Startposition.
    }
    
    // Diese Methode füllt das feldfarbe-Array mit den entsprechenden Farben ('w' oder 's').
    private static char[][] fillFeldfarbe() {
        for (int i = 1; i < 9; i++) { // Das Spielfeld reicht von 1 bis 8 (8x8 Schachbrett).
            for (int j = 1; j < 9; j++) {
                if ((i + j) % 2 == 0) {
                    feldfarbe[i][j] = 'w'; // Wenn die Summe der Indizes gerade ist, ist das Feld weiß.
                } else {
                    feldfarbe[i][j] = 's'; // Ansonsten ist das Feld schwarz.
                }
            }
        }
        return feldfarbe; // Gibt das gefüllte Array zurück.
    }
    
    // Getter-Methode für das feldfarbe-Array.
    public static char[][] getFeldfarbe() {
        return feldfarbe;
    }
    
    // Diese Methode setzt alle Felder im feld-Array auf null zurück (leert das Spielfeld).
    public static String[][] feldReset() {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Board.feld[i][j] = null; // Setzt jedes Feld auf null (leer).
            }
        }
        return Board.feld; // Gibt das zurückgesetzte Array zurück.
    }
    
    // Diese Methode füllt das feld-Array mit den Figuren in ihrer Startposition.
    private static String[][] fillFeld() {
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                Board.fillSchwarz(i, j); // Platziert schwarze Steine auf ihren Startpositionen.
                Board.fillWeiß(i, j); // Platziert weiße Steine auf ihren Startpositionen.
            }
        }
        return Board.feld; // Gibt das gefüllte Array zurück.
    }

    // Platziert die schwarzen Steine auf den ersten drei Reihen des Spielfelds.
    private static void fillSchwarz(int i, int j) {
        switch (i) {
            case 1: 
                if (j == 2 || j == 4 || j == 6 || j == 8) { // Schwarze Steine auf den schwarzen Feldern der 1. Reihe.
                    Board.feld[i][j] = "SteinSchwarz";
                }
                break;
            case 2: 
                if (j == 1 || j == 3 || j == 5 || j == 7) { // Schwarze Steine auf den schwarzen Feldern der 2. Reihe.
                    Board.feld[i][j] = "SteinSchwarz";
                }
                break;
            case 3: 
                if (j == 2 || j == 4 || j == 6 || j == 8) { // Schwarze Steine auf den schwarzen Feldern der 3. Reihe.
                    Board.feld[i][j] = "SteinSchwarz";
                }
                break;
            default: 
                break; // In allen anderen Reihen keine schwarzen Steine platzieren.
        }
    }
    
    // Platziert die weißen Steine auf den letzten drei Reihen des Spielfelds.
    private static void fillWeiß(int i, int j) {
        switch (i) {
            case 6: 
                if (j == 1 || j == 3 || j == 5 || j == 7) { // Weiße Steine auf den schwarzen Feldern der 6. Reihe.
                    Board.feld[i][j] = "SteinWeiß";
                }
                break;
            case 7: 
                if (j == 2 || j == 4 || j == 6 || j == 8) { // Weiße Steine auf den schwarzen Feldern der 7. Reihe.
                    Board.feld[i][j] = "SteinWeiß";
                }
                break;
            case 8: 
                if (j == 1 || j == 3 || j == 5 || j == 7) { // Weiße Steine auf den schwarzen Feldern der 8. Reihe.
                    Board.feld[i][j] = "SteinWeiß";
                }
                break;
            default: 
                break; // In allen anderen Reihen keine weißen Steine platzieren.
        }
    }
    
    // Getter-Methode für das feld-Array.
    public static String[][] getFeld() {
        return Board.feld;
    }
}
