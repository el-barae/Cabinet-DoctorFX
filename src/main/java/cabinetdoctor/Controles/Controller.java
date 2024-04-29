package cabinetdoctor.Controles;

import cabinetdoctor.Login;
import cabinetdoctor.Option;
import cabinetdoctor.Signup;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Controller {
    @FXML
    private Button cancelButton;
    @FXML
    private Button signupButton;
    @FXML
    public Button loginButton;
    @FXML
    private Label errorMessage;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label accessMessage;
    @FXML
    private TextField cinTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    Button loginWindow;
    @FXML
    Button signupWindow;

    @FXML
    public void loginWindowAction(ActionEvent e) throws IOException {
        new Login();
        ((Node)(e.getSource())).getScene().getWindow().hide();
    }
    public void signupWindowAction(ActionEvent e) throws IOException {
        new Signup();
        ((Node)(e.getSource())).getScene().getWindow().hide();
    }

    public void loginButtonAction(ActionEvent e) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if(BDController.login(username, password)){
            accessMessage.setText("Access Granted");
            new Option();
        }
        else{
            errorMessage.setText("Invalid username or password");
        }
    }

    public void signupButtonAction(ActionEvent e){
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String cin = cinTextField.getText();
        String email = emailTextField.getText();
        if(BDController.signup(username, password, email, cin)){
            accessMessage.setText("User have been created successfully(Try Login Now)");
        }
    }

    public void cancelButtonAction(ActionEvent e){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}