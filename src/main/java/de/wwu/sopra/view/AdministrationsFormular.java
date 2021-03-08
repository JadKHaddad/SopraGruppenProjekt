/**
 *
 */
package de.wwu.sopra.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Darstellung des Administrationsformulars
 * @author Gruppe 5
 *
 */
public class AdministrationsFormular extends View {

    private GridPane gridPane;
    /**
     * Erstellt ein neues AdministrationsFormular
     * @param tabSteuerung die Tabsteuerung
     */
    public AdministrationsFormular(TabSteuerung tabSteuerung) {
        gridPane = new GridPane();
        
        VBox vbox1 = new VBox();
        VBox vbox2 = new VBox();
         
        gridPane.setHgap(32);
        gridPane.setVgap(6);

        Button button = new Button("Neuen Behältertyp anlegen        ", new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        button.setTooltip(new Tooltip("Neuen Behältertyp hinzufügen"));
        
       
        
        button.setOnAction(event -> {
            BehaeltertypAnlegenFormular behaeltertypAnlegenFormular = new BehaeltertypAnlegenFormular(tabSteuerung);
            tabSteuerung.push(behaeltertypAnlegenFormular);
        });
        Button button1 = new Button("Neue Probenkategorie anlegen", new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        button1.setTooltip(new Tooltip("Neue Probenkategorie anlegen"));
        button1.setOnAction(event -> {
            ProbenkategorieAnlegenFormular probenkategorieAnlegenFormular = new ProbenkategorieAnlegenFormular(tabSteuerung);
            tabSteuerung.push(probenkategorieAnlegenFormular);
        });
        Button messtypButton = new Button("Neuen Messtyp anlegen   ", new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        messtypButton.setTooltip(new Tooltip ("Neuen Messtyp anlegen"));
        messtypButton.setOnAction(event -> {
            MesstypAnlegenFormular messtypAnlegenFormular = new MesstypAnlegenFormular(tabSteuerung);
            tabSteuerung.push(messtypAnlegenFormular);
        });
        
        Button deckeltypButton = new Button("Neuen Deckeltyp anlegen", new ImageView(new Image(getClass().getResourceAsStream("/add.png"))));
        deckeltypButton.setTooltip(new Tooltip("Neuen Deckeltyp anlegen"));
        deckeltypButton.setOnAction(event -> {
            DeckeltypAnlegenFormular deckeltypAnlegenFormular = new DeckeltypAnlegenFormular(tabSteuerung);
            tabSteuerung.push(deckeltypAnlegenFormular);
        });
        
        vbox1.setSpacing(8);
        vbox2.setSpacing(8);
        
        vbox1.getChildren().add(button);
        vbox1.getChildren().add(button1);
        
        vbox2.getChildren().add(messtypButton);
        vbox2.getChildren().add(deckeltypButton);
        
        gridPane.add(vbox1, 0, 0);
        gridPane.add(vbox2, 1, 0);
        

        gridPane.setVgap(8);


    }

    /**
     * Gibt die Node zurueck
     */
    @Override
    public Node getNode() {
        return gridPane;
    }
    
    /**
     * Gibt den Namen des Formulars zurueck
     */
    @Override
    public String getName() {
        return "Administration";
    }


}
