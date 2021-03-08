package de.wwu.sopra.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
/**
 * Definiert einen Benutzer
 * @author Gruppe 5
 */
public class Benutzer {

    private String benutzerName;
    private String password;
    private String name;
    private String vorname;
    private Set<Rolle> rollen = new HashSet<Rolle>();
    private ArrayList<Studie> studien;

    /**
     * Erstellt einen neuen Benutzer mit den uebergebenen Parametern
     * @param benutzerName Name des Benutzers im System
     * @param password     Passwort des Benutzers
     * @param name         Nachname des Benutzers
     * @param vorname      Vorname des Benutzers
     */
    public Benutzer(String benutzerName, String password, String name, String vorname) {
        studien = new ArrayList<Studie>();
        this.benutzerName = benutzerName;
        this.password = password;
        this.name = name;
        this.vorname = vorname;
    }

    /**
     * Fuegt dem Benutzer eine Rolle hinzu
     * @param rolle Rolle, die hinzugefuegt wird
     */
    public void addRolle(Rolle rolle) {
        if (rolle != null) {
            rollen.add(rolle);
        }
    }

    /**
     * Entfernt eine Rolle eines Benutzers
     * @param r Rolle
     */
    public void removeRolle(Rolle r) {
        rollen.remove(r);
    }

    /**
     * Gibt zurueck, ob der Benutzer die Rolle r besitzt.
     * @param r Die Rolle, fuer die geprueft wird, ob der Benutzer sie besitzt.
     * @return ob der Benutzer die Rolle besitzt.
     */
    public boolean hasRolle(Rolle r) {
        return rollen.contains(r);
    }

    /**
     * Gibt die Rollen eines Benutzers zurueck. (Nur eine Kopie, Rollen koennen
     * ueber das Set nicht bearbeitet werden!)
     * @return die Rollen des Benutzers.
     */
    public Set<Rolle> getRollen() {
        return new HashSet<Rolle>(rollen);
    }

    /**
     * Gibt die Studien zurueck
     * @return die Studien
     */
    public ArrayList<Studie> getStudien() {
        return studien;
    }

    /**
     * Fuegt eine neue Studie hinzu
     * @param studie die neue Studie
     */
    public void addStudie(Studie studie) {
        if (!hasStudie(studie)) {
            studien.add(studie);
            if (studie != null)
                studie.addBenutzer(this);
        }
    }

    /**
     * Entfernt eine Studie
     * @param studie die zu entfernende Studie
     */
    public void removeStudie(Studie studie) {
        if (hasStudie(studie)) {
            studien.remove(studie);
            if (studie != null)
                studie.removeBenutzer(this);
        }
    }

    /**
     * Prueft ob ein Studie vorhanden ist
     * @param studie die zu pruefende Studie
     * @return boolean (true, wenn Studie vorhanden)
     */
    public boolean hasStudie(Studie studie) {
        return studien.contains(studie);
    }

    /**
     * Gibt den Benutzernamen zurueck
     * @return benutzerName Der Name des Benutzers im System
     */
    public String getBenutzerName() {
        return benutzerName;
    }

    /**
     * Setzt den Benutzernamen auf den uebergebenen Parameter
     * @param benutzerName Der Name des Benutzers im System
     */
    public void setBenutzerName(String benutzerName) {
        this.benutzerName = benutzerName;
    }

    /**
     * Gibt das gehashte Benutzerpasswort zurueck.
     * @return password Das Passwort des Benutzers
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setzt das gehashte Passwort
     * @param passwort Passwort
     */
    public void setPassword(String passwort) {
        this.password = passwort;
    }

    /**
     * Gibt den Nachnamen des Benutzers zurueck
     * @return name Nachname des Benutzers wird zurueck gegeben
     */

    public String getName() {
        return name;
    }

    /**
     * Setzt den Nachnamen des Benutzers auf den uebergebenen Parameter
     * @param name Nachname des Benutzers
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gibt den Vornamen des Benutzers zurueck
     * @return vorname Vorname des Benutzers
     */
    public String getVorname() {
        return vorname;
    }

    /**
     * Setzt den Vornamen des Benutzer auf den uebergebenen Parameter
     * @param vorname Vorname des Benutzers
     */
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

}
