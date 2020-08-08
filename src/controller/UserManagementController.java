package controller;

import java.io.IOException;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import business.BOFactory;
import business.BOType;
import business.custom.UserBO;
import entity.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.UserTM;

public class UserManagementController {

  private final UserBO userBO = BOFactory.getInstance().getBO(BOType.USER);
  public JFXButton btnHome;
  public AnchorPane root;
  public JFXButton btnAddUser;
  public Label lblUserId;
  public JFXTextField txFirstName;
  public JFXTextField txtEmail;
  public JFXTextField txtContact;
  public JFXComboBox<String> cmbUserRole;
  public JFXTextField txtUsername;
  public JFXTextField txtPassword;
  public JFXButton btnDelete;
  public JFXButton btnSave;
  public TableView<UserTM> tblUserDetails;

  public void initialize() {

    //some initialize
    btnSave.setDisable(true);
    btnDelete.setDisable(true);

    //Add values to user role combo box
    cmbUserRole.getItems().addAll("admin", "manager", "staff");

    //Let's map some columns
    tblUserDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("userId"));
    tblUserDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("firstName"));
    tblUserDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("email"));
    tblUserDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
    tblUserDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("userRole"));
    tblUserDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("userName"));
    tblUserDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("password"));

    //Load all user details
    getAllUsers();

    //When clicked ona a raw in th table
    tblUserDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserTM>() {
      @Override
      public void changed(ObservableValue<? extends UserTM> observable, UserTM oldValue, UserTM newValue) {
        if (newValue == null) {
          return;
        }
        UserTM selectedUser = tblUserDetails.getSelectionModel().getSelectedItem();

        lblUserId.setText(selectedUser.getUserId());
        txFirstName.setText(selectedUser.getFirstName());
        txtEmail.setText(selectedUser.getEmail());
        txtContact.setText(selectedUser.getContact());
        txtUsername.setText(selectedUser.getUserName());
        txtPassword.setText(selectedUser.getPassword());
        cmbUserRole.getSelectionModel().select(selectedUser.getUserRole());

        btnDelete.setDisable(false);
        btnSave.setDisable(false);
        btnDelete.setDisable(false);
        btnSave.setText("UPDATE");

      }
    });
  }

  //generate user id
  public void btnAddUser_OnAction(ActionEvent actionEvent) {
    try {
      String newUserId = userBO.getNewUserId();
      lblUserId.setText(newUserId);
    } catch (Exception e) {
      e.printStackTrace();
    }

    btnSave.setDisable(false);


  }

  //Let's click delete button
  public void btnDelete_OnAction(ActionEvent actionEvent) {
    Alert alert = new Alert(AlertType.WARNING, "Do you want to delete the User?", ButtonType.YES, ButtonType.NO);
    alert.showAndWait();
    if (alert.getResult() == ButtonType.YES) {
      try {
        boolean result = userBO.deleteUser(tblUserDetails.getSelectionModel().getSelectedItem().getUserId());
        if (!result) {
          new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).showAndWait();
        } else {
          new Alert(AlertType.ERROR, "Successfully deleted", ButtonType.OK).showAndWait();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    clearField();
    getAllUsers();
    btnSave.setText("SAVE");
    btnSave.setDisable(true);
    btnDelete.setDisable(true);
  }

  //Store and update the values
  public void btnSave_OnAction(ActionEvent actionEvent) {
    if (validation()) {
      if (btnSave.getText().equals("SAVE")) {
        try {
          boolean result = userBO
              .saveGuest(new User(lblUserId.getText(), txFirstName.getText(), txtEmail.getText(), txtContact.getText(),
                  cmbUserRole.getSelectionModel().getSelectedItem(), txtUsername.getText(), txtPassword.getText()));
          if (!result) {
            new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).showAndWait();
          } else {
            new Alert(AlertType.ERROR, "Successfully added", ButtonType.OK).showAndWait();
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        try {
          boolean result = userBO
              .updateUser(new UserTM(tblUserDetails.getSelectionModel().getSelectedItem().getUserId(),
                  txFirstName.getText(), txtEmail.getText(), txtContact.getText(),
                  cmbUserRole.getSelectionModel().getSelectedItem(),
                  txtUsername.getText(), txtPassword.getText()));
          if (!result) {
            new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).showAndWait();
          } else {
            new Alert(AlertType.ERROR, "Successfully updated", ButtonType.OK).showAndWait();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }

        btnSave.setText("SAVE");
        btnSave.setDisable(true);

        tblUserDetails.refresh();
        getAllUsers();
        clearField();
      }
    }

  }

  //Navigate to home
  public void btnHome_OnAction(ActionEvent actionEvent) {

    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/dashBoard.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("DASHBOARD");
      stage.setScene(customerRegistrationScene);
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void getAllUsers() {
    tblUserDetails.getItems().clear();
    try {
      List<UserTM> allUsers = userBO.getAllUsers();
      ObservableList<UserTM> userArrayList = FXCollections.observableArrayList(allUsers);
      tblUserDetails.setItems(userArrayList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void clearField() {
    cmbUserRole.getSelectionModel().clearSelection();
    tblUserDetails.getSelectionModel().clearSelection();
    txFirstName.clear();
    txtEmail.clear();
    txtContact.clear();
    txtUsername.clear();
    txtPassword.clear();
    lblUserId.setText("XXXX");
    btnAddUser.requestFocus();
  }

  private boolean validation() {
    if (txtEmail.getText().trim().length() == 0 || !txtEmail.getText()
        .matches("^[a-z]+([.0-9a-z])*[@]([a-z])+[.]([a-z.])*")) {
      txtEmail.selectAll();
      txtEmail.requestFocus();
      return false;
    }
    if (cmbUserRole.getSelectionModel().isEmpty()) {
      cmbUserRole.requestFocus();
      return false;
    }
    if (txtContact.getText().trim().length() == 0 || !txtContact.getText().matches("^[0-9]{3}[-][0-9]{7}")) {
      txtContact.selectAll();
      txtContact.requestFocus();
      return false;
    }
    if (txtUsername.getText().trim().length() == 0 || !txtUsername.getText().matches("^[a-zA-Z]+([\\s.a-zA-Z])*")) {
      txtUsername.selectAll();
      txtUsername.requestFocus();
      return false;
    }
    return true;
  }
}

