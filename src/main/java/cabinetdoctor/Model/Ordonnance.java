package cabinetdoctor.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

public class Ordonnance {
    private final IntegerProperty id;
    private final IntegerProperty idV;
    private final StringProperty medicament;
    private final StringProperty test;
    private final StringProperty note;

    public Ordonnance(int idV, String medicament, String test, String note) {
        this.id = new SimpleIntegerProperty(this, "id");
        this.idV = new SimpleIntegerProperty(this, "idV");
        this.medicament = new SimpleStringProperty(this, "medicament");
        this.test = new SimpleStringProperty(this, "test");
        this.note = new SimpleStringProperty(this, "note");
    }

    public Ordonnance(int id, int idV, String medicament, String test, String note) {
        this.id = new SimpleIntegerProperty(this, "id");
        this.idV = new SimpleIntegerProperty(this, "idV");
        this.medicament = new SimpleStringProperty(this, "medicament");
        this.test = new SimpleStringProperty(this, "test");
        this.note = new SimpleStringProperty(this, "note");
        setId(id);
        setIdV(idV);
        setMedicament(String.valueOf(medicament));
        setTest(String.valueOf(test));
        setNote(String.valueOf(note));
    }

    public Ordonnance() {
        this.id = new SimpleIntegerProperty(this, "id");
        this.idV = new SimpleIntegerProperty(this, "idV");
        this.medicament = new SimpleStringProperty(this, "medicament");
        this.test = new SimpleStringProperty(this, "test");
        this.note = new SimpleStringProperty(this, "note");
    }

    public Ordonnance(int id, int idVisit, TextField medicament, TextField test, TextField note) {
        this.id = new SimpleIntegerProperty(this, "id");
        this.idV = new SimpleIntegerProperty(this, "idV");
        this.medicament = new SimpleStringProperty(this, "medicament");
        this.test = new SimpleStringProperty(this, "test");
        this.note = new SimpleStringProperty(this, "note");

        setId(id);
        setIdV(idVisit);
        setMedicament(String.valueOf(medicament));
        setTest(String.valueOf(test));
        setNote(String.valueOf(note));
    }

    // Getters and setters
    public int getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return new SimpleStringProperty(String.valueOf(getId()));
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getIdV() {
        return idV.get();
    }
    public String getSIdV() {
        return idV.get()+"";
    }

    public StringProperty idVProperty() {
        return new SimpleStringProperty(String.valueOf(getIdV()));
    }

    public void setIdV(int idV) {
        this.idV.set(idV);
    }

    public String getMedicament() {
        return medicament.get();
    }

    public StringProperty medicamentProperty() {
        return medicament;
    }

    public void setMedicament(String medicament) {
        this.medicament.set(medicament);
    }

    public String getTest() {
        return test.get();
    }

    public StringProperty testProperty() {
        return test;
    }

    public void setTest(String test) {
        this.test.set(test);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }
}
