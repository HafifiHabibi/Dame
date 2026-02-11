package dame;

public class CheckEndGame {
    
    // Konstruktor für die Klasse CheckEndGame
    public CheckEndGame() {
        
    }
    
    // Diese Methode überprüft, ob das Spiel zu Ende ist und gibt den Gewinner zurück.
    // Wenn das Spiel noch nicht zu Ende ist, gibt es "No Winner yet" zurück.
    public static String checkForEndGame() {
        // Überprüft, ob es keine weißen oder keine schwarzen Figuren mehr gibt,
        // oder ob der aktuelle Spieler keine gültigen Züge mehr hat.
        boolean whiteLoss = !hasWhiteFiguresLeft() || 
                            (GameVerlauf.currentPlayer.equals("SteinWeiß") && 
                             !canWhiteJump() && !canWhiteMove());
                             
        boolean blackLoss = !hasBlackFiguresLeft() || 
                            (GameVerlauf.currentPlayer.equals("SteinSchwarz") && 
                             !canBlackJump() && !canBlackMove());

        // Wenn entweder Weiß oder Schwarz verloren hat, wird der Gewinner ermittelt und angezeigt.
        if (whiteLoss || blackLoss) {
            String winner = whiteLoss ? "Schwarz" : "Weiß";
            BoardController.displayWinner(winner);
            return winner;
        }
        // Wenn noch kein Gewinner ermittelt wurde, wird "No Winner yet" zurückgegeben.
        else return "No Winner yet";
    }
    
    // Diese Methode überprüft, ob noch weiße Figuren auf dem Brett vorhanden sind.
    public static boolean hasWhiteFiguresLeft() {
        // Durchläuft das gesamte Spielfeld und sucht nach einer weißen Figur.
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Wenn eine weiße Figur gefunden wird, wird true zurückgegeben.
                if ("SteinWeiß".equals(Board.getFeld()[i][j])) {
                    return true;
                }
            }
        }
        // Wenn keine weiße Figur gefunden wurde, wird false zurückgegeben.
        return false;
    }

    // Diese Methode überprüft, ob noch schwarze Figuren auf dem Brett vorhanden sind.
    public static boolean hasBlackFiguresLeft() {
        // Durchläuft das gesamte Spielfeld und sucht nach einer schwarzen Figur.
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Wenn eine schwarze Figur gefunden wird, wird true zurückgegeben.
                if ("SteinSchwarz".equals(Board.getFeld()[i][j])) {
                    return true;
                }
            }
        }
        // Wenn keine schwarze Figur gefunden wurde, wird false zurückgegeben.
        return false;
    }
    
    // Diese Methode überprüft, ob eine der weißen Figuren noch einen gültigen Zug machen kann.
    public static boolean canWhiteMove() {
        // Durchläuft alle weißen Figuren.
        for (Figuren figur : DameControl.getWeißeFiguren()) {
            // Überspringt Figuren, die bereits aus dem Spiel genommen wurden (Zeile 1000).
            if (figur.getZeile() == 1000 ) {
                continue;
            }
            // Wenn eine Figur einen gültigen Zug hat, wird true zurückgegeben.
            if(canMove(figur)) {
                return true;
            }
        }
        // Wenn keine Figur einen gültigen Zug hat, wird false zurückgegeben.
        return false;
    }
    
    // Diese Methode überprüft, ob eine der schwarzen Figuren noch einen gültigen Zug machen kann.
    public static boolean canBlackMove() {
        // Durchläuft alle schwarzen Figuren.
        for (Figuren figur : DameControl.getSchwarzeFiguren()) {
            // Überspringt Figuren, die bereits aus dem Spiel genommen wurden (Zeile 1000).
            if (figur.getZeile() == 1000 ) {
                continue;
            }
            // Wenn eine Figur einen gültigen Zug hat, wird true zurückgegeben.
            if(canMove(figur)) {
                return true;
            }
        }
        // Wenn keine Figur einen gültigen Zug hat, wird false zurückgegeben.
        return false;
    }
    
    // Diese Methode überprüft, ob eine der weißen Figuren noch einen Sprung (Schlagen) machen kann.
    public static boolean canWhiteJump() {
        // Durchläuft alle weißen Figuren.
        for (Figuren figur : DameControl.getWeißeFiguren()) {
            // Überspringt Figuren, die bereits aus dem Spiel genommen wurden (Zeile 1000).
            if (figur.getZeile() == 1000 ) {
                continue;
            }
            // Wenn eine Figur einen gültigen Sprung machen kann, wird true zurückgegeben.
            if(canJump(figur)) {
                return true;
            }
        }
        // Wenn keine Figur einen gültigen Sprung machen kann, wird false zurückgegeben.
        return false;
    }
    
    // Diese Methode überprüft, ob eine der schwarzen Figuren noch einen Sprung (Schlagen) machen kann.
    public static boolean canBlackJump() {
        // Durchläuft alle schwarzen Figuren.
        for (Figuren figur : DameControl.getSchwarzeFiguren()) {
            // Überspringt Figuren, die bereits aus dem Spiel genommen wurden (Zeile 1000).
            if (figur.getZeile() == 1000 ) {
                continue;
            }
            // Wenn eine Figur einen gültigen Sprung machen kann, wird true zurückgegeben.
            if(canJump(figur)) {
                return true;
            }
        }
        // Wenn keine Figur einen gültigen Sprung machen kann, wird false zurückgegeben.
        return false;
    }
    
    // Diese Methode überprüft, ob eine gegebene Figur noch einen gültigen Zug machen kann.
    public static boolean canMove(Figuren figur) {
        // Durchläuft alle Felder auf dem Brett.
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Wenn ein Zug auf ein bestimmtes Feld gültig ist, wird true zurückgegeben.
                if (GameLogic.isMoveValid(figur, i, j)) {
                    return true;
                }
            }
        }
        // Wenn kein gültiger Zug gefunden wurde, wird false zurückgegeben.
        return false;
    }
    
    // Diese Methode überprüft, ob eine gegebene Figur noch einen gültigen Sprung (Schlagen) machen kann.
    public static boolean canJump(Figuren figur) {
        // Durchläuft alle Felder auf dem Brett.
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Wenn die Figur keine Dame ist, wird überprüft, ob ein normaler Sprung möglich ist.
                if(!figur.getDame()) {
                    if (GameLogic.isJumpValid(figur, i, j)) {
                        return true;
                    }
                }
                // Wenn die Figur eine Dame ist, wird überprüft, ob ein Damensprung möglich ist.
                else {
                    if (GameLogic.isDameJumpValid(figur, i, j)) {
                        return true;
                    }
                }
            }
        }
        // Wenn kein gültiger Sprung gefunden wurde, wird false zurückgegeben.
        return false;
    }
}