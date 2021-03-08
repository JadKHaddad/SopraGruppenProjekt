package de.wwu.sopra.view;

import java.util.HashSet;
import java.util.Set;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Rolle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Darstellung des BenutzerBearbeitenFormulars
 * @author Gruppe 5
 */
public class BenutzerBearbeitenFormular extends View {

    private TabSteuerung tabSteuerung;

    private GridPane pane;
    private TextField benutzernameField, nameField, vornameField;
    private boolean gueltigeNameEingabe = true;
    private boolean gueltigeVornameEingabe = true;
    private boolean gueltigeBenutzerNameEingabe = true;
    private boolean gueltigeRollenEingabe = true;

    /**
     * Erstellt ein BenutzerBearbeitenFormular
     * @param tabSteuerung die Tabsteuerung
     * @param benutzer der Benutzer
     */
    public BenutzerBearbeitenFormular(TabSteuerung tabSteuerung, Benutzer benutzer) {
        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);
        
        CheckBox studynurseCheckBox = new  CheckBox("Study Nurse");
        CheckBox personalabteilungsleiterCheckBox = new CheckBox("Personalleiter");

        CheckBox biobankleiterCheckBox = new CheckBox("Biobankleiter");
        CheckBox mtaCheckBox = new CheckBox("MTA");
        if (benutzer.hasRolle(Rolle.STUDY_NURSE)){
            studynurseCheckBox.setSelected(true);
        }
        if (benutzer.hasRolle(Rolle.BIOBANKLEITER)){
            biobankleiterCheckBox.setSelected(true);
        }
        if (benutzer.hasRolle(Rolle.MTA)){
            mtaCheckBox.setSelected(true);
        }
        if (benutzer.hasRolle(Rolle.PERSONALLEITER)){
            personalabteilungsleiterCheckBox.setSelected(true);
        }
        Text falscherName = new Text("Name darf nicht leer sein");
        falscherName.setFill(Color.RED);
        falscherName.setVisible(false);
        pane.add(falscherName, 2, 0);

        Text falscherVorname = new Text("Vorname darf nicht leer sein");
        falscherVorname.setFill(Color.RED);
        falscherVorname.setVisible(false);
        pane.add(falscherVorname, 2, 1);

        Text benutzernameUngueltig = new Text();
        benutzernameUngueltig.setFill(Color.RED);
        benutzernameUngueltig.setVisible(false);
        pane.add(benutzernameUngueltig, 2, 2);

        Text falscheRolle = new Text("es muss mindestens eine Rolle ausgewählt werden");
        falscheRolle.setFill(Color.RED);
        falscheRolle.setVisible(false);
        pane.add(falscheRolle, 2, 5);

        pane.add(new Label("Benutzername"), 0, 2);
        benutzernameField = new TextField(benutzer.getBenutzerName());
        pane.add(benutzernameField, 1, 2);

        pane.add(new Label("Name"), 0, 0);
        nameField = new TextField(benutzer.getName());
        pane.add(nameField, 1, 0);

        pane.add(new Label("Vorname"), 0, 1);
        vornameField = new TextField(benutzer.getVorname());
        pane.add(vornameField, 1, 1);

        pane.add(new Label("Rollen:"), 0, 3);

        pane.add(studynurseCheckBox, 0, 4);
        pane.add(personalabteilungsleiterCheckBox, 0, 5);
        pane.add(biobankleiterCheckBox, 1, 4);
        pane.add(mtaCheckBox, 1, 5);

        Button bearbeiten = new Button("OK");
        bearbeiten.setOnAction(event -> {
            benutzer.setBenutzerName(benutzernameField.getText());
            benutzer.setName(nameField.getText());
            benutzer.setVorname(vornameField.getText());
            
            if(studynurseCheckBox.isSelected()) {
                benutzer.addRolle(Rolle.STUDY_NURSE);
            } else {
                benutzer.removeRolle(Rolle.STUDY_NURSE);
            }
            if(personalabteilungsleiterCheckBox.isSelected()) {
                benutzer.addRolle(Rolle.PERSONALLEITER);
            } else {
                benutzer.removeRolle(Rolle.PERSONALLEITER);
            }
            if(biobankleiterCheckBox.isSelected()) {
                benutzer.addRolle(Rolle.BIOBANKLEITER);
            } else {
                benutzer.removeRolle(Rolle.BIOBANKLEITER);
            }
            if(mtaCheckBox.isSelected()) {
                benutzer.addRolle(Rolle.MTA);
            } else {
                benutzer.removeRolle(Rolle.MTA);
            }

            tabSteuerung.pop();
        });
        pane.add(bearbeiten, 0, 8);

