package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashboardController {

  public AnchorPane root;
  public JFXButton btnUserMgt;
  public JFXButton btnCheckIn;
  public JFXButton btnCheckOut;
  public JFXButton btnRoomMgt;
  public JFXButton btnGuestMgt;
  public FontAwesomeIconView btn_logout;
  public JFXButton btnReport;

  public void btnCheckIn_OnAction(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/guestRegistration.fxml"));
      Scene mainScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("CHECKING PROCESS");
      stage.setScene(mainScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void btnCheckOut_OnAction(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/checkOut.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("CHECKOUT PROCESS");
      stage.setScene(customerRegistrationScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void btnGuestMgt_OnAction(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/guestManagement.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("GUEST MANAGEMENT");
      stage.setScene(customerRegistrationScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void btnRoomMgt_OnAction(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/roomManagement.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("ROOM MANAGEMENT");
      stage.setScene(customerRegistrationScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void btnUserMgt_OnAction(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/userManagement.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("USER MANAGEMENT");
      stage.setScene(customerRegistrationScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void btnReport_OnAction(ActionEvent actionEvent) {
  }

  public void iconCheckIn_OnMouseClicked(MouseEvent mouseEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/guestRegistration.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("CHECKIN");
      stage.setScene(customerRegistrationScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void iconCheckOut_OnMouseCLicked(MouseEvent mouseEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/checkOut.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("CHECKOUT PROCESS");
      stage.setScene(customerRegistrationScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void iconGuestMgt_OnMouseClicked(MouseEvent mouseEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/guestManagement.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("GUEST MANAGEMENT");
      stage.setScene(customerRegistrationScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void iconRoomMgt_OnMouseClicked(MouseEvent mouseEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/roomManagement.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("ROOM MANAGEMENT");
      stage.setScene(customerRegistrationScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void iconUserMgt_OnMouseClicked(MouseEvent mouseEvent) {

      try {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/userManagement.fxml"));
        Scene customerRegistrationScene = new Scene(root);
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setTitle("USER MANAGEMENT");
        stage.setScene(customerRegistrationScene);
        stage.centerOnScreen();
        stage.sizeToScene();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


  public void iconReport_OnMouseClicked(MouseEvent mouseEvent) {
  }

  public void icon_logout_OnMouseClicked(MouseEvent mouseEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/userLogin.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("DASHBOARD");
      stage.setScene(customerRegistrationScene);
      stage.centerOnScreen();
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
