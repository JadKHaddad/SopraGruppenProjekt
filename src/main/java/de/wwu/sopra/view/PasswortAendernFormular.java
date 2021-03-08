package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Popup;

/**
 * Verwaltet die Ansicht des PasswortAendernFormulars
 * 
 * @author Gruppe 5
 */
public class PasswortAendernFormular extends View {

    private GridPane grid;
    private boolean gueltigesAltesPW, gueltigesNeuesPW1, gueltigesNeuesPW2, neuePWGleich;

    /**
     * Erzeugt ein PasswortAendernFormular
     * 
     * @param tabSteuerung steuert die Tabanzeige
     */
    public PasswortAendernFormular(TabSteuerung tabSteuerung) {

        // GrundLayout festlegen
        grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(4);

        // Komponenten erstellen
        Text altesPWText = new Text("Altes Passwort ");
        PasswordField altesPW = new PasswordField();
        Text neuesPW1Text = new Text("Neues Passwort ");
        PasswordField neuesPW1 = new PasswordField();
        Text neuesPW2Text = new Text("Neues Passwort wiederholen ");
        PasswordField neuesPW2 = new PasswordField();
        Text meldung1 = new Text("   ");
        Text meldung2 = new Text("   ");
        Text meldung3 = new Text("   ");
        meldung1.setFill(Color.RED);
        meldung2.setFill(Color.RED);
        meldung3.setFill(Color.RED);
        Button speichern = new Button("Speichern");
        Button zurueck = new Button("Zurück");

        // die Komponenten mit Koordinaten versehen
        GridPane.setConstraints(altesPWText, 0, 0);
        GridPane.setConstraints(altesPW, 0, 2);
        GridPane.setConstraints(neuesPW1Text, 0, 4);
        GridPane.setConstraints(neuesPW1, 0, 6);
        GridPane.setConstraints(neuesPW2Text, 0, 8);
        GridPane.setConstraints(neuesPW2, 0, 10);
        GridPane.setConstraints(meldung1, 0, 12, 10, 1, null, null);
        GridPane.setConstraints(meldung2, 0, 13, 10, 1, null, null);
        GridPane.setConstraints(meldung3, 0, 14, 10, 1, null, null);
        GridPane.setConstraints(speichern, 0, 16);
        GridPane.setConstraints(zurueck, 2, 16);

        // die Komponenten hinzufuegen
        grid.getChildren().add(altesPW);
        grid.getChildren().add(altesPWText);
        grid.getChildren().add(neuesPW1);
        grid.getChildren().add(neuesPW1Text);
        grid.getChildren().add(neuesPW2);
        grid.getChildren().add(neuesPW2Text);
        grid.getChildren().add(meldung1);
        grid.getChildren().add(meldung2);
        grid.getChildren().add(meldung3);
        grid.getChildren().add(speichern);
        grid.getChildren().add(zurueck);

        // Funktionalität für den Button Abbrechen
        zurueck.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                meldung1.setText(" ");
                meldung2.setText(" ");
                meldung3.setText(" ");
                tabSteuerung.pop();
            }
        });

        // Funktionalität für den Button Speichern
        speichern.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                String eingabeAltesPW = altesPW.getText();
                String eingabeNeuesPW1 = neuesPW1.getText();
                String eingabeNeuesPW2 = neuesPW2.getText();
                BenutzerSteuerung bs = BenutzerSteuerung.getInstance();
                String benutzerName = bs.getAktiverBenutzer().getBenutzerName();

                // Eingaben auf Gültigkeit überprüfen
                if (eingabeNeuesPW1.length() < 8 || neuesPW1.getText().isBlank()) {
                    gueltigesNeuesPW1 = false;
                } else {
                    gueltigesNeuesPW1 = true;
                }
                if (eingabeNeuesPW2.length() < 8 || neuesPW2.getText().isBlank()) {
                    gueltigesNeuesPW2 = false;
                } else {
                    gueltigesNeuesPW2 = true;
                }
                if (!(eingabeNeuesPW1.equals(eingabeNeuesPW2))) {
                    neuePWGleich = false;
                } else {
                    neuePWGleich = true;
                }
                if ((!(bs.login(benutzerName, eingabeAltesPW))) || altesPW.getText().isBlank()) {
                    gueltigesAltesPW = false;
                } else {
                    gueltigesAltesPW = true;
                }

                //passende Fehlermeldungen ausgeben bzw Passwort ändern
                if (!gueltigesAltesPW) {
                    meldung1.setText("Das alte Passwort ist ungültig.");
                } else {
                    meldung1.setText("");
                }
                if (!gueltigesNeuesPW1) {
                    meldung2.setText("Das neue Passwort muss mindestens acht Zeichen lang sein.");
                } else {
                    meldung2.setText("");
                }
                if (!neuePWGleich) {
                    meldung3.setText(
                            "Die Wiederholung des neuen Passworts stimmt nicht mit dem neuen Passwort überein.");
                } else {
                    meldung3.setText("");
                }
                if (gueltigesAltesPW && gueltigesNeuesPW1 && gueltigesNeuesPW2 && neuePWGleich) {
                    bs.passwortAendern(eingabeAltesPW, eingabeNeuesPW1, eingabeNeuesPW2);
                    new InfoPopup("Erfolgreich", "Ihr Passwort wurde erfolgreich geändert!", () -> {
                    });
                    tabSteuerung.pop();
                }
            }
        });
    }

    /**
     * Gibt die Node zurueck, die der "root" des ProbenbestandRaumFormulars ist.
     * 
     * @return Die root-Node des ProbenbestandRaumFormulars.
     */
    public Node getNode() {
        return grid;
    }

    /**
     * Gibt den Namen an, der bei den Breadcrumbs angezeigt wird.
     * 
     * @return der Name
     */
    @Override
    public String getName() {
        return "Passwort ändern";
    }

}
