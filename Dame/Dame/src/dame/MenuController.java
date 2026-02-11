package dame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController {
    private MenuView menuView;

    // Konstruktor der Klasse MenuController. Verbindet die MenuView mit diesem Controller.
    public MenuController(MenuView menuView) {
        this.menuView = menuView;
        
        // Event-Listener für die Buttons in der MenuView setzen
        this.menuView.addStartButtonListener(new StartButtonListener()); // Listener für den Start-Button
        this.menuView.addDebugButtonListener(new DebugButtonListener()); // Listener für den Debug-Button
        this.menuView.addExitButtonListener(new ExitButtonListener()); // Listener für den Exit-Button
    }

    // Innere Klasse, die den ActionListener für den Start-Button implementiert.
    // Startet das Spiel, wenn der Button geklickt wird.
    class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Spiel starten
            new DameControl(false); // Beispiel: Startet das Spiel
            menuView.dispose(); // Menüfenster schließen
        }
    }

    // Innere Klasse, die den ActionListener für den Debug-Button implementiert.
    // Startet den Debug-Modus, wenn der Button geklickt wird.
    class DebugButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Debug(); // Debug-Modus starten
            menuView.dispose(); // Menüfenster schließen
        }
    }

    // Innere Klasse, die den ActionListener für den Exit-Button implementiert.
    // Schließt das Menü, wenn der Button geklickt wird.
    class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Menü schließen
            menuView.dispose(); // Menüfenster schließen
        }
    }
}