package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.controller.SpeicherSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * Definiert die Tabsteuerung
 * @author Gruppe 5
 */
public class TabSteuerung {

    private Tab tab;

    private Stack<View> nodes = new Stack<>();
    private HBox mainPane;
    private HBox breadcrumbs;

    /**
     * Erzeugt eine TabSteuerung
     * @param tab   Der Tab, der gesteuert werden soll.
     * @param stage die stage, die gerade angezeigt wird
     */
    public TabSteuerung(Tab tab, Stage stage) {
        // Erstelle die Tabsteuerung
        this.tab = tab;
        VBox vbox = new VBox();
        HBox topBox = new HBox();
        breadcrumbs = new HBox();
        breadcrumbs.setSpacing(10);
        topBox.getChildren().add(breadcrumbs);
        vbox.getChildren().add(topBox);
        mainPane = new HBox();
        vbox.getChildren().add(mainPane);
        vbox.setSpacing(16);
        vbox.setPadding(new Insets(16));
        tab.setContent(vbox);

        // Wenn der Tab ausgewählt wird, wird onOpen() auf dem obersten View ausgewählt.
        tab.setOnSelectionChanged(event -> {
            if (tab.isSelected()) {
                nodes.peek().onOpen();
            }
        });

        // Fuege den Logout Button ein
        Button logout = new Button("Logout");
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        topBox.getChildren().add(region);
        topBox.getChildren().add(logout);

        // Dem Logout Button Funktionalitaet zuweisen
        logout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            /**
             * Schließt das Hauptfenster und oeffnet ein neues LoginFormular
             */
            public void handle(ActionEvent event) {
                BenutzerSteuerung bs = BenutzerSteuerung.getInstance();
                bs.logout();
                Stage primaryStage = new Stage();
                LoginFormular formular = new LoginFormular(primaryStage);
                formular.show(primaryStage);
                stage.close();
            }
        });

    }

    /**
     * Fügt einen View zur TabSteuerung hinzu.
     * @param view Der View
     */
    private void pushInternal(View view) {
        // Fuegt den View zum Nodestack hinzu.
        nodes.push(view);
        // Fuegt (falls nicht erster View einen Pfeil und) Namen des View zu den
        // Breadcrumbs hinzu.
        if (breadcrumbs.getChildren().size() > 0) {
            breadcrumbs.getChildren().add(new Label(">"));
        }
        Label l = new Label(view.getName());
        breadcrumbs.getChildren().add(l);
        int stacksize = nodes.size();
        l.setOnMouseClicked(event -> {
            // Wenn auf Namen geklickt wird, werden so viele View aus Stack gepoppt, bis der
            // angeklickte View angezeigt wird.
            if (stacksize < nodes.size()) {
                nodes.peek().onClose();
                while (stacksize < nodes.size()) {
                    popInternal();
                }
                updateTabInhalt();
                nodes.peek().onOpen();
            }
        });
    }

    /**
     * Fügt einen View zur TabSteuerung hinzu und updated danach den angezeigten
     * Tab.
     * @param view Der View
     */
    public void push(View view) {
        pushInternal(view);
        updateTabInhalt();
    }

    /**
     * Entfernt den obersten View der Tabsteuerung
     */
    private void popInternal() {
        // Nehme oberste Node vom Stack
        View view = nodes.pop();
        // Nehme obersten Text vom Breadcrumb
        breadcrumbs.getChildren().remove(breadcrumbs.getChildren().size() - 1);
        // Nehme '>' vom Stack, falls es existiert.
        if (breadcrumbs.getChildren().size() > 0) {
            breadcrumbs.getChildren().remove(breadcrumbs.getChildren().size() - 1);
        }
    }

    /**
     * Entfernt den obersten View der Tabsteuerung und updated danach den
     * angezeigten Tab.
     */
    public void pop() {
        SpeicherSteuerung.getInstance().speichern();
        nodes.peek().onClose();
        popInternal();
        updateTabInhalt();
        nodes.peek().onOpen();
    }

    /**
     * Setzt den Inhalt des Tabs auf den obersten View des Stacks
     */
    public void updateTabInhalt() {
        while (!mainPane.getChildren().isEmpty()) {
            mainPane.getChildren().remove(0);
        }
        mainPane.getChildren().add(nodes.peek().getNode());
    }
}
