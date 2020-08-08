package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import bo.BOFactory;
import bo.BOType;
import bo.custom.CheckoutBO;
import bo.custom.ReservationBO;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.ReservationTM;
import util.RoomTM;

public class CheckOutController {

  private final ReservationBO reservationBO = BOFactory.getInstance().getBO(BOType.RESERVATION);
  public AnchorPane root;
  public JFXButton btnHome;
  public JFXTextField txtSearchReservationDetails;
  public TableView<ReservationTM> tblReservationDetails;
  public TextField txtGuestName;
  public TextField txtNumberOfNights;
  public TextField txtTotalAmount;
  public JFXButton btnSaveAndPrint;
  public Label lblCurrentDate;
  public JFXListView<RoomTM> lstCheckinRooms;
  List<ReservationTM> reservationDetails = new ArrayList<>();
  private final CheckoutBO checkoutBO = BOFactory.getInstance().getBO(BOType.CHECKOUT);


  public void initialize() {

    //Set current date
    LocalDate today = LocalDate.now();
    lblCurrentDate.setText(today + "");

    //Column mapping
    tblReservationDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("reservationId"));
    tblReservationDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("guestId"));
    tblReservationDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("guestName"));
    tblReservationDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
    tblReservationDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
    tblReservationDetails.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("netAmount"));

    //Load all reservation details
    loadAllReservationDetails();

    if (!(tblReservationDetails.getItems().size()>0)) {
      btnSaveAndPrint.setDisable(true);
      txtGuestName.setEditable(false);
      txtNumberOfNights.setEditable(false);
      txtTotalAmount.setEditable(false);
    }

    //Searching..........
    txtSearchReservationDetails.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue == null) {
          return;
        }
        tblReservationDetails.getItems().clear();
        for (ReservationTM reservationDetail : reservationDetails) {
          if (reservationDetail.getReservationId().contains(newValue) ||
              reservationDetail.getGuestName().contains(newValue) || reservationDetail.getCheckInDate().toString()
              .contains(newValue)) {
            tblReservationDetails.getItems().add(reservationDetail);
          }
        }
      }
    });

    // when clicked a row
    tblReservationDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ReservationTM>() {
      @Override
      public void changed(ObservableValue<? extends ReservationTM> observable, ReservationTM oldValue,
          ReservationTM newValue) {
        if (newValue == null) {
          return;
        }
        txtGuestName.setText(newValue.getGuestName());
        txtNumberOfNights.setText(String.valueOf(ChronoUnit.DAYS.between(newValue.getCheckInDate().toLocalDate(),
            newValue.getCheckOutDate().toLocalDate())));

        //calculate total
        double total = newValue.getNetAmount().doubleValue() * 3;
        txtTotalAmount.setText(total + "");

        //load all booked room for selected reservationID
        try {
          List<RoomTM> allCheckoutRooms = checkoutBO
              .findAllCheckoutRooms(tblReservationDetails.getSelectionModel().getSelectedItem().getReservationId());
          ObservableList<RoomTM> rooms = FXCollections.observableArrayList(allCheckoutRooms);
          lstCheckinRooms.setItems(rooms);
          /*for (RoomTM allCheckoutRoom : allCheckoutRooms) {
            System.out.println(allCheckoutRoom);
          }*/
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

  }

  //checkout process
  public void btnSaveAndPrint_OnAction(ActionEvent actionEvent) {
    ReservationTM selectedCheckoutDetail = tblReservationDetails.getSelectionModel().getSelectedItem();
    if (!(txtGuestName.getText().trim().length() == 0) || !(txtNumberOfNights.getText().trim().length() == 0) ||
        !(txtTotalAmount.getText().trim().length() == 0)) {
      try {
        boolean result = checkoutBO.checkout(new ReservationTM(selectedCheckoutDetail.getReservationId(),
                selectedCheckoutDetail.getGuestId(), selectedCheckoutDetail.getGuestName(),
                selectedCheckoutDetail.getCheckInDate(),
                selectedCheckoutDetail.getCheckOutDate(), new BigDecimal(txtTotalAmount.getText())),
            UserLoginController.userId,
            Date.valueOf(lblCurrentDate.getText()),
            Integer.parseInt(txtNumberOfNights.getText()),
            lstCheckinRooms.getItems());
        if (!result) {
          new Alert(AlertType.ERROR, "Something went wrong.Please try again!", ButtonType.OK).showAndWait();
        } else {
          new Alert(AlertType.INFORMATION, "Checkout has been successfully done", ButtonType.OK).showAndWait();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      loadAllReservationDetails();
      lstCheckinRooms.getSelectionModel().clearSelection();
      txtGuestName.clear();
      txtNumberOfNights.clear();
      txtTotalAmount.clear();
    } else {
      new Alert(AlertType.ERROR,"Please select a guest to checkout",ButtonType.OK).showAndWait();
    }

  }

  //Method for load all reservation details
  private void loadAllReservationDetails() {
    tblReservationDetails.getItems().clear();
    try {
      List<ReservationTM> reservationDetail = reservationBO.reservationDetails("in");
      ObservableList<ReservationTM> reservationDetailsObservableList = FXCollections
          .observableArrayList(reservationDetail);
      tblReservationDetails.setItems(reservationDetailsObservableList);

      //Add values to reservation array
      for (ReservationTM rD : reservationDetail) {
        reservationDetails
            .add(new ReservationTM(rD.getReservationId(), rD.getGuestId(), rD.getGuestName(), rD.getCheckInDate(),
                rD.getCheckOutDate(), rD.getNetAmount()));
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
      stage.centerOnScreen();
      stage.setScene(customerRegistrationScene);
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
