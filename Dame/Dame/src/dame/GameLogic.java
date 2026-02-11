package dame;

import java.awt.Color;

public class GameLogic {
    
    // Konstruktor der Klasse GameLogic. Hier sind keine spezifischen Initialisierungen notwendig.
    public GameLogic() {
    }

    // Methode, die die Figur an einer bestimmten Position (row, col) auf dem Spielfeld zurückgibt.
    // Durchsucht zuerst die schwarzen Figuren, dann die weißen Figuren.
    public static Figuren getFigurAt(int row, int col) {
        for (Figuren figur : DameControl.getSchwarzeFiguren()) {
            if (figur.getZeile() == row && figur.getSpalte() == col) {
                return figur;
            }
        }
        for (Figuren figur : DameControl.getWeißeFiguren()) {
            if (figur.getZeile() == row && figur.getSpalte() == col) {
                return figur;
            }
        }
        return null; // Keine Figur gefunden
    }

    // Methode zur Überprüfung, ob ein geplanter Zug für eine bestimmte Figur gültig ist.
    // Unterscheidet zwischen normalen Figuren und Damen.
    public static boolean isMoveValid(Figuren figur, int row, int col) {
        if (figur.getZeile() == 1000 || (figur.getZeile() == row && figur.getSpalte() == col)) {
            return false; // Tote Figur oder gleiche Figur erneut ausgewählt
        }
        if (figur.getDame()) {
            return isDameMoveValid(figur, row, col);
        }
        // Logik zur Überprüfung der Gültigkeit eines normalen Zugs (kein Sprung)
        int deltaRow = Math.abs(figur.getZeile() - row);
        int deltaCol = Math.abs(figur.getSpalte() - col);

        // Überprüft, ob die Bewegung diagonal ist und ob das Zielfeld leer ist.
        switch (figur.getFarbe()) {
            case "SteinWeiß": 
                if (figur.getZeile() > row) {
                    return deltaRow == 1 && deltaCol == 1 && Board.getFeld()[row][col] == null;
                } else {
                    return false;
                }
            case "SteinSchwarz":
                if (figur.getZeile() < row) {
                    return deltaRow == 1 && deltaCol == 1 && Board.getFeld()[row][col] == null;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }
    
    // Methode zur Überprüfung, ob ein geplanter Zug für eine Dame gültig ist.
    public static boolean isDameMoveValid(Figuren dame, int row, int col) {
        if (dame.getZeile() == 1000 || (dame.getZeile() == row && dame.getSpalte() == col)) {
            return false; // Tote Figur oder gleiche Figur erneut ausgewählt
        }
        int deltaRow = row - dame.getZeile();
        int deltaCol = col - dame.getSpalte();

        // Prüfen, ob sich die Dame diagonal bewegt (deltaRow und deltaCol sollten gleich sein)
        if (Math.abs(deltaRow) != Math.abs(deltaCol)) {
            return false;
        }

        int stepRow = (deltaRow > 0) ? 1 : -1;
        int stepCol = (deltaCol > 0) ? 1 : -1;

        int currentRow = dame.getZeile() + stepRow;
        int currentCol = dame.getSpalte() + stepCol;

        // Überprüfen, ob sich auf dem Weg eine Figur befindet
        while (currentRow != row && currentCol != col) {
            if (getFigurAt(currentRow, currentCol) != null) {
                return false; // Eine Figur blockiert den Weg
            }

            currentRow += stepRow;
            currentCol += stepCol;
        }

        // Prüfen, ob das Zielfeld leer ist
        return Board.getFeld()[row][col] == null;
    }

    // Methode zur Überprüfung, ob ein geplanter Sprung für eine Figur gültig ist.
    public static boolean isJumpValid(Figuren figur, int row, int col) {
        if (figur.getZeile() == 1000 || (figur.getZeile() == row && figur.getSpalte() == col)) {
            return false; // Tote Figur oder gleiche Figur erneut ausgewählt
        }
        int deltaRow = row - figur.getZeile();
        int deltaCol = col - figur.getSpalte();

        // Prüfen, ob der Zug ein Sprungzug ist (zwei Felder in einer Diagonalen)
        if (Math.abs(deltaRow) == 2 && Math.abs(deltaCol) == 2) {
            int middleRow = figur.getZeile() + deltaRow / 2;
            int middleCol = figur.getSpalte() + deltaCol / 2;
            
            // Weiß darf nicht nach hinten schlagen
            if (figur.getFarbe().equals("SteinWeiß") && row > figur.getZeile()) {
                return false;
            }

            // Schwarz darf nicht nach hinten schlagen
            if (figur.getFarbe().equals("SteinSchwarz") && row < figur.getZeile()) {
                return false;
            }

            // Prüfen, ob auf dem dazwischenliegenden Feld eine Figur des Gegners steht
            Figuren figurInTheMiddle = getFigurAt(middleRow, middleCol);
            if (figurInTheMiddle != null && !figurInTheMiddle.getFarbe().equals(figur.getFarbe())) {
                return Board.getFeld()[row][col] == null; // Und das Zielfeld frei ist
            }
        }
        return false;
    }

    // Methode zur Überprüfung, ob ein geplanter Sprung für eine Dame gültig ist.
    public static boolean isDameJumpValid(Figuren dame, int targetRow, int targetCol) {
        if (dame.getZeile() == 1000 || (dame.getZeile() == targetRow && dame.getSpalte() == targetCol)) {
            return false; // Tote Figur oder gleiche Figur erneut ausgewählt
        }
        int startRow = dame.getZeile();
        int startCol = dame.getSpalte();

        int deltaRow = targetRow - startRow;
        int deltaCol = targetCol - startCol;

        // Prüfen, ob die Bewegung diagonal ist und das Zielfeld innerhalb des Brettes liegt
        if (Math.abs(deltaRow) == Math.abs(deltaCol) && ImFeld(targetRow, targetCol)) {
            int stepRow = deltaRow > 0 ? 1 : -1;
            int stepCol = deltaCol > 0 ? 1 : -1;
            
            int opponentRow = 0;
            int opponentCol = 0;

            int currentRow = startRow + stepRow;
            int currentCol = startCol + stepCol;
            boolean opponentFound = false;

            while (currentRow != targetRow && currentCol != targetCol) {
                Figuren figurInTheMiddle = getFigurAt(currentRow, currentCol);

                if (figurInTheMiddle != null) {
                    if (figurInTheMiddle.getFarbe().equals(dame.getFarbe())) {
                        // Dame kann nicht über eigene Figuren springen
                        return false;
                    } else if (opponentFound) {
                        // Dame kann nicht mehr als eine gegnerische Figur überspringen
                        return false;
                    } else {
                        opponentFound = true;
                        opponentRow = currentRow + stepRow;
                        opponentCol = currentCol + stepCol;
                    }
                }
                currentRow += stepRow;
                currentCol += stepCol;
            }

            // Prüfen, ob das Zielfeld leer ist und direkt hinter dem Gegner anliegt
            if (Board.getFeld()[targetRow][targetCol] == null && targetRow == opponentRow && targetCol == opponentCol) {
                return opponentFound; // Gültiger Sprung, wenn ein Gegner übersprungen wurde
            }
        }

        return false;
    }

    // Methode zur Überprüfung, ob eine Position innerhalb des Spielfelds liegt.
    private static boolean ImFeld(int row, int col) {
        return row >= 1 && row <= 8 && col >= 1 && col <= 8;
    }

    // Methode zur Durchführung eines Zugs einer Figur.
    public static void moveFigur(Figuren figur, int newRow, int newCol) {
        // Entferne Umrandung von der ausgewählten Figur
        BoardController.setBorder(figur.getZeile() - 1, figur.getSpalte() - 1, null);

        // Aktualisiere das Board-Modell
        Board.getFeld()[figur.getZeile()][figur.getSpalte()] = null; // Alte Position auf null setzen
        Board.getFeld()[newRow][newCol] = figur.getFarbe(); // Neue Position auf SteinSchwarz oder SteinWeiß setzen

        // Entferne das alte Icon
        BoardController.resetIcon(figur.getZeile() - 1, figur.getSpalte() - 1);
        
        // Setze das neue Icon für Damen
        if (figur.getDame()) {
            if (figur.getFarbe().equals("SteinSchwarz")) {
                BoardController.createIcon(newRow - 1, newCol - 1, Color.BLACK, true);
            } else {
                BoardController.createIcon(newRow - 1, newCol - 1, Color.WHITE, true);
            }
        }
        // Setze das neue Icon für normale Figuren
        else {
            if (figur.getFarbe().equals("SteinSchwarz")) {
                BoardController.createIcon(newRow - 1, newCol - 1, Color.BLACK, false);
            } else {
                BoardController.createIcon(newRow - 1, newCol - 1, Color.WHITE, false);
            }
        }
        // Aktualisiere die Position der Figur
        figur.zeile = newRow;
        figur.spalte = newCol;
        
        GameVerlauf.rememberFigur = GameVerlauf.selectedFigur;
        
        // Prüfe, ob ein Stein zu einer Dame wird
        switch (GameVerlauf.selectedFigur.getFarbe()) {
            case "SteinWeiß":
                if (!GameVerlauf.selectedFigur.getDame() && GameVerlauf.selectedFigur.getZeile() == 1) {
                    GameVerlauf.selectedFigur.setDame(true);
                    figur.setDame(true);
                    BoardController.createIcon(newRow - 1, newCol - 1, Color.WHITE, true);
                    GameVerlauf.rememberFigur = null; // Darf nicht nochmal schlagen, da Stein zur Dame wurde
                    GameVerlauf.forceSelect = false;
                    GameVerlauf.forceJump = false;
                }
                break;
            case "SteinSchwarz":
                if (!GameVerlauf.selectedFigur.getDame() && GameVerlauf.selectedFigur.getZeile() == 8) {
                    GameVerlauf.selectedFigur.setDame(true);
                    figur.setDame(true);
                    BoardController.createIcon(newRow - 1, newCol - 1, Color.BLACK, true);
                    GameVerlauf.rememberFigur = null;
                    GameVerlauf.forceSelect = false;
                    GameVerlauf.forceJump = false;
                }
                break;
            default:
                break;
        }
        
        // Entferne die Auswahl (keine Figur mehr ausgewählt)
        GameVerlauf.selectedFigur = null;
    }
    
    // Methode zur Überprüfung, ob eine Figur nach einem Schlag nochmal schlagen muss.
    public static void schlagNochmalZwang(Figuren figur) {
        if (figur.getDame()) {
            GameVerlauf.forceJump = canDameJump(figur);
            GameVerlauf.forceSelect = GameVerlauf.forceJump;
            GameVerlauf.rememberFigur = figur;
        } else {
            GameVerlauf.forceJump = canJump(figur);
            GameVerlauf.forceSelect = GameVerlauf.forceJump;
            GameVerlauf.rememberFigur = figur;
        }
        
        if (!GameVerlauf.forceJump) {
            GameVerlauf.rememberFigur = null;
        }
    }
    
    // Methode zur Durchführung eines Sprungs einer Figur und zum Entfernen der geschlagenen Figur.
    public static void performJump(Figuren figur, int newRow, int newCol) {
        int middleRow = (figur.getZeile() + newRow) / 2;
        int middleCol = (figur.getSpalte() + newCol) / 2;

        // Entferne die geschlagene Figur
        Figuren geschlageneFigur = getFigurAt(middleRow, middleCol);
        if (geschlageneFigur != null) {
            Board.getFeld()[middleRow][middleCol] = null;
            BoardController.resetIcon(middleRow - 1, middleCol - 1);
            // Entferne die geschlagene Figur aus dem Array der Figuren
            DameControl.removeFigur(geschlageneFigur);
        }
        
        moveFigur(figur, newRow, newCol);
        if (GameVerlauf.rememberFigur != null) { // rememberFigur ist null, wenn Stein zur Dame wurde
            schlagNochmalZwang(GameVerlauf.rememberFigur); // Testet, ob die Figur nochmal schlagen muss
        }
    }
    
    // Methode zur Durchführung eines Sprungs einer Dame und zum Entfernen der geschlagenen Figur.
    public static void performJumpDame(Figuren dame, int newRow, int newCol) {
        int deltaRow = (newRow - dame.getZeile()) / Math.abs(newRow - dame.getZeile());
        int deltaCol = (newCol - dame.getSpalte()) / Math.abs(newCol - dame.getSpalte());

        int middleRow = dame.getZeile() + deltaRow;
        int middleCol = dame.getSpalte() + deltaCol;

        // Überprüfe den Pfad auf gegnerische Figuren
        while (middleRow != newRow && middleCol != newCol) {
            Figuren geschlageneFigur = getFigurAt(middleRow, middleCol);
            if (geschlageneFigur != null) {
                Board.getFeld()[middleRow][middleCol] = null;
                BoardController.resetIcon(middleRow - 1, middleCol - 1);
                DameControl.removeFigur(geschlageneFigur);
                break; // Sobald eine Figur geschlagen wurde, kann der Zug beendet werden
            }
            middleRow += deltaRow;
            middleCol += deltaCol;
        }

        // Bewege die Dame zum Zielort
        moveFigur(dame, newRow, newCol);
        if (GameVerlauf.rememberFigur != null) { // rememberFigur ist null, wenn Stein zur Dame wurde
            schlagNochmalZwang(GameVerlauf.rememberFigur); // Testet, ob die Figur nochmal schlagen muss
        }
    }
    
    // Methode zur Überprüfung, ob eine Dame einen gültigen Sprung ausführen kann.
    public static boolean canDameJump(Figuren dame) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (isDameJumpValid(dame, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Methode zur Überprüfung, ob eine Figur einen gültigen Sprung ausführen kann.
    public static boolean canJump(Figuren figur) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (isJumpValid(figur, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // Methode zur Überprüfung, ob für die aktuelle Runde ein Schlagzwang besteht.
    public static boolean generellSchlagZwang() {
        if (GameVerlauf.currentPlayer.equals("SteinSchwarz")) {
            Figuren[] schwarzeFiguren = DameControl.getSchwarzeFiguren();
            for (Figuren figur : schwarzeFiguren) {
                if (figur.getZeile() == 1000) { // Wenn Figur aus dem Spiel ist, muss nicht behandelt werden
                    continue;
                }
                if (figur.getDame()) {
                    if (canDameJump(figur)) {
                        return true;
                    }
                } else {
                    if (canJump(figur)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            Figuren[] weißeFiguren = DameControl.getWeißeFiguren();
            for (Figuren figur : weißeFiguren) {
                if (figur.getZeile() == 1000) { // Wenn Figur aus dem Spiel ist, muss nicht behandelt werden
                    continue;
                }
                if (figur.getDame()) {
                    if (canDameJump(figur)) {
                        return true;
                    }
                } else {
                    if (canJump(figur)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
