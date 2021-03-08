package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Verwaltet die Ansicht des Loginformulars
 * @author Gruppe 5
 */
public class LoginFormular {

    private Scene scene;

    /**
     * Erstellt das LoginFormular
     * @param primaryStage die Scene, die man zu Beginn sieht
     */
    public LoginFormular(Stage primaryStage) {
        primaryStage.setResizable(false);
        GridPane grid = new GridPane();
        grid.setHgap(4);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Komponenten erzeugen
        TextField benutzerName = new TextField();
        PasswordField passwort = new PasswordField();
        Text benutzerNameText = new Text("Benutzername ");
        Text passwortText = new Text("Passwort ");
        Button login = new Button("Login");
        Text fehlermeldung = new Text(" ");
        fehlermeldung.setFill(Color.CRIMSON);



        // den Komponenten Koordinaten zuweisen
        GridPane.setConstraints(benutzerNameText, 0, 0);
        GridPane.setConstraints(benutzerName, 2, 0);
        GridPane.setConstraints(passwortText, 0, 2);
        GridPane.setConstraints(passwort, 2, 2);
        GridPane.setConstraints(fehlermeldung, 0, 4, 10, 1, null, null);
        GridPane.setConstraints(login, 0, 6);

        // die Komponenten hinzufuegen
        grid.getChildren().add(benutzerNameText);
        grid.getChildren().add(benutzerName);
        grid.getChildren().add(passwortText);
        grid.getChildren().add(passwort);
        grid.getChildren().add(login);
        grid.getChildren().add(fehlermeldung);

        scene = new Scene(grid, 400, 200);

        // Funktionalitaet auf den LoginButton setzen, bei Korrektem Passwort wird die
        // Hauptanwendung geoeffnet
        login.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

                String eingabeBenutzerName = benutzerName.getText();
                String eingabePasswort = passwort.getText();
                BenutzerSteuerung bs = BenutzerSteuerung.getInstance();
                if (eingabeBenutzerName.isEmpty() && eingabePasswort.isEmpty()) {
                    fehlermeldung.setText("Bitte Benutzernamen und Passwort eingeben.");
                    fehlermeldung.setFill(Color.CRIMSON);
                } else if (eingabeBenutzerName.isEmpty()) {
                    fehlermeldung.setText("Bitte Benutzernamen eingeben.");
                    fehlermeldung.setFill(Color.CRIMSON);
                } else if (eingabePasswort.isEmpty()) {
                    fehlermeldung.setText("Bitte Passwort eingeben.");
                    fehlermeldung.setFill(Color.CRIMSON);
                } else if (!(bs.login(eingabeBenutzerName, eingabePasswort))) {
                    fehlermeldung.setText("Passwort oder Benutzername ungültig.");
                    fehlermeldung.setFill(Color.CRIMSON);
                } else {
                    Stage tabStage = new Stage();
                    HauptFormular formular = new HauptFormular(tabStage);
                    formular.show();
                    primaryStage.close();
                }


            }
        });

    }

    /**
     * Methode zum Anzeigen einer Stage
     * @param stage Stage, die man sehen moechte
     */
    public void show(Stage stage) {
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("/kuchen.png"));
        stage.setTitle("Biobankverwaltung - Login");
    }
};