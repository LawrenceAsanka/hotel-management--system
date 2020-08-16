package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import bo.BOFactory;
import bo.BOType;
import bo.custom.GuestBO;
import javafx.animation.FadeTransition;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.GuestTM;

public class GuestManagementController {

  private final GuestBO guestBO = BOFactory.getInstance().getBO(BOType.GUEST);
  public JFXButton btnHome;
  public AnchorPane root;
  public JFXTextField txtSearchGuest;
  public TableView<GuestTM> tblGuestDetails;
  public TextField txtFirstName;
  public TextField txtLastName;
  public TextField txtContact;
  public JFXButton btnDelete;
  public TextField txtCountry;
  public JFXButton btnUpdate;
  public TextField txtAddress;
  public TextField txtNICAndPassword;
  public ComboBox<String> cmbStatus;
  List<GuestTM> guestArrayList = new ArrayList<>();

  public void initialize() {

    //Transition.....
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), root);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.setCycleCount(1);

    fadeIn.play();

    //Add values to combo box
    cmbStatus.getItems().addAll("in", "out");

    //Wrong with delete button
    btnDelete.setDisable(true);

    //Column mapping
    tblGuestDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("guestId"));
    tblGuestDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("guestFirstName"));
    tblGuestDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("guestLastName"));
    tblGuestDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("guestAddress"));
    tblGuestDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("passportNumber"));
    tblGuestDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("guestCountry"));
    tblGuestDetails.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("guestContact"));
    tblGuestDetails.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("guestStatus"));

    //Load all guest
    loadAllGuests();

    //What happens when click one raw
    tblGuestDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GuestTM>() {
      @Override
      public void changed(ObservableValue<? extends GuestTM> observable, GuestTM oldValue, GuestTM newValue) {
        if (newValue == null) {
          return;
        }
        txtFirstName.setText(newValue.getGuestFirstName());
        txtLastName.setText(newValue.getGuestLastName());
        txtContact.setText(newValue.getGuestContact());
        txtCountry.setText(newValue.getGuestCountry());
        txtNICAndPassword.setText(newValue.getPassportNumber());
        txtAddress.setText(newValue.getGuestAddress());
        cmbStatus.getSelectionModel().select(newValue.getGuestStatus());

        btnDelete.setDisable(false);
      }
    });

    //Let's do some search
    txtSearchGuest.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        ObservableList<GuestTM> guest = tblGuestDetails.getItems();
        guest.clear();
        for (GuestTM guestsArray : guestArrayList) {
          if (guestsArray.getGuestFirstName().contains(newValue) ||
              guestsArray.getGuestLastName().contains(newValue) || guestsArray.getGuestCountry().contains(newValue) ||
              guestsArray.getPassportNumber().contains(newValue)) {
            tblGuestDetails.getItems().add(guestsArray);
          }
        }
      }
    });

  }

  //Delete button procedure
  public void btnDelete_OnAction(ActionEvent actionEvent) {
    if (tblGuestDetails.getSelectionModel().isEmpty()) {
      new Alert(AlertType.ERROR, "Please select a guest to delete", ButtonType.OK).showAndWait();
      return;
    }
    Alert alert = new Alert(AlertType.WARNING, "Do you want to really delete the guest", ButtonType.YES, ButtonType.NO);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {
      try {
        boolean result = guestBO.deleteGuest(tblGuestDetails.getSelectionModel().getSelectedItem().getGuestId());
        if (!result) {
          new Alert(AlertType.ERROR, "Something went wrong,Please try again!!", ButtonType.OK).showAndWait();
        } else {
          new Alert(AlertType.INFORMATION, "Successfully deleted", ButtonType.OK).showAndWait();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      tblGuestDetails.refresh();
      loadAllGuests();
    }
    clearFields();

  }

  //Update button procedure
  public void btnUpdate_OnAction(ActionEvent actionEvent) {
    if (tblGuestDetails.getSelectionModel().isEmpty()) {
      new Alert(AlertType.ERROR, "Please select a guest to update", ButtonType.OK).showAndWait();
      return;
    }
    if (validation()) {
      try {
        boolean result = guestBO
            .updateGuest(new GuestTM(tblGuestDetails.getSelectionModel().getSelectedItem().getGuestId(),
                txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtNICAndPassword.getText(),
                txtCountry.getText(), txtContact.getText(), cmbStatus.getSelectionModel().getSelectedItem()
            ));
        if (!result) {
          new Alert(AlertType.ERROR, "Something went wrong,Please try again!!", ButtonType.OK).showAndWait();
        } else {
          new Alert(AlertType.INFORMATION, "Successfully updated", ButtonType.OK).showAndWait();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      clearFields();
      tblGuestDetails.refresh();
      loadAllGuests();
    }

  }

  //Method for load all guest
  private void loadAllGuests() {
    tblGuestDetails.getItems().clear();
    try {
      List<GuestTM> allGuest = guestBO.getAllGuest();
      ObservableList<GuestTM> guests = FXCollections.observableArrayList(allGuest);
      tblGuestDetails.setItems(guests);

      for (GuestTM guestTM : allGuest) {
        guestArrayList.add(new GuestTM(guestTM.getGuestId(), guestTM.getGuestFirstName(), guestTM.getGuestLastName(),
            guestTM.getGuestAddress(),
            guestTM.getPassportNumber(), guestTM.getGuestCountry(), guestTM.getGuestContact(),
            guestTM.getGuestStatus()));
      }
    } catch (Exception e) {
      e.printStackTrace();
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

  //text field clear method
  private void clearFields() {
    tblGuestDetails.getSelectionModel().clearSelection();
    txtFirstName.clear();
    txtLastName.clear();
    txtAddress.clear();
    txtNICAndPassword.clear();
    txtCountry.clear();
    txtContact.clear();
    cmbStatus.getSelectionModel().clearSelection();
  }

  private boolean validation() {
    if (txtFirstName.getText().trim().length() == 0 || !txtFirstName.getText().matches("^[a-zA-Z]+([\\s.a-zA-Z])*")) {
      txtFirstName.selectAll();
      txtFirstName.requestFocus();
      return false;
    }
    if (txtLastName.getText().trim().length() == 0 || !txtLastName.getText().matches("^[a-zA-Z]+([\\s.a-zA-Z])*")) {
      txtLastName.selectAll();
      txtLastName.requestFocus();
      return false;
    }
    if (txtAddress.getText().trim().length() == 0 || !txtAddress.getText()
        .matches("^[a-zA-Z0-9]+([\\sa-zA-Z0-9,-/])*")) {
      txtAddress.selectAll();
      txtAddress.requestFocus();
      return false;
    }
    if (txtCountry.getText().trim().length() == 0 || !txtCountry.getText().matches("^[a-zA-Z]+([\\sa-zA-Z0-9])*")) {
      txtCountry.selectAll();
      txtCountry.requestFocus();
      return false;
    }
    if (txtNICAndPassword.getText().trim().length() == 0 || !txtNICAndPassword.getText().matches("^[0-9]+[vV]")) {
      txtNICAndPassword.selectAll();
      txtNICAndPassword.requestFocus();
      return false;
    }
    if (txtContact.getText().trim().length() == 0 || !txtContact.getText().matches("^[0-9]{3}[-][0-9]*")) {
      txtContact.selectAll();
      txtContact.requestFocus();
      return false;
    }

    return true;
  }
}

