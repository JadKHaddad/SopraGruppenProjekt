package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Verwaltet die Übersicht und Erstellung von Kuehlschraenken
 * 
 * @author Gruppe 5
 */
public class KuehlschrankAnsichtFormular extends View {

    private GridPane pane;
    private Kuehlschrank eigenerkuehlschrank;
    private Button[][][] nodes;
    private VBox vboxForKuehlschrank;

    /**
     * Erstellet das Formular
     * 
     * @param tabSteuerung die Tabsteuerung
     * @param kuehlschrank der Kuehlschrank
     */
    public KuehlschrankAnsichtFormular(TabSteuerung tabSteuerung, Kuehlschrank kuehlschrank) {
        eigenerkuehlschrank = kuehlschrank;

        pane = new GridPane();

        nodes = new Button[kuehlschrank
                .getAnzahlSegmente()][Segment.GESTELLE_PRO_SEGMENT][Schublade.RACKS_PRO_SCHUBLADE];

        // erstelle Text für Kühlschrank Name
        Text keuhlschrankNameText = new Text(kuehlschrank.getName());
        keuhlschrankNameText.setStyle("-fx-font-weight: bold");
        keuhlschrankNameText.setStyle("-fx-font-size: 18px;");
        // erstelle Buttons
        Button zurueckButton = new Button("Zurück zur Kühlschrankauswahl");
        zurueckButton.setTooltip(new Tooltip("Zurück zur Kühlschrankauswahl"));

        // erstelle für den Kühlschrank ein VBox und für jedes Segment ein Hbox
        // gestelle von den Segmenten kommen als VBoxen in die Hboxen von einem Segment
        // schubladen von gestellen kommen in die VBoxen von Segmenten

        // verstelle VBox für Kühlschrank

        vboxForKuehlschrank = new VBox();
        ScrollPane scroll = new ScrollPane();
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scroll.setContent(vboxForKuehlschrank);
        if (eigenerkuehlschrank.getAnzahlSegmente() > 0) {
            // vboxForKuehlschrank Settings
            vboxForKuehlschrank
                    .setStyle("-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-color: black;");
            vboxForKuehlschrank.setMaxSize(100, 100);

            // erstelle für jedes Segment ein HBox
            for (int k = 0; k < eigenerkuehlschrank.getAnzahlSegmente(); k++) {
                int kk = k;

                HBox hboxForSegment = new HBox(5);

                for (int i = 0; i < Segment.GESTELLE_PRO_SEGMENT; i++) {
                    int ii = i;
                    VBox vboxForGestell = new VBox(5);
                    for (int j = 0; j < Schublade.RACKS_PRO_SCHUBLADE; j++) {
                        int jj = j;
                        // erstelle nodes für schubladen
                        nodes[k][i][j] = new Button();
                        // nodes Settings
                        boolean schubladeLeer = true;
                        for (Rack rack : kuehlschrank.getSegment(kk).getGestell(ii).getSchublade(jj).getRacks()) {
                            if (rack != null)
                                schubladeLeer = false;
                        }
                        String HOVERED_BUTTON_STYLE;

                        if (!schubladeLeer)
                            HOVERED_BUTTON_STYLE = "-fx-background-color: #98fb98";
                        else
                            HOVERED_BUTTON_STYLE = "";

                        nodes[k][i][j].setStyle(HOVERED_BUTTON_STYLE);
                        nodes[k][i][j].setMinWidth(30);
                        nodes[k][i][j].setMaxWidth(30);
                        nodes[k][i][j].setPrefWidth(30);
                        nodes[k][i][j].setMinHeight(15);
                        nodes[k][i][j].setMaxHeight(15);
                        nodes[k][i][j].setPrefHeight(15);
                        nodes[k][i][j].setTooltip(new Tooltip(
                                "Segment: " + (kk + 1) + ", Gestell: " + (ii + 1) + ", Schublade: " + (jj + 1)));
                        // nodes Funktionen festlegen

                        nodes[k][i][j].setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                // öffne das Fenster für die schublade:
                                String schubladeName = "Segment " + (kk + 1) + ", Gestell " + (ii + 1) + ", Schublade "
                                        + (jj + 1);
                                tabSteuerung.push(new SchubladeAnsichtFormular(tabSteuerung,
                                        kuehlschrank.getSegment(kk).getGestell(ii).getSchublade(jj), schubladeName));
                            }
                        });

                        // nodes in vboxForGestell hinzufügen
                        vboxForGestell.getChildren().add(nodes[k][i][j]);
                    }

                    hboxForSegment.getChildren().add(vboxForGestell);

                }

                // elemente in vboxForKuehlschrank hinzufügen
                vboxForKuehlschrank.setMargin(hboxForSegment, new Insets(10, 10, 10, 10));
                vboxForKuehlschrank.getChildren().add(hboxForSegment);

            }

            // pane Settings
            pane.setVgap(10);
            pane.setHgap(10);
            // füge elemte in pane hinzu

            pane.add(scroll, 0, 0);
            pane.add(zurueckButton, 0, 1);

            // ButtonFunktionen festlegen
            zurueckButton.setOnAction((event) -> {
                tabSteuerung.pop();
            });
        }

    }

    @Override
    public void onOpen() {
        for (int k = 0; k < eigenerkuehlschrank.getAnzahlSegmente(); k++) {
            for (int i = 0; i < Segment.GESTELLE_PRO_SEGMENT; i++) {
                for (int j = 0; j < Schublade.RACKS_PRO_SCHUBLADE; j++) {
                    boolean schubladeLeer = true;
                    for (Rack rack : eigenerkuehlschrank.getSegment(k).getGestell(i).getSchublade(j).getRacks()) {
                        if (rack != null)
                            schubladeLeer = false;
                    }
                    String HOVERED_BUTTON_STYLE;

                    if (!schubladeLeer)
                        HOVERED_BUTTON_STYLE = "-fx-background-color: #98fb98";
                    else
                        HOVERED_BUTTON_STYLE = "";

                    nodes[k][i][j].setStyle(HOVERED_BUTTON_STYLE);
                }
            }
        }
    }


    /**
     * Gibt die Hauptnode des Fensters zurueck
     */
    @Override
    public Node getNode() {

        return pane;
    }

    /**
     * Der Name des Formulars, das bei den Breadcrumbs angezeigt werden soll
     */
    @Override
    public String getName() {

        return eigenerkuehlschrank.getName();
    }

}
