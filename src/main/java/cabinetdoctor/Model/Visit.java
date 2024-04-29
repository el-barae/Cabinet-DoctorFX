package cabinetdoctor.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Visit {
    private final IntegerProperty id;
    private final StringProperty symptoms;
    private final StringProperty diagnostics;
    private final StringProperty note;
    private final ObjectProperty<LocalDate> deh;  // Changed to LocalDate for date handling
    private final StringProperty type;
    private final IntegerProperty montant;
    private final StringProperty cin;

    public Visit() {
        this.id = new SimpleIntegerProperty(this, "id");
        this.symptoms = new SimpleStringProperty(this, "symptoms");
        this.diagnostics = new SimpleStringProperty(this, "diagnostics");
        this.note = new SimpleStringProperty(this, "note");
        this.deh = new SimpleObjectProperty<>(this, "deh");
        this.type = new SimpleStringProperty(this, "type");
        this.montant = new SimpleIntegerProperty(this, "montant");
        this.cin = new SimpleStringProperty(this, "cin");
    }

    public Visit(int id, String symptoms, String diagnostics, String note, LocalDate deh, String type, int montant, String cin) {
        this();
        setId(id);
        setSymptoms(symptoms);
        setDiagnostics(diagnostics);
        setNote(note);
        setDeh(deh);
        setType(type);
        setMontant(montant);
        setCin(cin);
    }

    public Visit(String symptoms, String diagnostics, String note, LocalDate deh, String type, int montant, String cin) {
        this();
        setSymptoms(symptoms);
        setDiagnostics(diagnostics);
        setNote(note);
        setDeh(deh);
        setType(type);
        setMontant(montant);
        setCin(cin);
    }

    public String toString() {
        return "Visit{" +
                "id=" + id.get() +
                ", symptoms='" + symptoms.get() + '\'' +
                ", diagnostics='" + diagnostics.get() + '\'' +
                ", note='" + note.get() + '\'' +
                ", deh=" + deh.get() +
                ", type='" + type.get() + '\'' +
                ", montant=" + montant.get() +
                ", cin='" + cin.get() + '\'' +
                '}';
    }

    // Property Getters
    public StringProperty idProperty() {
        return new SimpleStringProperty(String.valueOf(getId()));
    }
    public StringProperty symptomsProperty() {
        return symptoms;
    }
    public StringProperty diagnosticsProperty() {
        return diagnostics;
    }
    public StringProperty noteProperty() {
        return note;
    }
    public StringProperty dehProperty() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = (deh != null && deh.get() != null) ? formatter.format(deh.get()) : "";
        return new SimpleStringProperty(formattedDate);
    }
    public StringProperty typeProperty() {
        return type;
    }
    public StringProperty montantProperty() {
        return new SimpleStringProperty(String.valueOf(getMontant()));
    }
    public StringProperty cinProperty() {
        return cin;
    }

    // Getters and Setters
    public int getId() {
        return id.get();
    }
    public void setId(int value) {
        id.set(value);
    }
    public String getSymptoms() {
        return symptoms.get();
    }
    public void setSymptoms(String value) {
        symptoms.set(value);
    }
    public String getDiagnostics() {
        return diagnostics.get();
    }
    public void setDiagnostics(String value) {
        diagnostics.set(value);
    }
    public String getNote() {
        return note.get();
    }
    public void setNote(String value) {
        note.set(value);
    }
    public LocalDate getDeh() {
        return deh.get();
    }
    public void setDeh(LocalDate value) {
        deh.set(value);
    }
    public String getType() {
        return type.get();
    }
    public void setType(String value) {
        type.set(value);
    }
    public int getMontant() {
        return montant.get();
    }
    public void setMontant(int value) {
        montant.set(value);
    }
    public String getCin() {
        return cin.get();
    }
    public void setCin(String value) {
        cin.set(value);
    }
}
