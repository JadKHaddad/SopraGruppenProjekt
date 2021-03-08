package de.wwu.sopra.view;

import de.wwu.sopra.controller.BenutzerSteuerung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.model.Rolle;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * Verwaltet das Hauptfenster und erstellt einzelne Tabs
 * @author gruppe 5
 */
public class HauptFormular {

    private Scene scene;
    private Stage stage;

    /**
     * Erzeugt das Hauptfenster
     * @param stage die Stage auf der sich das Hauptformular befindet
     */
    public HauptFormular(Stage stage){
        this.stage  = stage;
        TabPane tabPane = new TabPane();
        Benutzer benutzer = BenutzerSteuerung.getInstance().getAktiverBenutzer();
        if (benutzer.hasRolle(Rolle.BIOBANKLEITER) || benutzer.hasRolle(Rolle.MTA)) {
            // Probenbestandtab erstellen
            Tab probenbestandTab = new Tab("Probenbestand");
            TabSteuerung probenbestandTabSteuerung = new TabSteuerung(probenbestandTab, stage);
            ProbenbestandRaumFormular raumForm = new ProbenbestandRaumFormular(probenbestandTabSteuerung);
            probenbestandTabSteuerung.push(raumForm);
            probenbestandTab.setClosable(false);
            tabPane.getTabs().add(probenbestandTab);
        }

        if (benutzer.hasRolle(Rolle.BIOBANKLEITER) || benutzer.hasRolle(Rolle.STUDY_NURSE)) {
            // Studientab erstellen
            Tab studienTab = new Tab("Studien");
            TabSteuerung studienTabSteuerung = new TabSteuerung(studienTab, stage);
            StudienFormular studienForm = new StudienFormular(studienTabSteuerung);
            studienTabSteuerung.push(studienForm);
            studienTab.setClosable(false);
            tabPane.getTabs().add(studienTab);
        }

        if (benutzer.hasRolle(Rolle.PERSONALLEITER)) {
            // Personalverwaltungtab erstellen
            Tab personalverwaltungTab = new Tab("Personalverwaltung");
            TabSteuerung personalverwaltungTabSteuerung = new TabSteuerung(personalverwaltungTab, stage);
            PersonalverwaltungFormular personalForm = new PersonalverwaltungFormular(personalverwaltungTabSteuerung);
            personalverwaltungTabSteuerung.push(personalForm);
            personalverwaltungTab.setClosable(false);
            tabPane.getTabs().add(personalverwaltungTab);
        }

        if (benutzer.hasRolle(Rolle.BIOBANKLEITER)) {
            // Administrationtab erstellen
            Tab administrationsTab = new Tab("Administration");
            TabSteuerung administrationsTabSteuerung = new TabSteuerung(administrationsTab, stage);
            AdministrationsFormular adminForm = new AdministrationsFormular(administrationsTabSteuerung);
            administrationsTabSteuerung.push(adminForm);
            administrationsTab.setClosable(false);
            tabPane.getTabs().add(administrationsTab);
        }

		// Accounttab erstellen
        Tab accountTab = new Tab("Account");
        TabSteuerung accountTabSteuerung = new TabSteuerung(accountTab, stage);
        AccountFormular accountForm = new AccountFormular(accountTabSteuerung);
        accountTabSteuerung.push(accountForm);
        accountTab.setClosable(false);
        tabPane.getTabs().add(accountTab);

        if (benutzer.hasRolle(Rolle.BIOBANKLEITER)) {
            // Statistikentab erstellen
            Tab statistikenTab = new Tab("Statistiken");
            TabSteuerung statistikenTabSteuerung = new TabSteuerung(statistikenTab, stage);
            StatistikenFormular statistikenForm = new StatistikenFormular(statistikenTabSteuerung);
            statistikenTabSteuerung.push(statistikenForm);
            statistikenTab.setClosable(false);
            tabPane.getTabs().add(statistikenTab);
        }
        
        if(benutzer.hasRolle(Rolle.MTA)) {
            Tab benachrichtigungTab = new Tab("Benachrichtigungen");
            TabSteuerung benachrichtigungTabSteuerung = new TabSteuerung(benachrichtigungTab, stage);
            BenachrichtigungFormular benachrichtigungForm = new BenachrichtigungFormular(benachrichtigungTabSteuerung);
            benachrichtigungTabSteuerung.push(benachrichtigungForm);
            benachrichtigungTab.setClosable(false);
            tabPane.getTabs().add(benachrichtigungTab);
            
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(benachrichtigungTab);
        }
        
        scene = new Scene(tabPane, 720, 640);
    }

    /**
     * Zeigt das HauptFenster an.
     */
    public void show() {
        stage.setScene(scene);
        stage.setTitle("Biobankverwaltung");
        stage.getIcons().add(new Image("/kuchen.png"));
        stage.show();
    }


}
