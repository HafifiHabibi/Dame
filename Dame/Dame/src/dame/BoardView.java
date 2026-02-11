package dame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardView {
    // 2D-Array für die Buttons, die die Spielfelder auf dem Schachbrett repräsentieren.
    private JButton[][] buttons = new JButton[8][8];
    
    // JFrame ist das Hauptfenster, in dem das Spielfeld angezeigt wird.
    private JFrame frame = new JFrame();
    
    // Konstruktor, der das Spielfeld initialisiert und die Figuren auf das Brett platziert.
    public BoardView(Figuren[] schwarzeFiguren, Figuren[] weißeFiguren) {
        frame.setTitle("Dame Spiel");  // Setzt den Titel des Fensters
        frame.setSize(600, 600);       // Setzt die Größe des Fensters
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Beendet das Programm, wenn das Fenster geschlossen wird
        frame.setLayout(new GridLayout(8, 8)); // Verwendet ein GridLayout, um das 8x8-Schachbrett zu erstellen

        initializeBoard(); // Initialisiert das Schachbrett mit den Farben für die Felder
        plaziereFiguren(schwarzeFiguren, weißeFiguren); // Platziert die Figuren auf dem Brett
        
        frame.setVisible(true); // Macht das Fenster sichtbar
    }
    
    // Methode, die das Schachbrett initialisiert, indem sie Buttons zuweist und sie abwechselnd weiß und grau färbt.
    private void initializeBoard() {
        boolean white = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new JButton(); // Erstellt einen neuen Button für jedes Feld
                if (white) {
                    buttons[i][j].setBackground(Color.WHITE); // Setzt den Button-Hintergrund auf weiß
                } else {
                    buttons[i][j].setBackground(Color.GRAY); // Setzt den Button-Hintergrund auf grau
                }
                frame.add(buttons[i][j]); // Fügt den Button dem JFrame hinzu
                white = !white; // Wechselt die Farbe für das nächste Feld
            }
            white = !white; // Wechselt die Startfarbe für die nächste Zeile
        }
    }
    
    // Methode, die die Figuren auf dem Brett platziert. Die Figuren werden als Icons auf den Buttons dargestellt.
    private void plaziereFiguren(Figuren[] schwarzeFiguren, Figuren[] weißeFiguren) {
        for (Figuren figur : schwarzeFiguren) {
            if (figur.getZeile() == 1000) {
                continue; // Überspringt "tote" Figuren, die sich außerhalb des Spielfelds befinden
            }
            buttons[figur.getZeile() - 1][figur.getSpalte() - 1].setIcon(createCircleIcon(Color.BLACK)); // Platziert schwarze Figuren
        }
        for (Figuren figur : weißeFiguren) {
            if (figur.getZeile() == 1000) {
                continue; // Überspringt "tote" Figuren
            }
            buttons[figur.getZeile() - 1][figur.getSpalte() - 1].setIcon(createCircleIcon(Color.WHITE)); // Platziert weiße Figuren
        }
    }
    
    // Methode, die ein Icon erstellt, das einen gefüllten Kreis (eine Figur) darstellt.
    public Icon createCircleIcon(Color color) {
        int diameter = 50; // Größe des Kreises (Figur)
        Image image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) image.getGraphics();

        // Aktiviert Anti-Aliasing für weichere Kanten des Kreises
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color); // Setzt die Farbe für den Kreis
        g.fillOval(0, 0, diameter, diameter); // Zeichnet den Kreis
        g.dispose();

        return new ImageIcon(image); // Gibt das erstellte Icon zurück
    }
    
    // Methode, die ein spezielles Icon für eine Dame erstellt. 
    // Es wird ein größerer Kreis für den Hauptstein und ein kleinerer Kreis oben für die Dame dargestellt.
    public Icon createDameIcon(Color color) {
        return new Icon() {
            private final int size = 60; // Größe des Hauptkreises (Dame)

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                // Zeichnet den Hauptkreis (Hauptstein)
                g.setColor(color);
                g.fillOval(x, y, size, size);

                // Zeichnet einen kleineren, helleren Kreis oben, um die Dame darzustellen
                g.setColor(Color.GRAY); // Farbe für den kleineren Kreis
                int smallerSize = size / 2; // Größe des kleineren Kreises
                g.fillOval(x + (size - smallerSize) / 2, y + (size - smallerSize) / 2, smallerSize, smallerSize);
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }
    
    // Zeigt eine Nachricht an, die den Gewinner des Spiels verkündet.
    public void displayWinner(String winner) {
        JOptionPane.showMessageDialog(null, winner + " hat gewonnen!", "Spielende", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Getter-Methode, die das 2D-Array der Buttons zurückgibt. Dies ist nützlich für die Steuerung der Interaktionen.
    public JButton[][] getButtons() {
        return buttons;
    }
}