package de.wwu.sopra.view;

import de.wwu.sopra.controller.ProbenSteuerung;
import de.wwu.sopra.controller.RackSteuerung;
import de.wwu.sopra.controller.data.ProbenVerwaltung;
import de.wwu.sopra.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class RackAnsichtFormular extends View {
    TabSteuerung tabSteuerung;
    Rack rack;
    GridPane pane = new GridPane();
    String rackName;
    ProbenPlatz platz;

    private Circle[][] circles;
    private Circle letzterCircle;
    Text id = new Text();
    Text datum = new Text();
    Text menge = new Text();
    Text kategorie = new Text();
    Text einheit = new Text();
    Text minTemperatur = new Text();
    Text maxTemperatur = new Text();
    Text status = new Text();
    Text messungen = new Text();
    VBox vbox;
    Text behaeltertyp;
    Button probeloeschen;
    Button deleteButton;
    Button zurueckButton;
    Button bearbeiten;
    Button weiterverarbeiten;

    public RackAnsichtFormular(TabSteuerung tabSteuerung, Rack rack, String rackName, Probe startProbe) {
        this.tabSteuerung = tabSteuerung;
        this.rack = rack;
        this.rackName = rackName;

        if(startProbe != null) {
            platz = startProbe.getProbenplatz();
        }

        pane.setVgap(8);
        pane.setHgap(100);

        vbox = new VBox();
        vbox.setAlignment(Pos.BASELINE_LEFT);
        vbox.setSpacing(8);
        behaeltertyp = new Text("Behältertyp: " + rack.getBehaeltertyp().getName());
        behaeltertyp.setStyle("-fx-font-weight: bold");
        vbox.getChildren().add(behaeltertyp);
        vbox.getChildren().add(id);
        vbox.getChildren().add(status);
        vbox.getChildren().add(menge);
        vbox.getChildren().add(einheit);
        vbox.getChildren().add(minTemperatur);
        vbox.getChildren().add(maxTemperatur);
        vbox.getChildren().add(kategorie);

        circles = new Circle[rack.getSpalten()][rack.getZeilen()];

        letzterCircle = null;


        probeloeschen = new Button("Probe löschen",
                new ImageView(new Image(getClass().getResourceAsStream("/remove.png"))));

        probeloeschen.setOnAction(event -> {
            ProbenVerwaltung.getInstance().removeProbe(rack.getProbe(platz.getSpalte(), platz.getZeile()));
            rack.setProbe(platz.getSpalte(), platz.getZeile(), null);

            probeloeschen.setVisible(false);
            bearbeiten.setVisible(false);
            weiterverarbeiten.setVisible(false);
            refresh();
        });

        probeloeschen.setVisible(false);
        deleteButton = new Button("Rack löschen",
                new ImageView(new Image(getClass().getResourceAsStream("/remove.png"))));
        deleteButton.setTooltip(new Tooltip("Rack löschen"));
        deleteButton.setOnAction(event -> {
            if (RackSteuerung.getInstance().istRackLeer(rack)) {
                RackSteuerung.getInstance().removeRack(rack);
                tabSteuerung.pop();
            } else {
                new InfoPopup("Fehler", "Nur das Löschen von leeren und nicht reservierten Racks ist möglich." + "\n\n"
                        + "Entfernen Sie bitte zunächst alle Proben aus dem Rack.", () -> {});
            }
        });

        zurueckButton = new Button("Zurück");
        zurueckButton.setOnAction(event -> {
            tabSteuerung.pop();
        });

        bearbeiten = new Button("Bearbeiten");
        bearbeiten.setVisible(false);
        bearbeiten.setOnAction(event -> {
            tabSteuerung.push(new ProbeBearbeitenFormular(tabSteuerung, platz, rack));
            refresh();
        });
        
        weiterverarbeiten = new Button("Weiterverarbeiten");
        weiterverarbeiten.setVisible(false);
        weiterverarbeiten.setOnAction(event -> {
            tabSteuerung.push(new ProbeWeiterverarbeitenFormular(tabSteuerung, platz.getProbe()));
            refresh();
        });

        HBox hb = new HBox();
        hb.getChildren().add(probeloeschen);
        hb.getChildren().add(bearbeiten);
        hb.getChildren().add(weiterverarbeiten);
        hb.setSpacing(6);
        vbox.getChildren().add(hb);
        GridPane paneForProben = new GridPane();

        for (int y = 0; y < rack.getZeilen(); y++) {
            int yy = y;
            Text indexText = new Text(Integer.toString(y + 1));
            indexText.setStyle("-fx-font-weight: bold");
            GridPane.setMargin(indexText, new Insets(4, 4, 4, 4));
            paneForProben.add(indexText, y + 1, 0);

            for (int x = 0; x < rack.getSpalten(); x++) {
                int xx = x;
                Circle probenPlatz = new Circle();
                circles[x][y] = probenPlatz;
                probenPlatz.setRadius(8);
                probenPlatz.setStroke(Color.TRANSPARENT);

                probenPlatz.setOnMouseClicked(event -> {
                    if (letzterCircle != null)
                        letzterCircle.setStroke(Color.TRANSPARENT);
                    probenPlatz.setStroke(Color.BLACK);
                    letzterCircle = probenPlatz;
                    platz = rack.getProbenplatz(xx, yy);

                    setzteAktuelleProbe(platz.getProbe());
                });

                faerbeProbeNeuEin(x, y);

                probenPlatz.setOnMouseEntered(event -> {
                    probenPlatz.setFill(Color.GRAY);
                });
                probenPlatz.setOnMouseExited(event -> {
                    faerbeProbeNeuEin(xx, yy);
                });
                Text indexText2 = new Text(RackSteuerung.getInstance().getLetterString(x + 1));
                indexText2.setStyle("-fx-font-weight: bold");
                paneForProben.add(indexText2, 0, x + 1);
                GridPane.setMargin(indexText2, new Insets(4, 4, 4, 4));
                GridPane.setMargin(probenPlatz, new Insets(3, 3, 3, 3));
                paneForProben.add(probenPlatz, x + 1, y + 1);
            }
        }

        Text bidText = new Text("Rack ID: " + rack.getBid());
        bidText.setStyle("-fx-font-weight: bold");
        bidText.setStyle("-fx-font-size: 18px;");
        pane.add(bidText, 0, 0);
        pane.add(paneForProben, 0, 1);

        HBox hbox = new HBox();
        hbox.getChildren().add(deleteButton);
        hbox.getChildren().add(zurueckButton);
        hbox.setSpacing(6);
        pane.add(hbox, 0, 2);

        pane.add(vbox, 1, 1);

        // Startprobe
        if (startProbe != null) {
            letzterCircle = circles[startProbe.getProbenplatz().getSpalte()][startProbe.getProbenplatz().getZeile()];
            letzterCircle.setStroke(Color.BLACK);
            setzteAktuelleProbe(startProbe);
        }
    }

    public void refresh() {
        Probe aktuelleProbe = null;
        for (int x = 0; x < circles.length; x++) {
            for (int y = 0; y < circles[0].length; y++) {
                Circle probenPlatz = circles[x][y];
                probenPlatz.setRadius(8);
                if(probenPlatz == letzterCircle)
                    aktuelleProbe = rack.getProbenplatz(x, y).getProbe();
                faerbeProbeNeuEin(x, y);
            }
        }
        setzteAktuelleProbe(aktuelleProbe);
    }

    /**
     * Faerbt die Probe neu ein
     * @param x Die Spalte der Probe
     * @param y Die Zeile der Probe
     */
    private void faerbeProbeNeuEin(int x, int y) {
        Probe probe = rack.getProbe(x, y);
        if (probe != null) {
            switch (probe.getStatus()) {
                case NEU:
                    circles[x][y].setFill(Color.LIGHTGRAY);
                    break;
                case SOLL_VERNICHTET_WERDEN:
                    circles[x][y].setFill(Color.DARKGRAY);
                    break;
                default:
                    circles[x][y].setFill(Color.RED);
                    break;
            }

        } else if (ProbenSteuerung.getInstance().isProbenPlatzReserviert(rack.getProbenplatz(x, y))) {
            circles[x][y].setFill(Color.ORANGE);
        } else {
            circles[x][y].setFill(Color.GREEN);
        }
    }

    /**
     * Setzt die Aktuelle Probe
     * @param probe Die Probe
     */
    private void setzteAktuelleProbe(Probe probe) {
        if (probe == null) {
            id.setText("");
            status.setText("");
            menge.setText("");
            kategorie.setText("");
            datum.setText("");
            einheit.setText("");
            minTemperatur.setText("");
            maxTemperatur.setText("");
            probeloeschen.setVisible(false);
            bearbeiten.setVisible(false);
            weiterverarbeiten.setVisible(false);
        } else {
            id.setText("ID: " + probe.getBid());
            datum.setText("Entnahme Datum: " + probe.getEntnahmeDatum());
            menge.setText("Menge: " + probe.getInfo().getMenge());
            kategorie.setText("Kategorie: " + probe.getInfo().getKategorie());
            einheit.setText("Einheit: " + probe.getInfo().getKategorie().getEinheit());
            minTemperatur.setText(
                "Minimale Lagertemperatur: " + probe.getInfo().getKategorie().getMinLagerTemp());
            maxTemperatur.setText(
                "Maximale Lagertemperatur: " + probe.getInfo().getKategorie().getMaxLagerTemp());
            status.setText("Status: " + probe.getStatus().getName());
            boolean veraenderbar = !(probe.getStatus() == ProbenStatus.SOLL_VERNICHTET_WERDEN ||
                probe.getStatus() == ProbenStatus.NEU);
            probeloeschen.setVisible(veraenderbar);
            bearbeiten.setVisible(veraenderbar);
            weiterverarbeiten.setVisible(veraenderbar);
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
        return rackName;
    }

}
