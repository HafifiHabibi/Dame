package dame;

public class Figuren {
    // Die Farbe der Figur, entweder "SteinSchwarz" oder "SteinWeiß".
    public String farbe;
    
    // Gibt an, ob die Figur eine Dame ist. true, wenn es eine Dame ist, false sonst.
    public boolean dame;
    
    // Die Zeile, in der sich die Figur auf dem Spielfeld befindet (i-Koordinate).
    public int zeile;
    
    // Die Spalte, in der sich die Figur auf dem Spielfeld befindet (j-Koordinate).
    public int spalte;

    // Konstruktor, der eine neue Figur erstellt.
    // Setzt die Farbe, Zeile und Spalte der Figur, und setzt standardmäßig dame auf false.
    public Figuren(String farbe, int zeile, int spalte) {
        this.farbe = farbe;
        this.dame = false; // Neue Figuren sind standardmäßig keine Damen.
        this.zeile = zeile;
        this.spalte = spalte;
    }
    
    // Getter-Methode für die Farbe der Figur.
    public String getFarbe() {
        return farbe;
    }
    
    // Getter-Methode, die zurückgibt, ob die Figur eine Dame ist.
    public boolean getDame() {
        return dame;
    }
    
    // Getter-Methode für die Zeile (i-Koordinate) der Figur.
    public int getZeile() {
        return zeile;
    }
    
    // Getter-Methode für die Spalte (j-Koordinate) der Figur.
    public int getSpalte() {
        return spalte;
    }
    
    // Setter-Methode zum Ändern der Zeile der Figur. Wird zum Beispiel verwendet, wenn eine Figur bewegt wird.
    public void setZeile(int i) {
        this.zeile = i;
    }
    
    // Setter-Methode zum Ändern der Spalte der Figur. Wird ebenfalls verwendet, wenn eine Figur bewegt wird.
    public void setSpalte(int i) {
        this.spalte = i;
    }
    
    // Setter-Methode zum Festlegen, ob die Figur eine Dame ist.
    public void setDame(boolean a) {
        this.dame = a;
    }
}