        Button passwortNeu = new Button("Neues Passwort vergeben");
        pane.add(passwortNeu, 0, 6);
        passwortNeu.setOnAction(event -> {
            BenutzerSteuerung bs = BenutzerSteuerung.getInstance();
            String passwort = bs.genPasswort();
            BenutzerVerwaltung bv = BenutzerVerwaltung.getInstance();
            Benutzer benutzerAendern = null;

            for (Benutzer i : bv.getBenutzerSet()) {
                if (i.getBenutzerName().contentEquals(benutzernameField.getText())) {
                    benutzerAendern = i;
                }
            }
            bs.passwortAendern(benutzerAendern, passwort);
            InfoPopup popup = new InfoPopup("Neues Passwort", "Das neue Passwort von " + vornameField.getText() + " " + nameField.getText()
                    + " lautet: " + passwort, null);
        });

        Button abbrechen = new Button("Zurück");
        abbrechen.setTooltip(new Tooltip("Zurück zur Benutzerauswahl"));
        abbrechen.setOnAction(event -> {
            tabSteuerung.pop();
        });
        pane.add(abbrechen, 1, 8);

        ChangeListener<Boolean> checkboxListener = new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                gueltigeRollenEingabe = ((studynurseCheckBox.isSelected()
                        || personalabteilungsleiterCheckBox.isSelected() || biobankleiterCheckBox.isSelected()
                        || mtaCheckBox.isSelected()));

                falscheRolle.setVisible(!gueltigeRollenEingabe);
                bearbeiten.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
                        && gueltigeRollenEingabe));

            }
        };

        studynurseCheckBox.selectedProperty().addListener(checkboxListener);
        personalabteilungsleiterCheckBox.selectedProperty().addListener(checkboxListener);
        biobankleiterCheckBox.selectedProperty().addListener(checkboxListener);
        mtaCheckBox.selectedProperty().addListener(checkboxListener);

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeNameEingabe = !newValue.isEmpty();
            falscherName.setVisible(!gueltigeNameEingabe);
            bearbeiten.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
                    && gueltigeRollenEingabe));
            passwortNeu.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
                    && gueltigeRollenEingabe));

        });
        vornameField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeVornameEingabe = !newValue.isEmpty();
            falscherVorname.setVisible(!gueltigeVornameEingabe);
            bearbeiten.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
                    && gueltigeRollenEingabe));
            passwortNeu.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
                    && gueltigeRollenEingabe));
        });
        benutzernameField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.isEmpty()) {
                benutzernameUngueltig.setText("Benutzername darf nicht leer sein");
                benutzernameUngueltig.setVisible(true);
                gueltigeBenutzerNameEingabe = false;

            } else if (!newValue.matches("([0-9]|[a-z]|[A-Z]|_|-|\\.)+")) {
                benutzernameUngueltig.setText("ungültige Eingabe");
                benutzernameUngueltig.setVisible(true);
                gueltigeBenutzerNameEingabe = false;

            } else if (BenutzerSteuerung.getInstance().istBenutzernameVerwendet(newValue, benutzer)) {
                benutzernameUngueltig.setText("Benutzer vorhanden");
                benutzernameUngueltig.setVisible(true);
                gueltigeBenutzerNameEingabe = false;
            } else {
                gueltigeBenutzerNameEingabe = true;
                benutzernameUngueltig.setVisible(false);

            }
            bearbeiten.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
                    && gueltigeRollenEingabe));
            passwortNeu.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
                    && gueltigeRollenEingabe));

        });

    }

    /**
     * @return Die Hauptnode des Fensters.
     */
    @Override
    public Node getNode() {
        return pane;
    }

    /**
     * @return Der Name des Formulars, der bei den Breadcrumbs angezeigt werden sollen.
     */
    @Override
    public String getName() {
        return "Benutzer bearbeiten";
    }
}
