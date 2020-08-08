package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import business.BOFactory;
import business.BOType;
import business.custom.ReservationBO;
import business.custom.RoomBO;
import business.custom.RoomTypeBo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.GuestTM;
import util.ReservationDetailTM;
import util.ReservationTM;
import util.RoomTM;
import util.RoomTypeTM;

public class ReservationProcessController {

  private final ReservationBO reservationBO = BOFactory.getInstance().getBO(BOType.RESERVATION);
  private final RoomTypeBo roomTypeBo = BOFactory.getInstance().getBO(BOType.ROOM_TYPE);
  private final RoomBO roomBO = BOFactory.getInstance().getBO(BOType.ROOM);

  public JFXButton btnBack;
  public JFXButton btnCheckIn;
  public AnchorPane root;
  public Label lblReservationId;
  public TextField txtCheckIn;
  public DatePicker txtCheckOut;
  public ComboBox<RoomTypeTM> cmbRoomType;
  public JFXListView<RoomTM> lstAvailableRoom;
  public TableView<ReservationDetailTM> tblReservationDetail;
  public TextField txtNumberOfNights;
  boolean result = false;

  public void initialize() {

    txtNumberOfNights.setEditable(false);
    txtCheckIn.setEditable(false);

    //Disable dates
    txtCheckOut.setDayCellFactory(picker -> new DateCell() {
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);
        LocalDate today = LocalDate.now();
        setDisable(empty || date.compareTo(today.plusDays(1)) < 0);
      }
    });

    //Column Mapping
    tblReservationDetail.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
    tblReservationDetail.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("roomType"));
    tblReservationDetail.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
    tblReservationDetail.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("button"));

    //Generate reservation id
    generateReservationId();

    //Set the current date
    LocalDate today = LocalDate.now();
    txtCheckIn.setText(today + "");

    //Load all room types to combo box
    loadAllRoomTypes();

    //What's happen when clicked room type combo box
    cmbRoomType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RoomTypeTM>() {
      @Override
      public void changed(ObservableValue<? extends RoomTypeTM> observable, RoomTypeTM oldValue, RoomTypeTM newValue) {
        if (!(newValue == null)) {
          try {
            //find the available rooms which are not reserved
            List<RoomTM> availableRooms = roomBO.getAvailableRooms(newValue.getRoomTypeId(), "Not-Reserved");
            ObservableList<RoomTM> rooms = FXCollections.observableArrayList(availableRooms);
            lstAvailableRoom.setItems(rooms);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    });

    tblReservationDetail.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<ReservationDetailTM>() {
          @Override
          public void changed(ObservableValue<? extends ReservationDetailTM> observable, ReservationDetailTM oldValue,
              ReservationDetailTM newValue) {

            if (newValue==null) {
              return;
            }
            ObservableList<ReservationDetailTM> selectedRoom = tblReservationDetail.getItems();
            Button button = newValue.getButton();

            //to delete the selected raw
            button.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                selectedRoom.remove(newValue);
                lstAvailableRoom.getItems().clear();
                cmbRoomType.getSelectionModel().clearSelection();
                cmbRoomType.requestFocus();
              }
            });


          }
        });

    //What's happen when clicked list box
    lstAvailableRoom.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RoomTM>() {
      @Override
      public void changed(ObservableValue<? extends RoomTM> observable, RoomTM oldValue, RoomTM newValue) {
        ObservableList<ReservationDetailTM> roomDetails = tblReservationDetail.getItems();
        if (newValue == null) {
          return;
        }

        try {
          ReservationDetailTM room = roomBO.findRoom(newValue.getRoomNumber());
          tblReservationDetail.getItems().add(room);

        } catch (Exception e) {
          e.printStackTrace();
        }


      }
    });



  }

  //Method for generate reservation id
  private void generateReservationId() {
    try {
      String newReservationId = reservationBO.getNewReservationId();
      lblReservationId.setText(newReservationId);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  //Navigate to home
  public void btnBack_OnAction(ActionEvent actionEvent) {
    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/guestRegistration.fxml"));
      Scene reservationScene = new Scene(root);
      Stage stage = (Stage) this.root.getScene().getWindow();
      stage.setTitle("RESERVATION");
      stage.centerOnScreen();
      stage.setResizable(false);
      stage.setScene(reservationScene);
      stage.sizeToScene();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  //Checkin Button process
  public void btnCheckIn_OnAction(ActionEvent actionEvent) {
    //Checking valid or not
    if (validation()) {
      try {
        result = reservationBO
            .reserved(new GuestTM(GuestRegistrationController.guestId, GuestRegistrationController.guestFName,
                    GuestRegistrationController.guestLName, GuestRegistrationController.guestAddress,
                    GuestRegistrationController.guestNIC,
                    GuestRegistrationController.guestCountry, GuestRegistrationController.guestContact, "in"
                ),
                GuestRegistrationController.guestId,
                UserLoginController.userId,
                new ReservationTM(lblReservationId.getText(), GuestRegistrationController.guestId,
                    GuestRegistrationController.guestFName,
                    Date.valueOf(txtCheckIn.getText()), Date.valueOf(txtCheckOut.getValue()), BigDecimal.valueOf(000.00)
                ),
                tblReservationDetail.getItems());

        if (!result) {
          new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).show();
          return;
        }
        new Alert(AlertType.INFORMATION, "Guest checking successfully", ButtonType.OK).showAndWait();

      } catch (Exception e) {
        e.printStackTrace();
      }

      lblReservationId.setText("XXXX");
      txtCheckIn.clear();
      cmbRoomType.getSelectionModel().clearSelection();
      txtNumberOfNights.clear();
      lstAvailableRoom.getSelectionModel().clearSelection();
      tblReservationDetail.getSelectionModel().clearSelection();

      try {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/guestRegistration.fxml"));
        Scene reservationScene = new Scene(root);
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setTitle("RESERVATION");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(reservationScene);
        stage.sizeToScene();
      } catch (IOException e) {
        e.printStackTrace();
      }

    }


  }

  //Method for load all room types
  private void loadAllRoomTypes() {
    try {
      List<RoomTypeTM> allRoomTypes = roomTypeBo.getAllRoomTypes();
      ObservableList<RoomTypeTM> rooms = FXCollections.observableArrayList(allRoomTypes);
      cmbRoomType.setItems(rooms);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //Validation Method
  private boolean validation() {

    if (txtCheckOut.getValue() == null) {
      txtCheckOut.requestFocus();
      return false;
    }
    if (cmbRoomType.getSelectionModel().isEmpty()) {
      cmbRoomType.requestFocus();
      return false;
    }
    if (tblReservationDetail.getItems().size() == 0) {
      new Alert(AlertType.ERROR, "Select one or more rooms to check-in", ButtonType.OK).showAndWait();
      return false;
    }
    return true;
  }


}
