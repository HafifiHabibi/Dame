package dame;

import java.awt.Color;

public class GameVerlauf {
    public static Figuren selectedFigur = null; // Um die ausgewählte Figur zu speichern
    public static Figuren rememberFigur; // Speichert die Figur, die erneut ziehen muss
    public static String currentPlayer; // Um zu bestimmen, welcher Spieler dran ist
    public static boolean forceJump; // Schlagzwang nach dem Überspringen des ersten Gegners
    public static boolean forceSelect; // Figur, die Schlagzwang hat, muss ausgewählt werden
    public static boolean generellSchlagZwangDone; // Wenn true, wurde genereller Schlagzwang erledigt

    // Konstruktor der Klasse GameVerlauf. Initialisiert den Spielverlauf und die Spiellogik.
    GameVerlauf() {
        currentPlayer = "SteinWeiß"; // Weiß beginnt
        forceJump = false; // Initial kein Schlagzwang
        rememberFigur = null; // Zu Beginn keine Figur im Schlagzwang
        generellSchlagZwangDone = false; // Am Anfang kann nicht geschlagen werden, daher false
    }

    // Methode zur Behandlung eines Button-Klicks auf dem Spielfeld.
    // Nimmt die Zeilen- und Spaltenposition des Klicks als Argumente entgegen.
    public void handleButtonClick(int row, int col) {
        while (forceSelect) {
            DoForceSelect(row, col); // Zwingt zur Auswahl der Figur mit Schlagzwang
            return;
        }

        while (forceJump) {
            DoForceJump(row, col); // Zwingt zum Ausführen des Schlagzugs
            CheckEndGame.checkForEndGame(); // Überprüft, ob das Spiel zu Ende ist
            return;
        }

        if (selectedFigur == null) {
            selectFigur(row, col); // Wählt eine Figur aus
            return;
        }

        if (selectedFigur.getZeile() == row && selectedFigur.getSpalte() == col) {
            deselectFigur(); // Deselektiert die Figur, wenn sie erneut angeklickt wird
            return;
        }

        while (GameLogic.generellSchlagZwang() && !generellSchlagZwangDone) {
            DoSchlagZwang(row, col); // Führt den Schlagzwang aus, falls erforderlich
            CheckEndGame.checkForEndGame(); // Überprüft, ob das Spiel zu Ende ist
            return;
        }

        if (selectedFigur.getDame()) {
            DoActionDame(row, col); // Führt den Zug für eine Dame aus
            CheckEndGame.checkForEndGame(); // Überprüft, ob das Spiel zu Ende ist
            return;
        } else {
            DoActionStein(row, col); // Führt den Zug für einen normalen Stein aus
            CheckEndGame.checkForEndGame(); // Überprüft, ob das Spiel zu Ende ist
            return;
        }
    }

    // Methode zur Auswahl einer Figur auf dem Spielfeld.
    private void selectFigur(int row, int col) {
        Figuren figur = GameLogic.getFigurAt(row, col);
        if (figur != null) {
            if (!figur.getFarbe().equals(currentPlayer)) {
                System.out.println("Es ist nicht deine Figur. Der andere Spieler ist dran!");
                return; // Beendet die Methode, wenn es nicht die Figur des aktuellen Spielers ist
            }
        }
        // Setzt die neue Auswahl und markiert die Figur auf dem Spielfeld
        if (figur != null) {
            selectedFigur = figur;
            BoardController.setBorder(row - 1, col - 1, Color.GREEN); // Umrandung in Grün
        }

        if (figur == null) {
            System.out.println("Bitte wähle eine Figur aus");
        }
    }

    // Methode zum Deselektieren der aktuell ausgewählten Figur.
    private void deselectFigur() {
        if (selectedFigur != null) {
            BoardController.resetBorder(selectedFigur.getZeile() - 1, selectedFigur.getSpalte() - 1);
            selectedFigur = null;
        }
    }

