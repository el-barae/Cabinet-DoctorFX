package cabinetdoctor.Model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RendezVous {
    private final IntegerProperty id;
    private final StringProperty note;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty heure;
    private final StringProperty cinP;

    public RendezVous() {
        this.id = new SimpleIntegerProperty(this, "id");
        this.note = new SimpleStringProperty(this, "note");
        this.date = new SimpleObjectProperty<>(this, "date");
        this.heure = new SimpleStringProperty(this, "heure");
        this.cinP = new SimpleStringProperty(this, "cinP");
    }

    public RendezVous(int id, String note, LocalDate date, String heure, String cinP) {
        this.id = new SimpleIntegerProperty(this, "id");
        this.note = new SimpleStringProperty(this, "note");
        this.date = new SimpleObjectProperty<>(this, "date");
        this.heure = new SimpleStringProperty(this, "heure");
        this.cinP = new SimpleStringProperty(this, "cinP");

        setId(id);
        setNote(note);
        setDate(date);
        setHeure(heure);
        setCinP(cinP);
    }

    public RendezVous( String note, LocalDate date, String heure, String cinP) {
        this.id = new SimpleIntegerProperty(this, "id");
        this.note = new SimpleStringProperty(this, "note");
        this.date = new SimpleObjectProperty<>(this, "date");
        this.heure = new SimpleStringProperty(this, "heure");
        this.cinP = new SimpleStringProperty(this, "cinP");
        setNote(note);
        setDate(date);
        setHeure(heure);
        setCinP(cinP);
    }

    public String toString() {
        return "RendezVous{" +
                "note='" + note + '\'' +
                ", date='" + date + '\'' +
                ", heure='" + heure + '\'' +
                ", cinP='" + cinP + '\'' +
                '}';
    }

    public StringProperty idProperty() {
        return new SimpleStringProperty(String.valueOf(getId()));
    }


    public int getId() {
        return id.get();
    }

    public void setId(int newId) {
        id.set(newId);
    }

    public StringProperty noteProperty() {
        return note;
    }

    public String getNote() {
        return note.get();
    }

    public void setNote(String newNote) {
        note.set(newNote);
    }

    public StringProperty dateProperty() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = (date != null && date.get() != null) ? formatter.format(date.get()) : "";
        return new SimpleStringProperty(formattedDate);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate newDate) {
        date.set(newDate);
    }

    public StringProperty heureProperty() {
        return heure;
    }

    public String getHeure() {
        return heure.get();
    }

    public void setHeure(String newHeure) {
        heure.set(newHeure);
    }

    public StringProperty cinPProperty() {
        return cinP;
    }

    public String getCinP() {
        return cinP.get();
    }

    public void setCinP(String newCinP) {
        cinP.set(newCinP);
    }
}
