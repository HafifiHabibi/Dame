package dame;

public class DameControl {
    // Arrays zur Speicherung der schwarzen und weißen Figuren auf dem Spielfeld
    private static Figuren[] schwarzeFiguren;
    private static Figuren[] weißeFiguren;
    
    // Konstruktor, der das Spiel initialisiert, wenn nicht im Debug-Modus
    public DameControl(boolean debug) {
        if (!debug) {
            // Initialisierung der schwarzen Figuren und Speicherung in einem Array
            schwarzeFiguren = new Figuren[12];
            weißeFiguren = new Figuren[12];

            // Platzierung der schwarzen Figuren auf dem Spielbrett
            schwarzeFiguren[0] = new Figuren("SteinSchwarz", 1, 2);
            schwarzeFiguren[1] = new Figuren("SteinSchwarz", 1, 4);
            schwarzeFiguren[2] = new Figuren("SteinSchwarz", 1, 6);
            schwarzeFiguren[3] = new Figuren("SteinSchwarz", 1, 8);
            schwarzeFiguren[4] = new Figuren("SteinSchwarz", 2, 1);
            schwarzeFiguren[5] = new Figuren("SteinSchwarz", 2, 3);
            schwarzeFiguren[6] = new Figuren("SteinSchwarz", 2, 5);
            schwarzeFiguren[7] = new Figuren("SteinSchwarz", 2, 7);
            schwarzeFiguren[8] = new Figuren("SteinSchwarz", 3, 2);
            schwarzeFiguren[9] = new Figuren("SteinSchwarz", 3, 4);
            schwarzeFiguren[10] = new Figuren("SteinSchwarz", 3, 6);
            schwarzeFiguren[11] = new Figuren("SteinSchwarz", 3, 8);

            // Platzierung der weißen Figuren auf dem Spielbrett
            weißeFiguren[0] = new Figuren("SteinWeiß", 6, 1);
            weißeFiguren[1] = new Figuren("SteinWeiß", 6, 3);
            weißeFiguren[2] = new Figuren("SteinWeiß", 6, 5);
            weißeFiguren[3] = new Figuren("SteinWeiß", 6, 7);
            weißeFiguren[4] = new Figuren("SteinWeiß", 7, 2);
            weißeFiguren[5] = new Figuren("SteinWeiß", 7, 4);
            weißeFiguren[6] = new Figuren("SteinWeiß", 7, 6);
            weißeFiguren[7] = new Figuren("SteinWeiß", 7, 8);
            weißeFiguren[8] = new Figuren("SteinWeiß", 8, 1);
            weißeFiguren[9] = new Figuren("SteinWeiß", 8, 3);
            weißeFiguren[10] = new Figuren("SteinWeiß", 8, 5);
            weißeFiguren[11] = new Figuren("SteinWeiß", 8, 7);
            
            // Erstellen des Spielbretts
            new Board();
            
            // Initialisierung der Ansicht (View) des Spielbretts mit den platzierten Figuren
            BoardView boardView = new BoardView(schwarzeFiguren, weißeFiguren);
            
            // Initialisierung des Spielverlaufs (Model)
            GameVerlauf gameVerlauf = new GameVerlauf();
            
            // Initialisierung des Controllers für die Spiel-Logik
            new BoardController(boardView, gameVerlauf);
        }
    }
    
    // Getter-Methode für die schwarzen Figuren
    public static Figuren[] getSchwarzeFiguren() {
        return schwarzeFiguren;
    }

    // Getter-Methode für die weißen Figuren
    public static Figuren[] getWeißeFiguren() {
        return weißeFiguren;
    }
    
    // Setter-Methode, um die schwarzen Figuren zu aktualisieren
    public static void setSchwarzeFiguren(Figuren[] neueSchwarzeFiguren) {
        schwarzeFiguren = neueSchwarzeFiguren;
    }
    
    // Setter-Methode, um die weißen Figuren zu aktualisieren
    public static void setWeißeFiguren(Figuren[] neueWeißeFiguren) {
        weißeFiguren = neueWeißeFiguren;
    }
    
    // Methode, um eine Figur vom Spielbrett zu entfernen, indem ihre Position außerhalb des Spielfelds gesetzt wird
    public static void removeFigur(Figuren figurToRemove) {
        figurToRemove.setZeile(1000);
        figurToRemove.setSpalte(1000);  	
    }
    
    // Debug-Methode, um das Spielbrett in einem Debug-Modus anzuzeigen
    public static void BoardDebug() {
        BoardView boardViewDebug = new BoardView(schwarzeFiguren, weißeFiguren);
        GameVerlauf gameVerlauf = new GameVerlauf();
        
        // Initialisierung des Controllers für die Spiel-Logik im Debug-Modus
        new BoardController(boardViewDebug, gameVerlauf);
    }
}