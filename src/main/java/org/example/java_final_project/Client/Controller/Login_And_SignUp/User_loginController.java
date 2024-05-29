package org.example.java_final_project.Client.Controller.Login_And_SignUp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.java_final_project.Client.Controller.ClientCore;
import org.example.java_final_project.Main;
import org.example.java_final_project.Model.Request;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class User_loginController implements Initializable {

    /*Text Filed*/
    @FXML
    private TextField SDT_text;
    @FXML
    private TextField pass;

    /*ImageView*/
    @FXML
    private ImageView close_eye;
    @FXML
    private ImageView open_eye;

    /*Button*/
    @FXML
    private Button login_button;
    @FXML
    private Button signUpButton;

    /*Other*/
    @FXML
    private PasswordField pass_hide;
    @FXML
    private Label information;
    private Stage prevStage ;
    private String password;
    public void setPrevStage(Stage stage) { // Lấy dữ liệu từ stage cũ để chuyển stage mới
        this.prevStage = stage;
    }

    public void ShowPasswordOnAction(KeyEvent keyEvent) {
        CheckFields();
        password = pass.getText() ;
        pass_hide.setText(password);
    }
    public void HidePasswordOnAction(KeyEvent keyEvent) {
        CheckFields();
        password = pass_hide.getText();
        pass.setText(password);
    }
    public void open_eye_OnAction(MouseEvent mouseEvent) { // Đóng mắt
        pass_hide.setVisible(true);
        close_eye.setVisible(true);
        pass.setVisible(false);
        open_eye.setVisible(false);
    }
    public void close_eye_OnAction(MouseEvent mouseEvent) { // mở mắt
        pass_hide.setVisible(false);
        close_eye.setVisible(false);
        pass.setVisible(true);
        open_eye.setVisible(true);

        // Start a timer to hide the password after 3 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> open_eye_OnAction(null));
            }
        }, 1000);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pass.setVisible(false);
        open_eye.setVisible(false);
        SDT_text.textProperty().addListener((observable, oldValue, newValue) -> {
            CheckFields();
            information.setVisible(false);
        });
        pass.textProperty().addListener((observable, oldValue, newValue) -> {
            CheckFields();
            information.setVisible(false);
        });
        pass_hide.textProperty().addListener((observable, oldValue, newValue) -> {
            CheckFields();
            information.setVisible(false);
        });
        //Dữ liệu sẽ
        signUpButton.setOnAction(e -> changeScene());
        login_button.setOnAction(e -> sendData());
    }



    private void CheckFields(){
        boolean isUsernameEmpty = SDT_text.getText().isEmpty() ;
        boolean isPasswordEmpty = pass.isVisible() ? pass.getText().isEmpty() : pass_hide.getText().isEmpty() ;
        if(!isPasswordEmpty && !isUsernameEmpty){
            login_button.setDisable(false);
        }else{
            login_button.setDisable(true);
        }
    }
    private void sendData() {
                String SDT = SDT_text.getText();
                String password = pass.getText();
                String request = Request.LOGIN;
        Runnable checkAccount = new Runnable() {
            @Override
            public void run() {
                new ClientCore(SDT,password,request,information) ;
            }
        };
        checkAccount.run();
    }
    private void changeScene() {
       Runnable scene = new Runnable() {
           @Override
           public void run() {
               try{
                   FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("User-create-account.fxml"));

                   Parent root = fxmlLoader.load();

                   User_Sign_UpController signUpController = fxmlLoader.getController() ;
                   signUpController.setPreSignInStage(prevStage);

                   prevStage.getScene().setRoot(root);

                   System.out.println("Dieu huong toi dang ki ");
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
       };
       scene.run();
    }


}