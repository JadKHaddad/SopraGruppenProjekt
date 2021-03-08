package de.wwu.sopra.view;

import de.wwu.sopra.controller.ProbenSteuerung;
import de.wwu.sopra.controller.StudienSteuerung;
import de.wwu.sopra.controller.data.BidVerwaltung;
import de.wwu.sopra.controller.data.ProbenVerwaltung;
import de.wwu.sopra.model.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Verwaltet die Ansicht des VisitenDurchfuehrungsFormulars
 * @author Gruppe 5
 */
public class VisitenDurchfuehrungsFormular extends View {

    GridPane pane;
    PatientenTermin patientenTermin;
    private boolean alleFelderGefuellt;
    private ArrayList<Integer> bids;
    private Set<Probe> proben;
    

    /**
     * Erstellt ein VisitenDurchfuehrungsFormular
     * @param tabSteuerung die Tabsteuerung
     * @param patientenTermin die Patienten Termin
     */
    public VisitenDurchfuehrungsFormular(TabSteuerung tabSteuerung, PatientenTermin patientenTermin) {
        this.patientenTermin = patientenTermin;
        bids = new ArrayList<Integer>();
        pane = new GridPane();
        pane.setHgap(32);
        pane.setVgap(6);
        ArrayList<TextField> messFelder = new ArrayList<TextField>();
        ProbenVerwaltung probenVerwaltung = ProbenVerwaltung.getInstance();
        
        Text meldung = new Text();
        meldung.setFill(Color.CRIMSON);
       

        GridPane gridpane = new GridPane();
        pane.add(gridpane, 1, 1);

        if (!this.patientenTermin.isDurchgefuehrt()) {
            for (int i = 0; i < this.patientenTermin.getVisite().getMesstypen().size(); i++) {
                gridpane.add(new Text(this.patientenTermin.getVisite().getMesstypen().get(i).getName() + "   "), 0, i);
                TextField textField = new TextField();
                messFelder.add(textField);
                textField.setDisable(true);
                gridpane.add(textField, 1, i);
                gridpane.add(new Text("   " + this.patientenTermin.getVisite().getMesstypen().get(i).getEinheit()), 2, i);
                if(i == this.patientenTermin.getVisite().getMesstypen().size() - 1) {
                    gridpane.add(meldung, 1, i + 2);
                }
            }
        } else {
            for (int i = 0; i < this.patientenTermin.getVisite().getMesstypen().size(); i++) {
                gridpane.add(new Text(this.patientenTermin.getVisite().getMesstypen().get(i).getName() + "   "), 0, i);
                TextField textField = new TextField("" + this.patientenTermin.getMessungen().get(i).getWert());
                messFelder.add(textField);
                textField.setDisable(true);
                gridpane.add(textField, 1, i);
                gridpane.add(new Text("   " + this.patientenTermin.getVisite().getMesstypen().get(i).getEinheit()), 2, i);
            }
        }

        boolean[] messFeldGueltig = new boolean[messFelder.size()];

        TableView<ProbenInfo> probenTable = new TableView<ProbenInfo>();
        TableColumn<ProbenInfo, String> probenNameCol = new TableColumn<ProbenInfo, String>("Probenname");
        TableColumn<ProbenInfo, String> mengeCol = new TableColumn<ProbenInfo, String>("Menge");

        TableView<Probe> probenIdTable = new TableView<Probe>();
        TableColumn<Probe, String> probenNameIdCol = new TableColumn<Probe, String>("Probenname");
        TableColumn<Probe, String> mengeIdCol = new TableColumn<Probe, String>("Menge");
        TableColumn<Probe, String> idCol = new TableColumn<Probe, String>("ID");

        
        
        Button zurueck = new Button("Zurück");
        pane.add(zurueck, 0, 2);
        zurueck.setTooltip(new Tooltip("Zurück zur Auswahl"));
        zurueck.setOnAction(event -> {
            tabSteuerung.pop();
        });
        
        
        if (patientenTermin.isDurchgefuehrt()) {
            pane.add(probenIdTable, 0, 1);
           
        } else {
            pane.add(probenTable, 0, 1);
        }

        Button absagen = new Button("Visite absagen");
        absagen.setOnAction(event -> {
            patientenTermin.setAbgesagt(true);
            tabSteuerung.pop();
        });
        Button speichern = new Button(
                "Bestätige, dass alle Proben entnommen wurden \nund die Messungen korrekt eingetragen wurden");
        speichern.setDisable(true);

        Button durchfuehren = new Button("Visite starten");
        durchfuehren.setStyle("-fx-background-color: #98fb98; -fx-border-color: #000000");
        durchfuehren.setOnAction(event -> {
            if (patientenTermin.getVisite().getMesstypen().isEmpty())
                speichern.setDisable(false);
            for (TextField tx : messFelder)
                tx.setDisable(false);
            for (int i = 0; i < patientenTermin.getVisite().getProbenInfos().size(); i++)
                bids.add(BidVerwaltung.getInstance().getBid());
            durchfuehren.setDisable(true);
        });

        speichern.setOnAction(event -> {

            for (int i = 0; messFelder.size() > i; i++) {
                patientenTermin.addMessung(new Messung(messFelder.get(i).getText(),
                        patientenTermin.getVisite().getMesstypen().get(i), this.patientenTermin));
            }

            for (int i = 0; i < patientenTermin.getVisite().getProbenInfos().size(); i++) {
                Probe p = new Probe(bids.get(i), LocalDateTime.now(),
                        patientenTermin.getVisite().getProbenInfos().get(i), ProbenStatus.NEU, null, patientenTermin,
                        patientenTermin.getVisite().getProbenInfos().get(i).getBehaeltertyp());
                probenVerwaltung.addProbe(p);
                ProbenSteuerung.getInstance().fuegeProbeEin(p);
            }
            // Notify MTAs
            patientenTermin.setDurchgefuehrt(true);
            String proben = "";
            for (int i = 0; i < patientenTermin.getProben().size(); i++) {
                Probe probe = patientenTermin.getProben().get(i);
                proben += "Bid: " + probe.getBid() + "     " + probe.getInfo().getKategorie().getName() + " "
                        + probe.getInfo().getMenge() + probe.getInfo().getKategorie().getEinheit() + "\n";
            }
            if (!patientenTermin.getProben().isEmpty())
                new InfoPopup("Neue Proben", proben, () -> {
                });
            tabSteuerung.pop();

        }); 

        
        for (int i = 0; i < messFelder.size(); i++) {
            TextField textField = messFelder.get(i);
            final int finalI = i;
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                messFeldGueltig[finalI] = !newValue.isBlank();
                boolean alleFelderGefuellt = true;
                for (int j = 0; j < messFeldGueltig.length; j++) {
                    alleFelderGefuellt = alleFelderGefuellt && messFeldGueltig[j];
                }
                if(alleFelderGefuellt) {
                    meldung.setText("");
                }
                else {
                    meldung.setText("Alle Felder müssen ausgefüllt sein.");
                }

                speichern.setDisable(!alleFelderGefuellt);
            });
        }

        if (!this.patientenTermin.isAbgesagt() && !this.patientenTermin.isDurchgefuehrt()) {
            pane.add(durchfuehren, 0, 0);
            pane.add(absagen, 1, 0);
            pane.add(speichern, 1, 2);

        }

        probenNameCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ProbenInfo, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ProbenInfo, String> param) {
                        return new ReadOnlyObjectWrapper<String>(param.getValue().getKategorie().getName());
                    }
                });

        mengeCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ProbenInfo, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<ProbenInfo, String> param) {
                        return new ReadOnlyObjectWrapper<String>(
                                param.getValue().getMenge() + " " + param.getValue().getKategorie().getEinheit());
                    }
                });

        probenNameIdCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Probe, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Probe, String> param) {
                        return new ReadOnlyObjectWrapper<String>(param.getValue().getInfo().getKategorie().getName());
                    }
                });

        mengeIdCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Probe, String>, ObservableValue<String>>() {

                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Probe, String> param) {
                        return new ReadOnlyObjectWrapper<String>(param.getValue().getInfo().getMenge() + " "
                                + param.getValue().getInfo().getKategorie().getEinheit());
                    }
                });

        idCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Probe, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(CellDataFeatures<Probe, String> param) {
                return new ReadOnlyObjectWrapper<String>("" + param.getValue().getBid());
            }
        });

        ObservableList<ProbenInfo> list = FXCollections
                .observableList(new LinkedList<>(this.patientenTermin.getVisite().getProbenInfos()));
        probenTable.setItems(list);

        ObservableList<Probe> listId = FXCollections.observableList(new LinkedList<>(patientenTermin.getProben()));
        probenIdTable.setItems(listId);

        probenTable.getColumns().add(probenNameCol);
        probenTable.getColumns().add(mengeCol);

        probenIdTable.getColumns().add(idCol);
        probenIdTable.getColumns().add(probenNameIdCol);
        probenIdTable.getColumns().add(mengeIdCol);

    }
    
    /**
     * Gibt die Hauptnode des Fenster zurueck
     */
    @Override
    public Node getNode() {
        return pane;
    }
    
    /**
     * Gibt den Namen an, der in den Breadcrumbs angezeigt wird
     */
    @Override
    public String getName() {
        return "Visite von: " + patientenTermin.getStudienTeilnehmer().getPatient().getVorname() + " "
                + patientenTermin.getStudienTeilnehmer().getPatient().getName();
    }

}
