package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import business.BOFactory;
import business.BOType;
import business.custom.GuestBO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.GuestTM;

public class GuestRegistrationController {

  public static String guestId;
  public static String guestFName;
  public static String guestLName;
  public static String guestAddress;
  public static String guestNIC;
  public static String guestCountry;
  public static String guestContact;
  private final GuestBO guestBO = BOFactory.getInstance().getBO(BOType.GUEST);
  public JFXButton btnHome;
  public JFXButton btnNext;
  public AnchorPane root;
  public JFXTextField txtGuestId;
  public JFXTextField txtGuestFName;
  public JFXTextField txtGuestLName;
  public JFXTextField txtGuestAddress;
  public JFXTextField txtGuestCountry;
  public JFXTextField txtGuestNIC;
  public JFXTextField txtGuestContact;
  public JFXTextField txtGuestSearch;
  public TableView<GuestTM> tblGuestDetails;
  ArrayList<GuestTM> guests = new ArrayList<>();

  public void initialize() {

    //Column mapping
    tblGuestDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("guestFirstName"));
    tblGuestDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("guestLastName"));
    tblGuestDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("passportNumber"));
    tblGuestDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("guestCountry"));

    txtGuestId.setEditable(false);
    //Generate ID 
    generateGuestId();

    //Load all checkedOut guests
    loadAllCheckedOutGuests();

    //Let's do some search
    txtGuestSearch.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        ObservableList<GuestTM> guestsOL = tblGuestDetails.getItems();
        guestsOL.clear();
        for (GuestTM guest : guests) {
          if (guest.getGuestFirstName().contains(newValue) || guest.getGuestLastName().contains(newValue) ||
              guest.getGuestId().contains(newValue)) {
            guestsOL.add(guest);
          }
        }
      }
    });

    //What's happen when user clicked a table raw
    tblGuestDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GuestTM>() {
      @Override
      public void changed(ObservableValue<? extends GuestTM> observable, GuestTM oldValue, GuestTM newValue) {
        txtGuestId.setText(newValue.getGuestId());
        txtGuestFName.setText(newValue.getGuestFirstName());
        txtGuestLName.setText(newValue.getGuestLastName());
        txtGuestAddress.setText(newValue.getGuestAddress());
        txtGuestNIC.setText(newValue.getPassportNumber());
        txtGuestCountry.setText(newValue.getGuestCountry());
        txtGuestContact.setText(newValue.getGuestContact());
      }
    });

  }

  //Navigate to home
  public void btnHome_OnAction(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/dashBoard.fxml"));
      Scene customerRegistrationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("DASHBOARD");
      stage.centerOnScreen();
      stage.setResizable(false);
      stage.setScene(customerRegistrationScene);
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  //Navigate to Registration process UI
  public void btnNext_OnAction(ActionEvent actionEvent) {

    //Take the values to Reservation Process Controller
    if (getValidation()) {
      guestId = txtGuestId.getText();
      guestFName = txtGuestFName.getText();
      guestLName = txtGuestLName.getText();
      guestAddress = txtGuestAddress.getText();
      guestNIC = txtGuestNIC.getText();
      guestCountry = txtGuestCountry.getText();
      guestContact = txtGuestContact.getText();

      try {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/reservationProcess.fxml"));
        Scene customerRegistrationScene = new Scene(root);
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setTitle("CHECKING PROCESS");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(customerRegistrationScene);
        stage.sizeToScene();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  //Method for generate auto ID
  private void generateGuestId() {
    try {
      String newCustomerId = guestBO.getNewGuestId();
      txtGuestId.setText(newCustomerId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Method for load all guests
  private void loadAllCheckedOutGuests() {
    try {
      List<GuestTM> allGuests = guestBO.getAllCheckedOutGuests("out");
      ObservableList<GuestTM> guest = FXCollections.observableArrayList(allGuests);
      tblGuestDetails.setItems(guest);

      for (GuestTM allGuest : allGuests) {
        guests.add(new GuestTM(allGuest.getGuestId(), allGuest.getGuestFirstName(), allGuest.getGuestLastName(),
            allGuest.getGuestAddress(),
            allGuest.getPassportNumber(), allGuest.getGuestCountry(), allGuest.getGuestContact(),
            allGuest.getGuestStatus()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Validation Method
  private boolean getValidation() {
    if (txtGuestFName.getText().trim().length() == 0 || !txtGuestFName.getText().matches("^[a-zA-Z]+([\\s.a-zA-Z])*")) {
      txtGuestFName.selectAll();
      txtGuestFName.requestFocus();
      return false;
    }
    if (txtGuestLName.getText().trim().length() == 0 || !txtGuestLName.getText().matches("^[a-zA-Z]+([\\s.a-zA-Z])*")) {
      txtGuestLName.selectAll();
      txtGuestLName.requestFocus();
      return false;
    }
    if (txtGuestAddress.getText().trim().length() == 0 || !txtGuestAddress.getText()
        .matches("^[a-zA-Z0-9]+([\\sa-zA-Z0-9,-/])*")) {
      txtGuestAddress.selectAll();
      txtGuestAddress.requestFocus();
      return false;
    }
    if (txtGuestCountry.getText().trim().length() == 0 || !txtGuestCountry.getText().matches("^[a-zA-Z]+([\\sa-zA-Z0-9])*")) {
      txtGuestCountry.selectAll();
      txtGuestCountry.requestFocus();
      return false;
    }
    if (txtGuestNIC.getText().trim().length() == 0 || !txtGuestNIC.getText().matches("^[0-9]+[vV]")) {
      txtGuestNIC.selectAll();
      txtGuestNIC.requestFocus();
      return false;
    }
    if (txtGuestContact.getText().trim().length() == 0 || !txtGuestContact.getText().matches("^[0-9]{3}[-][0-9]*")) {
      txtGuestContact.selectAll();
      txtGuestContact.requestFocus();
      return false;
    }

    return true;
  }
}
