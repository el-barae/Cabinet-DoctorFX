package cabinetdoctor;

import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class CabinetDoctorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        cabinetdoctor.Controles.DBManager.createDBTables();
        cabinetdoctor.Controles.DBManager.createDBUsers();
       new Login();
    }

    public static void main(String[] args) {
        launch();
    }
}