package dame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {

    private JButton startButton;  // Button zum Starten des Spiels
    private JButton debugButton;  // Button zum Starten des Debug-Modus
    private JButton exitButton;   // Button zum Beenden des Programms

    // Konstruktor für die MenuView. Initialisiert das Menüfenster und seine Komponenten.
    public MenuView() {
        // Titel des Fensters setzen
        setTitle("Dame Menü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);  // Größe des Fensters
        setLocationRelativeTo(null);  // Fenster in der Mitte des Bildschirms positionieren
        
        // Layout des Fensters setzen
        setLayout(new BorderLayout());
        
        // Überschrift "Dame" hinzufügen
        JLabel titleLabel = new JLabel("Dame", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));  // Schriftart und Größe der Überschrift
        add(titleLabel, BorderLayout.NORTH);  // Überschrift oben im Fenster platzieren
        
        // Panel für die Buttons erstellen
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));  // GridLayout mit 3 Reihen, 1 Spalte, 10px Abstand
        
        // Buttons erstellen
        startButton = new JButton("Start");  // Button zum Starten des Spiels
        debugButton = new JButton("Debug");  // Button zum Starten des Debug-Modus
        exitButton = new JButton("Beenden"); // Button zum Beenden des Programms
        
        // Buttons zum Panel hinzufügen
        buttonPanel.add(startButton);
        buttonPanel.add(debugButton);
        buttonPanel.add(exitButton);
        
        // Panel mit den Buttons im Zentrum des Fensters platzieren
        add(buttonPanel, BorderLayout.CENTER);
        
        // Fenster sichtbar machen
        setVisible(true);
    }
    
    // Getter für die Buttons
    public JButton getStartButton() {
        return startButton;
    }

    public JButton getDebugButton() {
        return debugButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }
    
    // Methoden, um ActionListener zu den Buttons hinzuzufügen
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);  // Listener für den Start-Button hinzufügen
    }

    public void addDebugButtonListener(ActionListener listener) {
        debugButton.addActionListener(listener);  // Listener für den Debug-Button hinzufügen
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);  // Listener für den Exit-Button hinzufügen
    }
}