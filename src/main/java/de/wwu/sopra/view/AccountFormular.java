package de.wwu.sopra.view;

import java.time.LocalDateTime;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.PatientenTermin;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.ProbenInfo;
import de.wwu.sopra.model.Rolle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Darstellung des Accountvormulars
 * @author Gruppe 5
 */
public class AccountFormular extends View {

    private BorderPane border;

    /**
     * Erzeugt das AccountFormular
     * @param tabSteuerung steuert die Tabanzeige
     */
    public AccountFormular(TabSteuerung tabSteuerung) {

        BenutzerSteuerung bs = BenutzerSteuerung.getInstance();

        // erstelle eine BorderPane als Grundlegendes Layout mit left right und bottom
        // Region
        border = new BorderPane();
        border.setPrefSize(500, 500);
        VBox right = new VBox();
        VBox bottom = new VBox();

        VBox left = new VBox();
        border.setLeft(left);
        border.setRight(right);
        border.setBottom(bottom);
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        left.getChildren().add(hbox1);
        left.getChildren().add(hbox2);

        // Erstellen aller Textfelder und des Buttons
        Text nameText = new Text();
        Text vornameText = new Text();
        Text benutzerNameText = new Text();
        Button passwortaendern = new Button("Passwort ändern");
        Text benutzerNameTextFest = new Text("Benutzername:");
        Text rollenText = new Text("Rollen");
        Text bbLeiterText = new Text("Biobank-Leiter");
        Text mtaText = new Text("MTA");
        Text pLeiterText = new Text("Personalleiter");
        Text studyNurseText = new Text("Study Nurse");

        // die Komponenten stylen
        rollenText.setStyle("-fx-font-weight: bold");
        vornameText.setStyle("-fx-font-weight: bold");
        nameText.setStyle("-fx-font-weight: bold");
        rollenText.setStyle("-fx-font-size: 18px;");
        vornameText.setStyle("-fx-font-size: 18px;");
        nameText.setStyle("-fx-font-size: 18px;");

        // die Layouts anpassen
        right.setSpacing(6);
        hbox1.setSpacing(6);
        hbox2.setSpacing(6);
        left.setSpacing(6);

        // alle Komponenten zu right hinzufuegen
        // später duerfen nur die Rollen hinzugefuegt werden, die der Benutzer hat.
        // Alternativ koennte man den Text auf ""setzen

        Benutzer benutzer = bs.getAktiverBenutzer();
        right.getChildren().add(rollenText);
        if (benutzer.hasRolle(Rolle.BIOBANKLEITER)) {
            right.getChildren().add(bbLeiterText);
        }
        if (benutzer.hasRolle(Rolle.PERSONALLEITER)) {
            right.getChildren().add(pLeiterText);
        }
        if (benutzer.hasRolle(Rolle.STUDY_NURSE)) {
            right.getChildren().add(studyNurseText);
        }
        if (benutzer.hasRolle(Rolle.MTA)) {
            right.getChildren().add(mtaText);
        }


        // den Button zum bottom hinzufuegen
        bottom.getChildren().add(passwortaendern);

        // allen Komponenten aus Left hinzufuegen
        hbox1.getChildren().add(vornameText);
        hbox1.getChildren().add(nameText);
        hbox2.getChildren().add(benutzerNameTextFest);
        hbox2.getChildren().add(benutzerNameText);

        // setzen des Vornamens, Namens und Benutzernamens und den richtigen Wert
        // zum Testen: Default Werte, diese müssen später geändert werden
        nameText.setText(bs.getAktiverBenutzer().getName());
        vornameText.setText(bs.getAktiverBenutzer().getVorname());
        benutzerNameText.setText(bs.getAktiverBenutzer().getBenutzerName());

        // Funktionalitaet auf den Button setzen
        // es soll ein neues Fenster geoeffnet haben

        passwortaendern.setTooltip(new Tooltip("Hier klicken um das Passwort zu ändern."));
        passwortaendern.setOnAction(new EventHandler<ActionEvent>() {
            /**
             * Oeffnet eine neues Fenster
             */
            public void handle(ActionEvent event) {
                PasswortAendernFormular passwortAendernFormular = new PasswortAendernFormular(tabSteuerung);
                tabSteuerung.push(passwortAendernFormular);
            }
        });
    }

    /**
     * Gibt die Node zurueck, die der "root" des ProbenbestandRaumFormulars ist.
     * @return Die root-Node des ProbenbestandRaumFormulars.
     */
    public Node getNode() {
        return border;
    }

    /**
     * Gibt den Namen an, der bei den Breadcrumbs angezeigt wird.
     * @return der Name
     */
    @Override
    public String getName() {
        return "Account";
    }

}
