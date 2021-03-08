package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.controller.data.PatientenVerwaltung;
import de.wwu.sopra.model.Patient;
import de.wwu.sopra.model.PatientenTermin;
import de.wwu.sopra.model.Rolle;
import de.wwu.sopra.model.Studie;
import de.wwu.sopra.model.Studienteilnehmer;
import de.wwu.sopra.model.Visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Verwaltet die Ansicht des PatientenAuswahlFormulars
 * 
 * @author Gruppe 5
 */
public class PatientenAuswahlFormular extends View {

    GridPane pane;
    Studie studie;

    private TableView<Patient> patientTable, teilnehmerTable;
    private ArrayList<Patient> patienten, teilnehmer;
    /**
     * Erstellt ein PatientenAuswahlFormular
     * 
     * @param tabSteuerung die Tabsteuerung
     * @param studie       die Studie
     */
    public PatientenAuswahlFormular(TabSteuerung tabSteuerung, Studie studie) {
        studie.setBearbeitbar(false);
        pane = new GridPane();
        this.studie = studie;

        ArrayList<Studienteilnehmer> studienteilnehmer = this.studie.getTeilnehmer();

        patientTable = new TableView<Patient>();
        pane.add(patientTable, 0, 1);

        teilnehmerTable = new TableView<Patient>();
        pane.add(teilnehmerTable, 1, 1);

        PatientenVerwaltung patienteVerwaltung = PatientenVerwaltung.getInstance();

        patienten = new ArrayList<Patient>(patienteVerwaltung.getPatientenSet());

        teilnehmer = new ArrayList<Patient>();

        for (Studienteilnehmer st : studienteilnehmer)
            teilnehmer.add(st.getPatient());

        for (Patient p : teilnehmer)
            patienten.remove(p);

        ObservableList<Patient> list = FXCollections.observableList(patienten);
        patientTable.setItems(list);

        pane.setHgap(32);
        pane.setVgap(6);

        Text vPatienten = new Text("Verfügbare Patienten:");
        Text aPatienten = new Text("Ausgewählte Patienten:");

        pane.add(vPatienten, 0, 0);
        pane.add(aPatienten, 1, 0);

        TableColumn<Patient, String> patientenVorNameCol = new TableColumn<Patient, String>("Vorname");
        TableColumn<Patient, String> patientenNameCol = new TableColumn<Patient, String>("Nachname");

        TableColumn<Patient, Button> removeCol = new TableColumn<Patient, Button>();
        removeCol.setSortable(false);
        TableColumn<Patient, Button> editCol = new TableColumn<Patient, Button>();
        editCol.setSortable(false);
        TableColumn<Patient, Button> addCol = new TableColumn<Patient, Button>();
        addCol.setSortable(false);

        TableColumn<Patient, Button> removeCol2 = new TableColumn<Patient, Button>();
        removeCol2.setSortable(false);

        patientenVorNameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        patientenNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        removeCol.setCellFactory(ActionButtonTableCell.forTableColumn(
                new Image(getClass().getResourceAsStream("/remove.png")), "Aus System löschen", (Patient p) -> {
                    new BestaetigenPopup("Warnung", "Wollen Sie diesen Patienten wirklich aus dem System entfernen?" + "\n" + "\n" + "Alle Patientendaten und dessen Messungen werden gelöscht und alle Proben werden zur Vernichtung freigegeben!", () -> {
                        patienteVerwaltung.removePatient(p);
                        patienten.remove(p);
                        patientTable.refresh();
                    }, () ->  {});
                    
                    return p;
                }));

        editCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/edit.png")), "Bearbeiten", (Patient p) -> {
                    tabSteuerung.push(new PatientenBearbeitenFormular(tabSteuerung, p));
                    return p;
                }));

        addCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/add.png")), "Hinzufügen", (Patient p) -> {
                    if (teilnehmer.size() >= studie.getAnzTeilnehmer())
                        new InfoPopup("Zu viele Patienten", "Für diese Studie können maximal "
                                + studie.getAnzTeilnehmer() + " Patienten ausgewählt werden!", () -> {
                                });
                    else {
                        patienten.remove(p);
                        teilnehmer.add(p);
                        patientTable.refresh();
                        teilnehmerTable.refresh();
                    }
                    return p;
                }));

        patientTable.getColumns().add(patientenVorNameCol);
        patientTable.getColumns().add(patientenNameCol);

        patientTable.getColumns().add(addCol);

        if (BenutzerSteuerung.getInstance().getAktiverBenutzer().hasRolle(Rolle.STUDY_NURSE)) {
            patientTable.getColumns().add(editCol);
            patientTable.getColumns().add(removeCol);
        }

        TableColumn<Patient, String> teilnehmerVorNameCol = new TableColumn<Patient, String>("Vorname");
        TableColumn<Patient, String> teilnehmerNameCol = new TableColumn<Patient, String>("Nachname");

        teilnehmerVorNameCol.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        teilnehmerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Patient, Button> entfernenCol = new TableColumn<Patient, Button>();
        entfernenCol.setSortable(false);

        removeCol2.setCellFactory(ActionButtonTableCell.forTableColumn(
                new Image(getClass().getResourceAsStream("/remove.png")), "Aus System löschen", (Patient p) -> {
                    new BestaetigenPopup("Warnung", "Wollen Sie diesen Patienten wirklich aus dem System entfernen?" + "\n" + "\n" + "Alle Patientendaten und dessen Messungen werden gelöscht und alle Proben werden zur Vernichtung freigegeben!", () -> {
                        patienteVerwaltung.removePatient(p);
                        teilnehmer.remove(p);
                        teilnehmerTable.refresh();
                    }, () ->  {});
                    return p;
                }));

        entfernenCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/minus.png")), "Entfernen", (Patient p) -> {
                    patienten.add(p);
                    teilnehmer.remove(p);
                    patientTable.refresh();
                    teilnehmerTable.refresh();
                    return p;
                }, (patient, button) -> {
                    button.setDisable(false);
                    for(Studienteilnehmer st: studienteilnehmer) {
                        if(st.getPatient().equals(patient))
                            button.setDisable(true);                            
                    }
                }));

        ObservableList<Patient> listSt = FXCollections.observableList(teilnehmer);
        teilnehmerTable.setItems(listSt);

        teilnehmerTable.getColumns().add(teilnehmerVorNameCol);
        teilnehmerTable.getColumns().add(teilnehmerNameCol);

        teilnehmerTable.getColumns().add(entfernenCol);
        teilnehmerTable.getColumns().add(removeCol2);

        Button neu = new Button("Neuen Patienten anlegen");
        neu.setTooltip(new Tooltip("Neuen Patienten anlegen"));

        neu.setOnAction(event -> {
            tabSteuerung.push(new PatientenAnlegeFormular(tabSteuerung));
        });

        Button anwenden = new Button("Anwenden");
        anwenden.setTooltip(new Tooltip("Anwenden"));
        anwenden.setOnAction(event -> {


            for (Studienteilnehmer st : studienteilnehmer) {
                if (!teilnehmer.contains(st.getPatient())) {
                    studienteilnehmer.remove(st);
                    st.setPatient(null);
                    st.setStudie(null);
                }
            }


            for (Patient patient : teilnehmer) {
                boolean enthalten = false;
                for (Studienteilnehmer studienT : studienteilnehmer) {
                    if (studienT.getPatient().equals(patient))
                        enthalten = true;
                }
                if (!enthalten) {
                    Studienteilnehmer st = new Studienteilnehmer(patient, studie);
                    for (Visite v : studie.getVisiten())
                        st.addPatientenTermin(new PatientenTermin(v, null, st));
                    studie.addTeilnehmer(st);
                }
            }


            tabSteuerung.pop();
        });

        if (BenutzerSteuerung.getInstance().getAktiverBenutzer().hasRolle(Rolle.STUDY_NURSE))
            pane.add(neu, 0, 2);
        pane.add(anwenden, 1, 2);

    }

    /**
     * Gibt die Hauptnode des Fensters zurueck
     */
    @Override
    public Node getNode() {
        return pane;
    }

    /**
     * Gibt den Namen an, der bei den Breadcrumbs angezeigt werden soll
     */
    @Override
    public String getName() {
        return "Patienten auswählen";
    }

    /**
     * Verwaltet, was passieren soll wenn das PatientenAuswahlFormular geöffnet wird
     */
    @Override
    public void onOpen() {
        PatientenVerwaltung patienteVerwaltung = PatientenVerwaltung.getInstance();

        patienten = new ArrayList<Patient>(patienteVerwaltung.getPatientenSet());

        for (Patient p : teilnehmer)
            patienten.remove(p);

        ObservableList<Patient> list = FXCollections.observableList(patienten);
        patientTable.setItems(list);

        patientTable.refresh();
    }
}
