module cabinetdoctor.cabinetdoctor {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    exports cabinetdoctor;
    opens cabinetdoctor to javafx.fxml;
    exports cabinetdoctor.vue;
    opens cabinetdoctor.vue to javafx.fxml;
    exports cabinetdoctor.Controles;
    opens cabinetdoctor.Controles to javafx.fxml;
    exports cabinetdoctor.Service;
    opens cabinetdoctor.Service to javafx.fxml;
    exports cabinetdoctor.Model;
    opens cabinetdoctor.Model to javafx.fxml;
}