    // Methode zum Wechseln des Spielers nach einem Zug.
    private void switchPlayer() {
        if (currentPlayer.equals("SteinWeiß")) {
            currentPlayer = "SteinSchwarz";
        } else {
            currentPlayer = "SteinWeiß";
        }
        System.out.println("Es ist " + currentPlayer + " an der Reihe.");
        generellSchlagZwangDone = false; // Schlagzwang wird zurückgesetzt
    }

    // Methode zur Auswahl der Figur bei bestehendem Schlagzwang.
    private void DoForceSelect(int row, int col) {
        selectFigur(row, col);
        if (selectedFigur == null) {
            return;
        }
        if (selectedFigur == rememberFigur) {
            forceSelect = false;
            rememberFigur = null;
            return;
        } else {
            deselectFigur();
            System.out.println("Du musst mit der gleichen Figur nochmal schlagen");
            return;
        }
    }

    // Methode zur Durchführung eines Schlagzugs bei bestehendem Schlagzwang.
    private void DoForceJump(int row, int col) {
        if (!selectedFigur.getDame()) {
            if (GameLogic.isJumpValid(selectedFigur, row, col)) {
                GameLogic.performJump(selectedFigur, row, col);
                generellSchlagZwangDone = true; // Es wurde bereits einmal geschlagen
                if (!forceJump) {
                    switchPlayer();
                }
                return;
            } else {
                System.out.println("Invalid Move");
                return;
            }

        } else {
            if (GameLogic.isDameJumpValid(selectedFigur, row, col)) {
                GameLogic.performJumpDame(selectedFigur, row, col);
                generellSchlagZwangDone = true; // Es wurde bereits einmal geschlagen
                if (!forceJump) {
                    switchPlayer();
                }
                return;
            } else {
                System.out.println("Invalid Move");
                return;
            }
        }
    }

    // Methode zur Durchführung eines Zuges bei generellem Schlagzwang.
    private void DoSchlagZwang(int row, int col) {
        if (selectedFigur.getDame()) {
            if (GameLogic.isDameJumpValid(selectedFigur, row, col)) {
                GameLogic.performJumpDame(selectedFigur, row, col);
                generellSchlagZwangDone = true; // Es wurde bereits einmal geschlagen
                if (!forceJump) {
                    switchPlayer();
                }
                return;
            } else {
                System.out.println("Invalid move!");
                deselectFigur();
                return;
            }
        } else {
            if (GameLogic.isJumpValid(selectedFigur, row, col)) {
                GameLogic.performJump(selectedFigur, row, col);
                generellSchlagZwangDone = true; // Es wurde bereits einmal geschlagen
                if (!forceJump) {
                    switchPlayer();
                }
            } else {
                System.out.println("Invalid move!");
                deselectFigur();
                return;
            }
        }
        return;
    }

    // Methode zur Durchführung eines Zuges einer Dame, wenn kein Schlagzwang besteht.
    private void DoActionDame(int row, int col) {
        if (GameLogic.isDameMoveValid(selectedFigur, row, col)) {
            GameLogic.moveFigur(selectedFigur, row, col);
            if (!forceJump) {
                switchPlayer();
            }
        } else if (GameLogic.isDameJumpValid(selectedFigur, row, col)) {
            GameLogic.performJumpDame(selectedFigur, row, col);
            if (!forceJump) {
                switchPlayer();
            }
        } else {
            System.out.println("Invalid move!");
            return;
        }
    }

    // Methode zur Durchführung eines Zuges eines normalen Steins, wenn kein Schlagzwang besteht.
    private void DoActionStein(int row, int col) {
        if (GameLogic.isMoveValid(selectedFigur, row, col)) {
            GameLogic.moveFigur(selectedFigur, row, col);
            if (!forceJump) {
                switchPlayer();
            }
        } else if (GameLogic.isJumpValid(selectedFigur, row, col)) {
            GameLogic.performJump(selectedFigur, row, col);
            if (!forceJump) {
                switchPlayer();
            }
        } else {
            System.out.println("Invalid move!");
            return;
        }
    }
}