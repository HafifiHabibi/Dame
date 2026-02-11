package dame; 

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class BoardController {
    // Statische Referenz auf die Klasse GameVerlauf, die den Spielverlauf verwaltet
    public static GameVerlauf gameVerlauf;
    
    // Statische Referenz auf die BoardView-Klasse, die die grafische Darstellung des Spielbretts handhabt
    private static BoardView boardView;
    
    // Konstruktor, der das BoardView- und GameVerlauf-Objekt initialisiert
    public BoardController(BoardView boardView, GameVerlauf gameVerlauf) {
        BoardController.boardView = boardView;
        BoardController.gameVerlauf = gameVerlauf;
        
        initializeController(); // Initialisiert den Controller, um auf Benutzerinteraktionen zu reagieren
    }

    // Methode, die den Controller initialisiert, indem sie ActionListener zu den Buttons auf dem Spielbrett hinzufügt
    private void initializeController() {
        JButton[][] buttons = boardView.getButtons(); // Holt das 2D-Array der Buttons vom BoardView
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int row = i + 1; // Korrigiert die Reihen- und Spaltennummer, um mit dem 1-basierten Board übereinzustimmen
                final int col = j + 1;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gameVerlauf.handleButtonClick(row, col); // Übergibt die Klick-Position an die GameVerlauf-Klasse zur Verarbeitung
                    }
                });
            }
        }
    }
    
    // Methode, um die Umrandung eines Buttons an einer bestimmten Position auf dem Spielbrett zu setzen
    public static void setBorder(int row, int col, Color color) {
        boardView.getButtons()[row][col].setBorder(new LineBorder(color, 3)); // Setzt eine Umrandung mit der angegebenen Farbe und Breite
    }

    // Methode, um das Icon eines Buttons an einer bestimmten Position zurückzusetzen
    public static void resetIcon(int row, int col) {
        boardView.getButtons()[row][col].setIcon(null); // Entfernt das Icon
        boardView.getButtons()[row][col].setBorder(null); // Entfernt die Umrandung
    }
    
    // Methode, um nur die Umrandung eines Buttons an einer bestimmten Position zurückzusetzen
    public static void resetBorder(int row, int col) {
        boardView.getButtons()[row][col].setBorder(null); // Entfernt die Umrandung
    }
    
    // Methode, um ein Icon für eine Spielfigur (oder Dame) auf einem Button zu erstellen
    public static void createIcon(int row, int col, Color color, boolean Dame) {
        if(Dame) {
            boardView.getButtons()[row][col].setIcon(boardView.createDameIcon(color)); // Setzt ein Dame-Icon, wenn die Figur eine Dame ist
        } else {
            boardView.getButtons()[row][col].setIcon(boardView.createCircleIcon(color)); // Setzt ein normales Figuren-Icon
        }
    }
    
    // Methode, um den Gewinner des Spiels anzuzeigen
    public static void displayWinner(String winner) {
        boardView.displayWinner(winner); // Zeigt eine Dialogbox mit der Gewinner-Nachricht an
    }
}
