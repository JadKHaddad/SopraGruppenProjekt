package de.wwu.sopra.view;

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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
/**
 * Darstellung des BenutzerErstellenFormulars
 * @author Gruppe 5
 *
 */
public class BenutzerErstellenFormular extends View {

    private TabSteuerung tabSteuerung;

    private GridPane pane;
    private TextField nameField, vornameField, benutzernameField;
    private boolean gueltigeNameEingabe = false;
    private boolean gueltigeVornameEingabe = false;
    private boolean gueltigeBenutzerNameEingabe = false;
    private boolean gueltigeRollenEingabe = false;
    String color = new String();
   
    

    /**
     * Erstellt ein BenutzerErstellenFormular
     * 
     * @param tabSteuerung die Tabsteuerung
     */
    public BenutzerErstellenFormular(TabSteuerung tabSteuerung) {
        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);

        pane.add(new Label("Name"), 0, 0);
        nameField = new TextField();
        pane.add(nameField, 1, 0);
        Text falscherName = new Text("Name darf nicht leer sein");
        falscherName.setFill(Color.RED);
        falscherName.setVisible(false);
        pane.add(falscherName, 2, 0);

        pane.add(new Label("Vorname"), 0, 1);
        vornameField = new TextField();
        pane.add(vornameField, 1, 1);
        Text falscherVorname = new Text("Vorname darf nicht leer sein");
        falscherVorname.setFill(Color.RED);
        falscherVorname.setVisible(false);
        pane.add(falscherVorname, 2, 1);

        pane.add(new Label("Benutzername"), 0, 2);

        benutzernameField = new TextField();
        pane.add(benutzernameField, 1, 2);

        Text benutzernameUngueltig = new Text();
        benutzernameUngueltig.setFill(Color.RED);
        benutzernameUngueltig.setVisible(false);
        pane.add(benutzernameUngueltig, 2, 2);

        pane.add(new Label("Rollen:"), 0, 3);
        CheckBox studynurseCheckBox = new CheckBox("Study Nurse");
        pane.add(studynurseCheckBox, 0, 5);
        
        CheckBox personalabteilungsleiterCheckBox = new CheckBox("Personalleiter");
        pane.add(personalabteilungsleiterCheckBox, 0, 6);
        
        Text falscheRolle = new Text("es muss mindestens eine Rolle ausgewählt werden");
        falscheRolle.setFill(Color.RED);
        falscheRolle.setVisible(true);
        pane.add(falscheRolle, 2, 5);
        
        CheckBox biobankleiterCheckBox = new CheckBox("Biobankleiter");
        pane.add(biobankleiterCheckBox, 1, 5);
        
        CheckBox mtaCheckBox = new CheckBox("MTA");
        pane.add(mtaCheckBox, 1, 6);
        
        Button benutzerAnlegen = new Button("Benutzer anlegen");
        pane.add(benutzerAnlegen, 0, 7);
        
        Button abb = new Button("Abbrechen");
        abb.setOnAction(event -> {
            tabSteuerung.pop();
        });
        pane.add(abb, 1, 7);
        color = abb.getStyle();       

        ChangeListener<Boolean> checkboxListener = new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                gueltigeRollenEingabe = ((studynurseCheckBox.isSelected()
                        || personalabteilungsleiterCheckBox.isSelected() || biobankleiterCheckBox.isSelected()
                        || mtaCheckBox.isSelected()));

                falscheRolle.setVisible(!gueltigeRollenEingabe);
                benutzerAnlegen.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe
                        && gueltigeBenutzerNameEingabe && gueltigeRollenEingabe));

            }
        };

        studynurseCheckBox.selectedProperty().addListener(checkboxListener);
        personalabteilungsleiterCheckBox.selectedProperty().addListener(checkboxListener);
        biobankleiterCheckBox.selectedProperty().addListener(checkboxListener);
        mtaCheckBox.selectedProperty().addListener(checkboxListener);

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeNameEingabe = !newValue.isEmpty();
            if(nameField.getText().contentEquals("Willemsen")) {
                abb.setStyle("-fx-background-color: Fuchsia ");
            }
            else {
                abb.setStyle(color);
            }
            falscherName.setVisible(!gueltigeNameEingabe);
            benutzerAnlegen.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
                    && gueltigeRollenEingabe));

        });
        vornameField.textProperty().addListener((observable, oldValue, newValue) -> {
            gueltigeVornameEingabe = !newValue.isEmpty();
            if(vornameField.getText().contentEquals("Lisa")) {
                benutzerAnlegen.setStyle("-fx-background-color: Fuchsia");
            }
            else {
                benutzerAnlegen.setStyle(color);
            }
            falscherVorname.setVisible(!gueltigeVornameEingabe);
            benutzerAnlegen.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
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

            } else if (BenutzerSteuerung.getInstance().istBenutzernameVerwendet(newValue, null)) {
                benutzernameUngueltig.setText("Benutzer vorhanden");
                benutzernameUngueltig.setVisible(true);
                gueltigeBenutzerNameEingabe = false;
            } else {
                gueltigeBenutzerNameEingabe = true;
                benutzernameUngueltig.setVisible(false);
            }
            benutzerAnlegen.setDisable(!(gueltigeNameEingabe && gueltigeVornameEingabe && gueltigeBenutzerNameEingabe
                    && gueltigeRollenEingabe));

        });

        benutzerAnlegen.setDisable(true);

        benutzerAnlegen.setOnAction(event -> {
            BenutzerSteuerung bs = BenutzerSteuerung.getInstance();
            String passwort = bs.genPasswort();
            Benutzer b = bs.erstelleBenutzer(benutzernameField.getText(), passwort,
                    nameField.getText(), vornameField.getText());
            BenutzerVerwaltung.getInstance().addBenutzer(b);
            if (studynurseCheckBox.isSelected()) {
                b.addRolle(Rolle.STUDY_NURSE);
            }
            if (personalabteilungsleiterCheckBox.isSelected()) {
                b.addRolle(Rolle.PERSONALLEITER);
            }
            if (biobankleiterCheckBox.isSelected()) {
                b.addRolle(Rolle.BIOBANKLEITER);
            }
            if (mtaCheckBox.isSelected()) {
                b.addRolle(Rolle.MTA);
            }
            
            InfoPopup popup = new InfoPopup("Initiales Passwort", "Das initiale Passwort von " + 
            b.getVorname() + " " + b.getName() + " lautet: " + passwort + ".", null);
            
            tabSteuerung.pop();
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
        return "Benutzer anlegen";
    }
}
