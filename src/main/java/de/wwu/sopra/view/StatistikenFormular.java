package de.wwu.sopra.view;

import java.util.Random;

import de.wwu.sopra.controller.data.LagerVerwaltung;
import de.wwu.sopra.controller.data.ProbenVerwaltung;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Verwaltet die Ansicht des StatistikenFormulars
 * 
 * @author Gruppe 5
 */
public class StatistikenFormular extends View {
    private PieChart pieChart = new PieChart();
    private PieChart.Data slice1 = new PieChart.Data("Belegter Probenplatz",
            ProbenVerwaltung.getInstance().getProbenSet().size());
    private PieChart.Data slice2 = new PieChart.Data("Verfügbarer Probenplatz",
            LagerVerwaltung.getInstance().getAnzProbenplaetze() - ProbenVerwaltung.getInstance().getProbenSet().size());
    private GridPane gridPane;
    private Random random = new Random();
    private int bananen;
    private Text maxRacksLabel = new Text();
    private Text anzRacksLabel = new Text();
    private Text gesamteAnzahlProben = new Text();
    private Text belgterPlatzProzent = new Text();
    Text t = new Text("Bananen Pro Mitarbeiter: ");

    /**
     * Erstellt ein StatistikenFormular
     * 
     * @param tabSteuerung die Tabsteuerung
     */
    public StatistikenFormular(TabSteuerung tabSteuerung) {
        gridPane = new GridPane();
        gridPane.setVgap(8);
        anzRacksLabel.setText("Belegte Racks: " + LagerVerwaltung.getInstance().getAnzBelegterRacks());
        gesamteAnzahlProben.setText("Gesamte Anzahl Proben im System: " + ProbenVerwaltung.getInstance().getProbenSet().size());
        maxRacksLabel.setText("Maximale Anzahl Racks in allen Kühlschränken: " + LagerVerwaltung.getInstance().getAnzMaxRacks());
        
        if (LagerVerwaltung.getInstance().getAnzMaxRacks() != 0)
            belgterPlatzProzent = new Text(
                    "Belegter Kühlschrankplatz: " + (int) ((double) LagerVerwaltung.getInstance().getAnzBelegterRacks()
                            / (double) LagerVerwaltung.getInstance().getAnzMaxRacks() * 100) + "%");
        else
            belgterPlatzProzent = new Text("Belegter Kühlschrankplatz: Keine Probenplätze vorhanden");
        // weitere Elemente werden noch erzeugt
        
        gridPane.add(maxRacksLabel, 0, 1);
        gridPane.add(anzRacksLabel, 0, 2);
        gridPane.add(gesamteAnzahlProben, 0, 3);
        gridPane.add(belgterPlatzProzent, 0, 4);
        Button b = new Button("                        ");
        b.setOpacity(0);
        b.setOnAction(event -> {

            gridPane.add(t, 0, 5);
        });

        if (LagerVerwaltung.getInstance().getAnzMaxRacks() == 0) {
            pieChart.setVisible(false);
        } else {
            pieChart.setVisible(true);
        }
        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        gridPane.add(b, 0, 5);
        gridPane.add(pieChart, 0, 6);
    }

    @Override
    public void onOpen() {
        anzRacksLabel.setText("Belegte Racks: " + LagerVerwaltung.getInstance().getAnzBelegterRacks());
        gesamteAnzahlProben.setText("Gesamte Anzahl Proben im System: " + ProbenVerwaltung.getInstance().getProbenSet().size());
        maxRacksLabel.setText("Maximale Anzahl Racks in allen Kühlschränken: " + LagerVerwaltung.getInstance().getAnzMaxRacks());
        if (LagerVerwaltung.getInstance().getAnzMaxRacks() != 0)
            belgterPlatzProzent = new Text(
                    "Belegter Probenplatz: " + (int) ((double) LagerVerwaltung.getInstance().getAnzBelegterRacks()
                            / (double) LagerVerwaltung.getInstance().getAnzMaxRacks() * 100) + "%");
        else
            belgterPlatzProzent = new Text("Belegter Kühlschrankplatz: Keine Kühlschrankplatz vorhanden");
        
        if (LagerVerwaltung.getInstance().getAnzMaxRacks() == 0) {
            pieChart.setVisible(false);
        } else {
            pieChart.setVisible(true);
        }
        slice1.setPieValue(LagerVerwaltung.getInstance().getAnzBelegterRacks());
        slice2.setPieValue(LagerVerwaltung.getInstance().getAnzMaxRacks()
                - LagerVerwaltung.getInstance().getAnzBelegterRacks());

        bananen = random.nextInt();
        t.setText("Bananen Pro Mitarbeiter: " + bananen);

    }

    /**
     * Gibt die Hautpnode des Fensters zurueck
     */
    @Override
    public Node getNode() {

        return gridPane;
    }

    /**
     * Gibt den Namen wieder, der in den Breadcrumbs angezeigt wird.
     */
    @Override
    public String getName() {

        return "Globale Statistiken";
    }
}
