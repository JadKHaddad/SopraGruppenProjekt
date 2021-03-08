/**
 *
 */
package de.wwu.sopra.model;

import java.time.LocalDateTime;

/**
 * Proben werden definiert
 * @author Gruppe 5
 *
 */
public class Probe {
    private int bid;
    private LocalDateTime entnahmeDatum;
    private ProbenInfo info;
    private ProbenStatus status;
    private PatientenTermin termin;
    private ProbenPlatz probenPlatz;
    private Behaeltertyp behaeltertyp;

    /**
     * Erstellt eine Probe mit Probenplatz.
     *
     * @param bid           Die Barcode ID einer Probe
     * @param entnahmeDatum Das entnahme Datum der Probe
     * @param info          Die Probeninformation
     * @param status        Der Probenstatus
     * @param termin        Der Patiententermin, an dem die Probe angelegt wurde
     * @param probenPlatz   Der ProbenPlatz
     * @param behaeltertyp  Der Typ des Behaelters
     */
    public Probe(int bid, LocalDateTime entnahmeDatum, ProbenInfo info, ProbenStatus status, ProbenPlatz probenPlatz,
            PatientenTermin termin, Behaeltertyp behaeltertyp) {
        this.bid = bid;
        this.entnahmeDatum = entnahmeDatum;
        this.info = info;
        this.status = status;
        setProbenPlatz(probenPlatz);
        setTermin(termin);
        this.behaeltertyp = behaeltertyp;
    }

    /**
     * Gibt die bid einer Probe zurueck.
     *
     * @return bid
     */
    public int getBid() {
        return bid;
    }

    /**
     * Setzt die bid einer Probe.
     *
     * @param bid Die ID einer Probe
     */
    public void setBid(int bid) {
        this.bid = bid;
    }

    /**
     * Gibt das Entnahmedatum einer Probe zurueck.
     *
     * @return entnahmeDatum Das Entnahmedatum einer Probe.
     */
    public LocalDateTime getEntnahmeDatum() {
        return entnahmeDatum;
    }

    /**
     * Setzt das Entnahmedatum einer Probe auf den uebergebenen Wert.
     *
     * @param entnahmeDatum Das Entnahmedatum einer Probe.
     */
    public void setEntnahmeDatum(LocalDateTime entnahmeDatum) {
        this.entnahmeDatum = entnahmeDatum;
    }

    /**
     * Gibt die Probeninfo der Probe zurueck.
     *
     * @return info Die Information einer Probe.
     */
    public ProbenInfo getInfo() {
        return info;
    }

    /**
     * Setzt die Probeninfo der Probe auf den uebergebenen Wert.
     *
     * @param info Die Information einer Probe.
     */
    public void setInfo(ProbenInfo info) {
        this.info = info;
    }

    /**
     * Gibt den Probenstatus einer Probe zurueck.
     *
     * @return status Der aktuelle Status der Probe.
     */
    public ProbenStatus getStatus() {
        return status;
    }

    /**
     * Setzt den Status einer Probe auf den uebergebenen Wert.
     *
     * @param status Der aktuelle Status der Probe.
     */
    public void setStatus(ProbenStatus status) {
        this.status = status;
    }

    /**
     * Gibt den Probenplatz zurueck
     * 
     * @return den Probenplat
     */
    public ProbenPlatz getProbenplatz() {
        return probenPlatz;
    }

    /**
     * Legt einen neues Probenplatz fest
     * @param newProbenPlatz der neue Probenplatz
     */
    public void setProbenPlatz(ProbenPlatz newProbenPlatz) {
        if (this.probenPlatz != newProbenPlatz) {
            ProbenPlatz oldProbenPlatz = this.probenPlatz;
            this.probenPlatz = newProbenPlatz;
            if (oldProbenPlatz != null) {
                oldProbenPlatz.setProbe(null);
            }
            if (newProbenPlatz != null) {
                newProbenPlatz.setProbe(this);
            }
        }
    }

    /**
     * Gibt der Termin zurueck
     * @return den Termin
     */
    public PatientenTermin getTermin() {
        return termin;
    }

    /**
     * Setzt den (Entnahme) Termin
     * 
     * @param termin der zu setztende Termin
     */
    public void setTermin(PatientenTermin termin) {
        if (this.termin != termin) {
            if (this.termin != null && this.termin.hasProbe(this))
                this.termin.removeProbe(this);
            ;
            this.termin = termin;
            if (this.termin != null && !termin.hasProbe(this))
                this.termin.addProbe(this);
        }
    }

    /**
     * Gibt den Behaeltertyp zurueck
     * @return den Behaeltertyp
     */
    public Behaeltertyp getBehaeltertyp() {
        return behaeltertyp;
    }

    /**
     * Setzt den Behaeltertyp der Probe
     * @param behaeltertyp der zu setztende Behaeltertyp
     */
    public void setBehaeltertyp(Behaeltertyp behaeltertyp) {
        this.behaeltertyp = behaeltertyp;
    }
}
