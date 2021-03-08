
package de.wwu.sopra.controller;

import com.thoughtworks.xstream.XStream;
import de.wwu.sopra.controller.data.*;
import de.wwu.sopra.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Verwaltung der Speicherung, sowie der Erstellung eines Default Benutzers
 * @author Gruppe 5
 */
public class SpeicherSteuerung {

    private static SpeicherSteuerung instance;
    private final boolean debugModus = false;


    /**
     * @return Die Instanz der Speicherverwaltung.
     */
    public static SpeicherSteuerung getInstance() {
        if (instance == null) {
            instance = new SpeicherSteuerung();
        }
        return instance;
    }

    private File file = new File("save.crypt");
    private XStream xs;
    private String password;
    private final String CHECK_ENCRYTPION = "Das hier überprüft alles!";

    /**
     * Setzung der Klassenvariablen
     */
    private SpeicherSteuerung() {
        xs = new XStream();
        xs.autodetectAnnotations(true);

        xs.allowTypesByWildcard(new String[]{"de.wwu.sopra.**"});
    }

    public boolean speicherDateiExistiert() {
        return file.exists();
    }
    
    public void setFile(File f) {
        this.file = f;
    }

    /**
     * Laed gespeicherte Dateien.
     */
    public boolean laden() {
        if (file.exists()) {
            try {
                byte[] verschluesselteDaten = new FileInputStream(file).readAllBytes();
                String entschluesselteDaten = entschluesseln(verschluesselteDaten, password);
                if(entschluesselteDaten == null || !entschluesselteDaten.startsWith(CHECK_ENCRYTPION)) {
                    return false;
                }
                entschluesselteDaten = entschluesselteDaten.substring(CHECK_ENCRYTPION.length());
                Datenhaltung daten = (Datenhaltung) xs.fromXML(entschluesselteDaten);
                BehaelterVerwaltung.getInstance().setBehaeltertypSet(daten.behaeltertypenSet);
                BenutzerVerwaltung.getInstance().setBenutzerSet(daten.benutzerSet);
                LagerVerwaltung.getInstance().setRaumSet(daten.raumSet);
                PatientenVerwaltung.getInstance().setPatientenSet(daten.patientenSet);
                ProbenKategorieVerwaltung.getInstance().setProbenKategorieSet(daten.probenKategorienSet);
                ProbenVerwaltung.getInstance().setProbenSet(daten.probenSet);
                StudienVerwaltung.getInstance().setStudienSet(daten.studienSet);
                BidVerwaltung.getInstance().setCounter(daten.counter);
                MesstypVerwaltung.getInstance().setMesstypen(daten.messtypList);
                DeckeltypVerwaltung.getInstance().setDeckeltypen(daten.deckeltypList);
                ProbenSteuerung.getInstance().setPersistenzDaten(daten.probenSteuerungPersistenz);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            defaultDaten();
        }
        return true;
    }

    /**
     * Erstellt Defaultdaten beim ersten oeffnen der Anwendung.
     */
    public void defaultDaten() {
        Benutzer b = BenutzerSteuerung.getInstance().erstelleBenutzer("admin", "passwort", "admin", "admin");
        BenutzerVerwaltung.getInstance().addBenutzer(b);
        b.addRolle(Rolle.MTA);
        b.addRolle(Rolle.STUDY_NURSE);
        b.addRolle(Rolle.BIOBANKLEITER);
        b.addRolle(Rolle.PERSONALLEITER);
        
        
        if(debugModus) {
            ProbenKategorieVerwaltung.getInstance().addProbenKategorie(new ProbenKategorie("Blut", "ml", 7, 4));
            ProbenKategorieVerwaltung.getInstance().addProbenKategorie(new ProbenKategorie("Plasma", "ml", 7, 4));
            ProbenKategorieVerwaltung.getInstance().addProbenKategorie(new ProbenKategorie("Haare", "Stück", 40, 0));
            ProbenKategorieVerwaltung.getInstance().addProbenKategorie(new ProbenKategorie("Knochenmark", "g", 10, 2));
            ProbenKategorieVerwaltung.getInstance().addProbenKategorie(new ProbenKategorie("Speichel", "ml", 10, 2));
            ProbenKategorieVerwaltung.getInstance().addProbenKategorie(new ProbenKategorie("Urin", "ml", 10, 4));
            
            MesstypVerwaltung.getInstance().addMesstyp(new Messtyp("Körpertemperatur", "Grad Celsius"));
            MesstypVerwaltung.getInstance().addMesstyp(new Messtyp("Herzfrequenz", "Schläge pro Minute"));
            MesstypVerwaltung.getInstance().addMesstyp(new Messtyp("Gewicht", "kg"));
            MesstypVerwaltung.getInstance().addMesstyp(new Messtyp("Blutdruck", "mmHg"));
            MesstypVerwaltung.getInstance().addMesstyp(new Messtyp("Gehirnaktivität", "Prozent"));

            Deckeltyp d = new Deckeltyp("D 56");
            DeckeltypVerwaltung.getInstance().addDeckeltyp(d);
            DeckeltypVerwaltung.getInstance().addDeckeltyp(new Deckeltyp("A 108"));
            DeckeltypVerwaltung.getInstance().addDeckeltyp(new Deckeltyp("B 22"));
            DeckeltypVerwaltung.getInstance().addDeckeltyp(new Deckeltyp("B 01"));
            DeckeltypVerwaltung.getInstance().addDeckeltyp(new Deckeltyp("CM 333"));
            
            BehaelterVerwaltung.getInstance().addBehaeltertyp(new Behaeltertyp("Behälter1", 2f, 3f, (float)(2*2*Math.PI*3), d));
            BehaelterVerwaltung.getInstance().addBehaeltertyp(new Behaeltertyp("Behälter2", 3f, 4f, (float)(3*3*Math.PI*4), d));
            BehaelterVerwaltung.getInstance().addBehaeltertyp(new Behaeltertyp("Behälter3", 7f, 4f, (float)(7*7*Math.PI*4), d));

            PatientenVerwaltung.getInstance().addPatient(new Patient("Huchen", "Herbert", 250, "Unten"));
            PatientenVerwaltung.getInstance().addPatient(new Patient("Kleber", "Klaus", 23, "Kleberstraße123"));
            PatientenVerwaltung.getInstance().addPatient(new Patient("Mustermann", "Max", 27, "Musterstraße123"));
            PatientenVerwaltung.getInstance().addPatient(new Patient("Brot", "Berhard", 42, "Brotweg"));
            PatientenVerwaltung.getInstance().addPatient(new Patient("Glup", "Gudrun", 50, "Im Aquarium"));
            PatientenVerwaltung.getInstance().addPatient(new Patient("Jang", "Josefine", 15, "Zu Hause"));
            PatientenVerwaltung.getInstance().addPatient(new Patient("Ernst", "Emil", 30, "In der Uni"));

            Benutzer bb = BenutzerSteuerung.getInstance().erstelleBenutzer("Passwort ist p", "p", "Supernurse",
                "Stefan");
            BenutzerVerwaltung.getInstance().addBenutzer(bb);
            bb.addRolle(Rolle.STUDY_NURSE);

            Benutzer bbb = BenutzerSteuerung.getInstance().erstelleBenutzer("Passwort ist x", "x", "Meganurse",
                "Siegfried");
            BenutzerVerwaltung.getInstance().addBenutzer(bbb);
            bbb.addRolle(Rolle.STUDY_NURSE);
        }

    }

    /**
     * Speichert Dateien ab.
     */
    public void speichern() {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            Datenhaltung daten = new Datenhaltung();
            daten.behaeltertypenSet = BehaelterVerwaltung.getInstance().getBehaeltertypSet();
            daten.benutzerSet = BenutzerVerwaltung.getInstance().getBenutzerSet();
            daten.raumSet = LagerVerwaltung.getInstance().getRaumSet();
            daten.patientenSet = PatientenVerwaltung.getInstance().getPatientenSet();
            daten.probenKategorienSet = ProbenKategorieVerwaltung.getInstance().getProbenKategorieSet();
            daten.probenSet = ProbenVerwaltung.getInstance().getProbenSet();
            daten.studienSet = StudienVerwaltung.getInstance().getStudienSet();
            daten.counter = BidVerwaltung.getInstance().getCounter();
            daten.messtypList = MesstypVerwaltung.getInstance().getMesstypen();
            daten.deckeltypList = DeckeltypVerwaltung.getInstance().getDeckeltypen();
            daten.probenSteuerungPersistenz = ProbenSteuerung.getInstance().getPersistenzDaten();
            String stringText = xs.toXML(daten);
            fos.write(verschluesseln(CHECK_ENCRYTPION + stringText, password));
            fos.flush();
            fos.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String entschluesseln(byte[] data, String password) {
        try {
            Cipher cipher2 = Cipher.getInstance("AES");
            cipher2.init(Cipher.DECRYPT_MODE, erzeugeSchluessel(password));
            byte[] cipherData2 = cipher2.doFinal(data);
            return new String(cipherData2);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            return null;
        }
    }

    public byte[] verschluesseln(String klartext, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, erzeugeSchluessel(password));
            return cipher.doFinal(klartext.getBytes());
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SecretKeySpec erzeugeSchluessel(String keyStr) {
        try {
            byte[] key = (keyStr).getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Klasse, um alle zu speichernden Daten in ein Objekt zu kapseln.
     */
    private static class Datenhaltung {
        public Set<Behaeltertyp> behaeltertypenSet;
        public Set<Benutzer> benutzerSet;
        public Set<Raum> raumSet;
        public Set<Patient> patientenSet;
        public Set<ProbenKategorie> probenKategorienSet;
        public Set<Probe> probenSet;
        public Set<Studie> studienSet;
        public int counter;
        public ArrayList<Messtyp> messtypList;
        public ArrayList<Deckeltyp> deckeltypList;
        public ProbenSteuerung.ProbenSteuerungPersistenz probenSteuerungPersistenz;
    }

}