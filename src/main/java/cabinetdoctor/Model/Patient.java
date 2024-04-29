package cabinetdoctor.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patient {
    private final StringProperty cin;
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty sexe;
    private final StringProperty ddn;
    private final StringProperty tele;

    public Patient(String cin, String nom, String prenom, String sexe, String ddn, String tele) {
        this.cin = new SimpleStringProperty(this, "cin");
        this.nom = new SimpleStringProperty(this, "nom");
        this.prenom = new SimpleStringProperty(this, "prenom");
        this.sexe = new SimpleStringProperty(this, "sexe");
        this.ddn = new SimpleStringProperty(this, "ddn");
        this.tele = new SimpleStringProperty(this, "tele");

        setCin(cin);
        setNom(nom);
        setPrenom(prenom);
        setSexe(sexe);
        setDdn(ddn);
        setTele(tele);
    }

    public Patient() {
        this.cin = new SimpleStringProperty(this, "cin");
        this.nom = new SimpleStringProperty(this, "nom");
        this.prenom = new SimpleStringProperty(this, "prenom");
        this.sexe = new SimpleStringProperty(this, "sexe");
        this.ddn = new SimpleStringProperty(this, "ddn");
        this.tele = new SimpleStringProperty(this, "tele");
    }

    public StringProperty cinProperty() {
        return cin;
    }

    public String getCin() {
        return cin.get();
    }

    public void setCin(String newCin) {
        cin.set(newCin);
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String newNom) {
        nom.set(newNom);
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String newPrenom) {
        prenom.set(newPrenom);
    }

    public StringProperty sexeProperty() {
        return sexe;
    }

    public String getSexe() {
        return sexe.get();
    }

    public void setSexe(String newSexe) {
        sexe.set(newSexe);
    }

    public StringProperty ddnProperty() {
        return ddn;
    }

    public String getDdn() {
        return ddn.get();
    }

    public void setDdn(String newDdn) {
        ddn.set(newDdn);
    }

    public StringProperty teleProperty() {
        return tele;
    }

    public String getTele() {
        return tele.get();
    }

    public void setTele(String newTele) {
        tele.set(newTele);
    }
}
