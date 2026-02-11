package dame;

public class main {

    // Die main-Methode ist der Einstiegspunkt für das Programm.
    // Sie initialisiert die Benutzeroberfläche und den dazugehörigen Controller für das Menü.
	public static void main(String[] args) {
        // Erstellt eine neue Instanz der MenuView, die für die Anzeige des Menüs zuständig ist.
		MenuView menuView = new MenuView();
        
        // Erstellt eine neue Instanz des MenuController und verbindet ihn mit der MenuView.
        // Der Controller steuert die Interaktionen zwischen der Benutzeroberfläche (MenuView) und der zugrundeliegenden Logik.
        MenuController menuController = new MenuController(menuView);
	}
}