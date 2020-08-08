package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import bo.BOFactory;
import bo.BOType;
import bo.custom.UserBO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserLoginController {

  public JFXTextField txtUserName;
  public JFXComboBox<String> cmbUserRole;
  public JFXButton btnLogin;
  public AnchorPane root;
  public static String userId;
  public JFXPasswordField txtPassword;


  private final UserBO userBO = BOFactory.getInstance().getBO(BOType.USER);

  public void initialize() {

    cmbUserRole.getItems().addAll("admin","manager","staff");
  }

  public void btnLogin_OnAction(ActionEvent actionEvent) {

    try {
      String login = userBO
          .login(txtUserName.getText(), txtPassword.getText(), cmbUserRole.getSelectionModel().getSelectedItem());

      if (login == null) {
        new Alert(AlertType.ERROR, "Invalid username or password", ButtonType.OK).showAndWait();
        return;
      } else {
        userId = login;
        try {
          Parent root = FXMLLoader.load(this.getClass().getResource("/view/dashBoard.fxml"));
          Scene dashBoardScene = new Scene(root);
          Stage stage = (Stage) this.root.getScene().getWindow();
          stage.setTitle("DASHBOARD");
          stage.setScene(dashBoardScene);
          stage.centerOnScreen();
          stage.show();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}
