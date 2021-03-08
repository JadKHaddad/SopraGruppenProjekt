package de.wwu.sopra.view;

import de.wwu.sopra.controller.ProbenSteuerung;
import de.wwu.sopra.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SchubladeAnsichtFormular extends View {
    TabSteuerung tabSteuerung;
    Schublade schublade;

    private GridPane pane;
    private String name;
    private VBox vbox;


    public SchubladeAnsichtFormular(TabSteuerung tabSteuerung, Schublade schublade, String schubladeName) {
        this.schublade = schublade;
        this.name = schubladeName;
        this.tabSteuerung = tabSteuerung;
        pane = new GridPane();
        pane.setVgap(8);
        vbox = new VBox();
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scroll.setFitToWidth(true);
        Button zurueckButton = new Button("Zurück zur Schubladenauswahl");
        refresh();
        scroll.setContent(vbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        pane.add(scroll, 0, 0);
        pane.add(zurueckButton, 0, 1);
        zurueckButton.setOnAction(event -> {
            tabSteuerung.pop();
        });

    }

    public void refresh() {
        vbox.getChildren().clear();
        for (int i = 0; i < Schublade.RACKS_PRO_SCHUBLADE; i++) {
            int ii = i;
            Rack rack = schublade.getRack(i);
            if (rack == null) {
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(80);
                rectangle.setWidth(120);
                rectangle.setFill(null);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(2);
                vbox.getChildren().add(rectangle);
            } else {
                GridPane paneForRack = new GridPane();
                paneForRack.setAlignment(Pos.CENTER);
                paneForRack.setStyle(
                    "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-color: black;");
                paneForRack.setOnMouseClicked(event -> {
                    tabSteuerung.push(
                        new RackAnsichtFormular(tabSteuerung, rack, "Rack " + (ii + 1), null));
                });
                paneForRack.setVgap(3);
                paneForRack.setHgap(3);
                paneForRack.setPadding(new Insets(5));

                paneForRack.setOnMouseEntered(event -> {
                    paneForRack.setStyle(
                        "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-color: gray;");
                });
                paneForRack.setOnMouseExited(event -> {
                    paneForRack.setStyle(
                        "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-color: black;");
                });



                for (int x = 0; x < rack.getSpalten(); x++) {
                    for (int y = 0; y < rack.getZeilen(); y++) {

                        Circle circleForProbenPlatz = new Circle();
                        circleForProbenPlatz.setRadius(rack.getBehaeltertyp().getRadius() * 2);

                        faerbeProbeNeuEin(rack.getProbenplatz(x, y), circleForProbenPlatz);

                        paneForRack.add(circleForProbenPlatz, x, y);
                    }
                }
                vbox.getChildren().add(paneForRack);
            }
        }
    }

    /**
     * Faerbt die Probe neu ein
     * @param probenPlatz der Platz der Probe
     * @param circle Der Circle, der neu eingefaerbt werden soll.
     */
    private void faerbeProbeNeuEin(ProbenPlatz probenPlatz, Circle circle) {
        Probe probe = probenPlatz.getProbe();
        if (probe != null) {
            switch (probe.getStatus()) {
                case NEU:
                    circle.setFill(Color.LIGHTGRAY);
                    break;
                case SOLL_VERNICHTET_WERDEN:
                    circle.setFill(Color.DARKGRAY);
                    break;
                default:
                    circle.setFill(Color.RED);
                    break;
            }
        } else if (ProbenSteuerung.getInstance().isProbenPlatzReserviert(probenPlatz)) {
            circle.setFill(Color.ORANGE);
        } else {
            circle.setFill(Color.GREEN);
        }
    }

    @Override
    public void onOpen() {
        refresh();
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public String getName() {
        return name;
    }

}
