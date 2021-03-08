package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.model.Kuehlschrank;
import de.wwu.sopra.model.Raum;
import de.wwu.sopra.model.Rolle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Verwaltet die Ansciht des ProbenbestandKuehlschrankFormulars
 * 
 * @author Gruppe 5
 */
public class ProbenbestandKuehlschrankFormular extends View {

    private GridPane gridPane;
    private Raum raum;
    private TableView<Kuehlschrank> kuehlschrankTable;
    private boolean isLeiter;

    /**
     * Erzeugt ein ProbenbestandKuehlschrankFomrular
     * 
     * @param tabSteuerung die Steuerung
     * @param raum         der Raum
     */
    public ProbenbestandKuehlschrankFormular(TabSteuerung tabSteuerung, Raum raum) {
        if (BenutzerSteuerung.getInstance().getAktiverBenutzer().hasRolle(Rolle.BIOBANKLEITER))
            isLeiter = true;
        else
            isLeiter = false;

        this.raum = raum;
        kuehlschrankTable = new TableView<Kuehlschrank>();
        gridPane = new GridPane();
        Button button = new Button("Neuen Kühlschrank hinzufügen",
                new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        button.setTooltip(new Tooltip("Neuen Kuehlschrank hinzufügen"));

        button.setOnAction(event -> {

            tabSteuerung.push(new KuehlschrankErstellenFormular(tabSteuerung, raum));
        });

        Text ueberschrift = new Text("Kühlschränke in " + raum.getName());
        ueberschrift.setStyle("-fx-font-weight: bold");
        ueberschrift.setStyle("-fx-font-size: 18px;");

        gridPane.add(ueberschrift, 0, 0);
        if (isLeiter)
            gridPane.add(button, 0, 1);
        gridPane.setVgap(8);
        kuehlschrankTable = new TableView<>();
        gridPane.add(kuehlschrankTable, 0, 2);

        TableColumn<Kuehlschrank, Float> nameCol = new TableColumn<>("Name");
        TableColumn<Kuehlschrank, Float> breiteCol = new TableColumn<>("Breite");
        TableColumn<Kuehlschrank, Float> tiefeCol = new TableColumn<>("Tiefe");
        TableColumn<Kuehlschrank, Float> hoeheCol = new TableColumn<>("Höhe");
        TableColumn<Kuehlschrank, Float> temperaturCol = new TableColumn<>("Temperatur");
        TableColumn<Kuehlschrank, Button> removeCol = new TableColumn<>();
        removeCol.setSortable(false);
        TableColumn<Kuehlschrank, Button> editCol = new TableColumn<>();
        editCol.setSortable(false);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        breiteCol.setCellValueFactory(new PropertyValueFactory<>("breite"));
        tiefeCol.setCellValueFactory(new PropertyValueFactory<>("tiefe"));
        hoeheCol.setCellValueFactory(new PropertyValueFactory<>("hoehe"));
        temperaturCol.setCellValueFactory(new PropertyValueFactory<>("Temperatur"));

        removeCol.setCellFactory(ActionButtonTableCell.forTableColumn(
                new Image(getClass().getResourceAsStream("/remove.png")), "Löschen", (Kuehlschrank p) -> {

                    boolean kuelschrankLeer = true;
                    for (int i = 0; i < p.getAnzahlSegmente(); i++) {
                        for (int j = 0; j < p.getSegment(i).GESTELLE_PRO_SEGMENT; j++) {
                            for (int k = 0; k < p.getSegment(i).getGestell(j).SCHUBLADEN_PRO_GESTELL; k++) {
                                for (int l = 0; l < p.getSegment(i).getGestell(j).getSchublade(k)
                                        .getRacks().length; l++) {
                                    if (p.getSegment(i).getGestell(j).getSchublade(k).getRack(l) != null)
                                        kuelschrankLeer = false;
                                }

                            }
                        }
                    }
                    if (kuelschrankLeer) {
                        raum.removeKuehlschrank(p);
                        ObservableList<Kuehlschrank> list = FXCollections
                                .observableList(new LinkedList<>(raum.getKuehlschraenke()));
                        kuehlschrankTable.setItems(list);
                    } else {
                        new InfoPopup("Fehler", "Löschen des Kühlschrankes nicht möglich, da dieser nicht leer ist."
                                + "\n\n" + "Entfernen Sie zunächst alle Racks aus dem Kühlschrank", () -> {
                                });
                    }

                    return p;
                }));

        editCol.setCellFactory(ActionButtonTableCell.forTableColumn(
                new Image(getClass().getResourceAsStream("/edit.png")), "Bearbeiten", (Kuehlschrank p) -> {
                    tabSteuerung.push(new KuehlschrankBearbeitenFormular(tabSteuerung, p));
                    return p;
                }));

        // display
        ObservableList<Kuehlschrank> list = FXCollections.observableList(new LinkedList<>(raum.getKuehlschraenke()));
        kuehlschrankTable.setItems(list);

        kuehlschrankTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    if(BenutzerSteuerung.getInstance().getAktiverBenutzer().hasRolle(Rolle.MTA)) {
                    tabSteuerung.push(new KuehlschrankAnsichtFormular(tabSteuerung,
                            kuehlschrankTable.getSelectionModel().getSelectedItem()));
                    }
                }
            }
        });

        kuehlschrankTable.getColumns().add(nameCol);
        kuehlschrankTable.getColumns().add(breiteCol);
        kuehlschrankTable.getColumns().add(tiefeCol);
        kuehlschrankTable.getColumns().add(hoeheCol);
        kuehlschrankTable.getColumns().add(temperaturCol);
        if (isLeiter) {
            kuehlschrankTable.getColumns().add(editCol);
            kuehlschrankTable.getColumns().add(removeCol);
        }
    }

    @Override
    public void onOpen() {
        ObservableList<Kuehlschrank> list = FXCollections.observableList(new LinkedList<>(raum.getKuehlschraenke()));
        kuehlschrankTable.setItems(list);
        kuehlschrankTable.refresh();
    }

    /**
     * Gibt die Node zurück, die der "root" des ProbenbestandKuehlschrankFormulars
     * ist.
     * 
     * @return Die root-Node des ProbenbestandKuehlschrankFormulars.
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
        return raum.getName();
    }
}
