/**
 * @author Gruppe 5
 */
package de.wwu.sopra.application;

import de.wwu.sopra.controller.SpeicherSteuerung;
import de.wwu.sopra.view.InfoPopup;
import de.wwu.sopra.view.LoginFormular;
import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * Startet die Anwendung
 */
public class App extends Application {

    private File lockDatei = new File(".lock");
    private boolean startVerhindert = false;
    private boolean speichereAb = false;

    /**
     * @param primaryStage die erste Stage Oeffnet das Login Fenster
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Falls die Lock file schon existiert, wird die Anwendung direkt wieder
        // beendet.
        if (lockDatei.exists()) {
            startVerhindert = true;
            InfoPopup popup = new InfoPopup("Fehler",
                    "Die Anwendung ist bereits gestartet. " + "Falls Sie sicher sind, dass die Anwendung nicht läuft, "
                            + "" + "löschen Sie bitte die .lock Datei im Verzeichnis des Programmes.",
                    primaryStage::close);
        } else {
            lockDatei.createNewFile();
            do {
                String password = zeigePasswortFenster();
                if (password == null) {
                    stop();
                    return;
                }
                SpeicherSteuerung.getInstance().setPassword(password);
            } while (!SpeicherSteuerung.getInstance().laden());
            speichereAb = true;
            LoginFormular formular = new LoginFormular(primaryStage);
            formular.show(primaryStage);
        }
    }

    public String zeigePasswortFenster() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Entschlüsseln");
        ImageView imageView = new ImageView("/kuchen.png");
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        dialog.setGraphic(imageView);

        if (SpeicherSteuerung.getInstance().speicherDateiExistiert()) {
            dialog.setHeaderText("Bitte geben Sie den Schlüssel ein, um die Speicherdatei zu entschlüsseln.");
        } else {
            dialog.setHeaderText(
                    "Bitte geben Sie einen Schlüssel ein, mit dem die Speicherdatei verschlüsselt werden soll.");
        }
        dialog.setContentText("Schlüssel:");

        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/kuchen.png"));
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);

    }

    /**
     * Uberschreibt die Speicherdatei beim beenden des Programms
     */
    @Override
    public void stop() throws Exception {
        if (!startVerhindert) {
            if (speichereAb) {
                SpeicherSteuerung.getInstance().speichern();
            }
            lockDatei.delete();
        }
    }
}
