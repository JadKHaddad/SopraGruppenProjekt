/**
 * @author Gruppe 5
 */
package de.wwu.sopra.controller;

import de.wwu.sopra.controller.data.BenutzerVerwaltung;
import de.wwu.sopra.model.Benutzer;
import de.wwu.sopra.view.InfoPopup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

/**
 *Klasse zur Verwaltung von Benutzern, deren Login, sowie der sicheren Passwort Generierung
 */
public class BenutzerSteuerung {

    private static BenutzerSteuerung instance;

    private BenutzerSteuerung() {

    }

    /**
     * BenutzerSteuerung als Singleton um sicherzustellen, dass nur eine
     * BenutzerSteuerung mit korrektem, synchronisiertem Inhalt existiert
     * 
     * @return die BenutzerSteuerung instance
     */
    public static synchronized BenutzerSteuerung getInstance() {
        if (instance == null) {
            instance = new BenutzerSteuerung();
        }
        return instance;
    }

    private Benutzer aktiverBenutzer = null;

    /**
     * logt einen Benutzer ein
     * 
     * @param benutzername gibt den benutzernamen ein
     * @param passwort     gibt das Passwort ein
     * @return true falls ein Benutzer gefunden wurde mit dem selben benutzernamen
     *         und passwort und setzt den aktiven Benutzer auf ihn
     */
    public boolean login(String benutzername, String passwort) {
        for (Benutzer benutzer : BenutzerVerwaltung.getInstance().getBenutzerSet()) {
            if (benutzer.getBenutzerName().equals(benutzername)) {
                if (validatePassword(passwort, benutzer.getPassword())) {
                    aktiverBenutzer = benutzer;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Gibt zurueck, ob ein Benutzername bereits verwendet wird.
     * 
     * @param benutzername   Der zu ueberpruefende Benutzername.
     * @param ausserBenutzer Zu ignorierender Benutzer, um bei gleichgebliebenen
     *                       Namen trotzdem true zurueck zu geben. Darf null sein.
     * @return ob der Benutzername von jemandem ausser ausserBenutzer verwendet
     *         wird.
     */
    public boolean istBenutzernameVerwendet(String benutzername, Benutzer ausserBenutzer) {
        BenutzerVerwaltung verwaltung = BenutzerVerwaltung.getInstance();
        for(Benutzer benutzer: verwaltung.getBenutzerSet()) {
            if (ausserBenutzer == null || benutzer != ausserBenutzer) {
                if (benutzer.getBenutzerName().equals(benutzername)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Erstellt einen Benutzer mit gehashtem Passwort
     * @param benutzername benutzername des Benutzers
     * @param passwort     ungehashtes Passwort des Benutzers
     * @param name         Nachname des Benutzers
     * @param vorname      vorname des Benutzers
     * @return Benutzer mit gehashtem Passwort
     */
    public Benutzer erstelleBenutzer(String benutzername, String passwort, String name, String vorname) {
        String gehashtesPasswort = generateStrongPasswordHash(passwort);
        return new Benutzer(benutzername, gehashtesPasswort, name, vorname);
    }
  

    /**
     * Ueberladene Methode der Benutzererstellung, um automatische Passwortgenerierung zu garantieren.
     * @param benutzername Der Benutzername des Benutzers
     * @param name Der Name des Benutzers
     * @param vorname Der Vorname des Benutzers
     * @return Der fertige Benutzer
     */
    public Benutzer erstelleBenutzer(String benutzername, String name, String vorname) {
        String passwort = genPasswort();
        return erstelleBenutzer(benutzername, passwort, name, vorname);
    }

    /**
     * Generierung eines zufaelligen 8-stelligen Passworts bestehend aus Zahlen
     * @return Das  generierte Passwort
     */
    public String genPasswort() {
        String passwort = "";
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            passwort += rand.nextInt(10);
        }
        return passwort;

    }
    
    /**
     * Logt einen aktiven Benutzer aus
     */
    public void logout() {
        aktiverBenutzer = null;
    }

    /**
     * Aendert das Passwort eines Benutzers
     * 
     * @param oldPassword     das alte passwort welches geaendert werden soll
     *                        (Sicherheitsmassnahme)
     * @param newPasswort     das neue Passwort des Benutzers
     * @param newPasswortTest Sicherheitsmassnahme damit das richtige neue Passwort
     *                        eingegeben wird
     */
    public void passwortAendern(String oldPassword, String newPasswort, String newPasswortTest) {
        if (validatePassword(oldPassword, aktiverBenutzer.getPassword()) && newPasswort.equals(newPasswortTest)) {
            String gehashtesNewPasswort = generateStrongPasswordHash(newPasswort);
            aktiverBenutzer.setPassword(gehashtesNewPasswort);
        }
    }


    /**
     * Aendert das Passwort eines Benutzers
     * @param benutzer der Benutzer fuer den das Passwort geaendert werden soll
     * @param newPasswort     das neue Passwort des Benutzers
     */
    public void passwortAendern(Benutzer benutzer, String newPasswort) {
        
            String gehashtesNewPasswort = generateStrongPasswordHash(newPasswort);
            benutzer.setPassword(gehashtesNewPasswort);
        
    }
    
    /**
     * Methode zur hashing von Passwoertern
     * 
     * @param password zu hashendes Passwort
     * @return gehashtes Passwort
     */
    private static String generateStrongPasswordHash(String password) {

        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory secretkeyfactory = null;
        try {
            secretkeyfactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hash = new byte[0];
        try {
            hash = secretkeyfactory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * generiert ein zufaelliges Byte-Array
     * 
     * @return ein zufaelliges Byte-Array
     */
    private static byte[] getSalt() {
        SecureRandom securerandom = null;
        try {
            securerandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        byte[] salt = new byte[16];
        securerandom.nextBytes(salt);
        return salt;
    }

    /**
     * konvertiert ein Bytearray zu eine zweistellige Hexadezimalzahl
     * 
     * @param array der Bytearray der veraendert werden soll
     * @return den String als zweistellige Hexadezimalzahl
     */
    private static String toHex(byte[] array) {

        BigInteger biginteger = new BigInteger(1, array);
        String hex = biginteger.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }

    }

    /**
     * testet ob ein gehashtes Passwort mit einem anderen Passwort uebereinstimmt
     * 
     * @param originalPassword das zu testende Passwort
     * @param storedPassword   das im Benutzer gespeicherte Passwort
     * @return true, falls originalPassword == storedPassword
     */
    private static boolean validatePassword(String originalPassword, String storedPassword) {

        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory secretkeyfactory = null;
        try {
            secretkeyfactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
        byte[] testHash = new byte[0];
        try {
            testHash = secretkeyfactory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException exception) {
            exception.printStackTrace();
        }

        int difference = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            difference |= hash[i] ^ testHash[i];
        }
        return difference == 0;

    }

    /**
     * konvertiert eine Hexadezimalzahl in ein Bytearray
     * 
     * @param hex die Hexadezimalzahl als String
     * @return die Hexadezimalzahl als Bytearray
     */
    private static byte[] fromHex(String hex) {

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    /**
     * Gibt den aktiven Benutzer zurueck
     * @return Den aktiven Benutzer
     */
    public Benutzer getAktiverBenutzer() {
        return aktiverBenutzer;
    }
}
