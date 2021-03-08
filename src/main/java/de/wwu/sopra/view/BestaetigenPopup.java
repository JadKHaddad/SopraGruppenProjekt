package de.wwu.sopra.view;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Verwaltet die Popuperstellung.
 * @author Gruppe 5
 */
public class BestaetigenPopup {

    /**
     * Erstellt ein Popup
     * @param title Der Titel des Fensters
     * @param message Die Nachricht des Fensters
     * @param okCallback Der Code, der ausgefuehrt wird, sobald das Popup mit dem OK-Button geschlossen wird.
     * @param abbrechenCallback Der Code, der ausgefuehrt wird, sobald das Popup mit dem Abbrechen-Button geschlossen wird.
     */
    public BestaetigenPopup(String title, String message, Runnable okCallback, Runnable abbrechenCallback) {
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK, ButtonType.CANCEL);
        DialogPane root = alert.getDialogPane();

        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.getIcons().add(new Image("/kuchen.png"));

            ButtonBase buttonOk = (ButtonBase) root.lookupButton(ButtonType.OK);
            buttonOk.setOnAction(evt -> {
                dialogStage.close();
                if (okCallback != null) {
                    okCallback.run();
                }
            });
            
            ButtonBase buttonCancel = (ButtonBase) root.lookupButton(ButtonType.CANCEL);
            buttonCancel.setOnAction(evt -> {
                dialogStage.close();
                if (abbrechenCallback != null) {
                    abbrechenCallback.run();
                }
            });
        

        // replace old scene root with placeholder to allow using root in other Scene
        root.getScene().setRoot(new Group());

        root.setPadding(new Insets(10, 0, 10, 0));
        Scene scene = new Scene(root);

        dialogStage.setScene(scene);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setAlwaysOnTop(true);
        dialogStage.setResizable(false);
        dialogStage.setTitle(title);
        dialogStage.show();
    }

}
