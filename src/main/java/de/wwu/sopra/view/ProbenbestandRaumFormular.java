package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.controller.RaumSteuerung;
import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.controller.data.ProbenVerwaltung;
import de.wwu.sopra.model.Gestell;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Probe;
import de.wwu.sopra.model.Rack;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Rolle;
import de.wwu.sopra.model.Schublade;
import de.wwu.sopra.model.Segment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

/**
 * Verwaltet die Ansicht des ProbenbestandRaumFormulars
 * 
 * @author Gruppe 5
 */
public class ProbenbestandRaumFormular extends View {

    private boolean isLeiter;
    private GridPane gridPane;
    private TableView<Raum> raumTable;

    /**
     * Erzeugt ein ProbenbestandRaumFormular
     * 
     * @param tabSteuerung die Steuerung
     */
    public ProbenbestandRaumFormular(TabSteuerung tabSteuerung) {

        gridPane = new GridPane();
        
        gridPane.setHgap(32);
        gridPane.setVgap(6);


        if (BenutzerSteuerung.getInstance().getAktiverBenutzer().hasRolle(Rolle.BIOBANKLEITER))
            isLeiter = true;
        else
            isLeiter = false;

        Button button = new Button("Neuen Raum anlegen",
                new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        button.setTooltip(new Tooltip("Neuen Raum hinzufügen"));
        button.setOnAction(event -> {
            RaumErstellenFormular raumErstellenFormular = new RaumErstellenFormular(tabSteuerung);
            tabSteuerung.push(raumErstellenFormular);
        });
        if (isLeiter)
            gridPane.add(button, 0, 0);
        gridPane.setVgap(8);
        raumTable = new TableView<Raum>();
        gridPane.add(raumTable, 0, 1);

        Text suchen = new Text("Rack oder Probe über ID suchen:");
        TextField idEingabe = new TextField();
        Button suchButton = new Button("Suchen");
        suchButton.setTooltip(new Tooltip("Suchen"));
        suchButton.setDisable(true);
        
        Text fehlerNachricht = new Text("Ungültige Eingabe. \n Nur Dezimalzahlen erlaubt.");
        fehlerNachricht.setFill(Color.CRIMSON);
        fehlerNachricht.setVisible(false);
        
        gridPane.add(fehlerNachricht, 2, 2);
        

        String decimalPattern = "(\\d+)";

        idEingabe.textProperty().addListener((observable, oldValue, newValue) -> {
            fehlerNachricht.setVisible(!(newValue.matches(decimalPattern)));
            suchButton.setDisable(!(newValue.matches(decimalPattern)));
        });
        suchButton.setOnAction(event -> {
            Pair<Rack, Probe> pair = RaumSteuerung.getInstance().bidSuchen(Integer.parseInt(idEingabe.getText()));
            Probe zielProbe = pair.getValue();
            Rack zielRack = pair.getKey();
            

            if (zielProbe != null)
                zielRack = pair.getValue().getProbenplatz().getRack();



            if (zielProbe != null || zielRack != null) {
                Schublade zielSchublade = zielRack.getSchublade();
                Gestell zielGestell = zielSchublade.getGestell();
                Segment zielSegment = zielGestell.getSegment();
                Kuehlschrank zielKuehlschrank = zielSegment.getKuehlschrank();
                Raum zielRaum = zielKuehlschrank.getRaum();
                String schubladeName = "Segment " + (zielSegment.getStelleImKuehlschrank() + 1) + ", Gestell "
                        + (zielGestell.getStelleImSegment() + 1) + ", Schublade "
                        + (zielSchublade.getStelleImGestell() + 1);
                
                tabSteuerung.push(new ProbenbestandKuehlschrankFormular(tabSteuerung, zielRaum));
                tabSteuerung.push(new KuehlschrankAnsichtFormular(tabSteuerung, zielKuehlschrank));
                tabSteuerung.push(new SchubladeAnsichtFormular(tabSteuerung, zielSchublade, schubladeName));
                if (zielProbe ==  null)
                    tabSteuerung.push(new RackAnsichtFormular(tabSteuerung, zielRack,
                            "Rack " + zielRack.getStelleInSchublade(), null));
                else
                    tabSteuerung.push(new RackAnsichtFormular(tabSteuerung, zielRack,
                            "Rack " + zielRack.getStelleInSchublade(), zielProbe));
 
            } else {
                new InfoPopup("Hinweis", "Im System wurde keine Probe und kein Rack mit dieser ID gefunden", () -> {});
            }

        });

        HBox hbox = new HBox();
        hbox.setSpacing(6);
        hbox.getChildren().add(suchen);
        hbox.getChildren().add(idEingabe);
        hbox.getChildren().add(suchButton);

        if (BenutzerSteuerung.getInstance().getAktiverBenutzer().hasRolle(Rolle.MTA))
            gridPane.add(hbox, 0, 2);

        TableColumn<Raum, String> roomNameCol = new TableColumn<Raum, String>("Raumname");
        TableColumn<Raum, Float> flaecheCol = new TableColumn<Raum, Float>("Fläche");
        TableColumn<Raum, Float> hoeheCol = new TableColumn<Raum, Float>("Höhe");
        TableColumn<Raum, Button> removeCol = new TableColumn<Raum, Button>();
        removeCol.setSortable(false);
        TableColumn<Raum, Button> editCol = new TableColumn<Raum, Button>();
        editCol.setSortable(false);

        roomNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        flaecheCol.setCellValueFactory(new PropertyValueFactory<>("quadratmeter"));
        hoeheCol.setCellValueFactory(new PropertyValueFactory<>("hoehe"));

        removeCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/remove.png")), "Löschen", (Raum p) -> {
                    if (p.getKuehlschraenke().isEmpty()) {
                        LagerVerwaltung.getInstance().removeRaum(p);
                        Set<Raum> raumSet = LagerVerwaltung.getInstance().getRaumSet();
                        ObservableList<Raum> list = FXCollections.observableList(new LinkedList<>(raumSet));
                        raumTable.setItems(list);
                    } else {
                        new InfoPopup("Fehler", "Löschen des Raumes nicht möglich, da dieser nicht leer ist." + "\n\n"
                                + "Entfernen Sie zunächst alle Kühlschränke aus dem Raum", () -> {
                                });
                    }

                    return p;
                }));
        editCol.setCellFactory(ActionButtonTableCell
                .forTableColumn(new Image(getClass().getResourceAsStream("/edit.png")), "Bearbeiten", (Raum p) -> {
                    if (p.getKuehlschraenke().size() == 0){
                        tabSteuerung.push(new RaumBearbeitenFormular(tabSteuerung, p));
                    }else{
                        new InfoPopup("Raum nicht bearbeitbar", "Ein Raum kann nur bearbeitet werden, wenn er leer ist.", ()->{});
                    }
                    return p;
                }));

        // display
        Set<Raum> raumSet = LagerVerwaltung.getInstance().getRaumSet();
        ObservableList<Raum> list = FXCollections.observableList(new LinkedList<>(raumSet));
        raumTable.setItems(list);

        raumTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    tabSteuerung.push(new ProbenbestandKuehlschrankFormular(tabSteuerung,
                            raumTable.getSelectionModel().getSelectedItem()));
                }
            }
        });

        raumTable.getColumns().add(roomNameCol);
        raumTable.getColumns().add(flaecheCol);
        raumTable.getColumns().add(hoeheCol);
        if (isLeiter) {
            raumTable.getColumns().add(editCol);
            raumTable.getColumns().add(removeCol);
        }
    }

    @Override
    public void onOpen() {
        Set<Raum> raumSet = LagerVerwaltung.getInstance().getRaumSet();
        ObservableList<Raum> list = FXCollections.observableList(new LinkedList<>(raumSet));
        raumTable.setItems(list);
        raumTable.refresh();
    }

    /**
     * Gibt die Node zurück, die der "root" des ProbenbestandRaumFormulars ist.
     * 
     * @return Die root-Node des ProbenbestandRaumFormulars.
     */
    public Node getNode() {
        return gridPane;
    }

    /**
     * Gibt den Namen an, der bei den Breadcrumbs angezeigt wird.
     * 
     * @return der Name
     */
    @Override
    public String getName() {
        return "Räume";
    }
}
