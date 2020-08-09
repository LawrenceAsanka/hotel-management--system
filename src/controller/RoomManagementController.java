package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import bo.BOFactory;
import bo.BOType;
import bo.custom.RoomBO;
import bo.custom.RoomTypeBo;
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
import util.RoomTM;
import util.RoomTypeTM;

public class RoomManagementController {

  private final RoomTypeBo roomTypeBo = BOFactory.getInstance().getBO(BOType.ROOM_TYPE);
  private final RoomBO roomBO = BOFactory.getInstance().getBO(BOType.ROOM);
  private final ArrayList<RoomTM> roomDetail = new ArrayList<>();
  public AnchorPane root;
  public JFXButton btnHome;
  public TextField txtRoomTypeName;
  public TextField txtTypePrice;
  public JFXButton btnRoomTypeAdd;
  public JFXButton btnRoomTypeDelete;
  public JFXButton btnRoomTypeUpdate;
  public TableView<RoomTypeTM> tblRoomType;
  public TextField txtRoomNo;
  public ComboBox<RoomTypeTM> cmbRoomType;
  public TextField txtRoomPrice;
  public ComboBox<String> cmbRoomStatus;
  public TableView<RoomTM> tblRoomDetails;
  public JFXButton btnRoomAdd;
  public JFXButton btnRoomDelete;
  public JFXButton btnRoomUpdate;
  public JFXTextField txtSearchRoom;
  boolean result = false;

  public void initialize() {

    //Transition.....
    FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), root);
    fadeIn.setFromValue(0);
    fadeIn.setToValue(1);
    fadeIn.setCycleCount(1);

    fadeIn.play();

    //disable editable room number filed
    txtRoomNo.setEditable(false);
    //Generate ID
    generateAutoId();
    //Load all room status
    cmbRoomStatus.getItems().addAll("Reserved", "Not-Reserved");

    //Columns Mapping
    tblRoomType.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("roomTypeId"));
    tblRoomType.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("roomType"));
    tblRoomType.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("roomTypePrice"));

    tblRoomDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
    tblRoomDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("roomType"));
    tblRoomDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
    tblRoomDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("roomStatus"));

    //Load all room types
    loadAllRoomTypes();
    //Load all rooms
    loadAllRooms();

    //When user select a room type
    tblRoomType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RoomTypeTM>() {
      @Override
      public void changed(ObservableValue<? extends RoomTypeTM> observable, RoomTypeTM oldValue, RoomTypeTM newValue) {
        if (!(newValue == null)) {
          txtRoomTypeName.setText(newValue.getRoomType());
          txtTypePrice.setText(newValue.getRoomTypePrice() + "");

          btnRoomTypeAdd.setDisable(true);
        }
      }
    });

    //When user select a room type from combo box
    cmbRoomType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RoomTypeTM>() {
      @Override
      public void changed(ObservableValue<? extends RoomTypeTM> observable, RoomTypeTM oldValue, RoomTypeTM newValue) {
        if (!(newValue == null)) {
          txtRoomPrice.setText(cmbRoomType.getSelectionModel().getSelectedItem().getRoomTypePrice() + "");
        }
      }
    });

    //When user select a room
    tblRoomDetails.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RoomTM>() {
      @Override
      public void changed(ObservableValue<? extends RoomTM> observable, RoomTM oldValue, RoomTM newValue) {
        if (!(newValue == null)) {
          RoomTM selectedRoom = tblRoomDetails.getSelectionModel().getSelectedItem();

          txtRoomPrice.setEditable(false);

          txtRoomNo.setText(selectedRoom.getRoomNumber());
          cmbRoomStatus.getSelectionModel().select(selectedRoom.getRoomStatus());

          String roomType = selectedRoom.getRoomType();
          ObservableList<RoomTypeTM> rooms = cmbRoomType.getItems();
          for (RoomTypeTM room : rooms) {
            if (room.getRoomType().equals(roomType)) {
              cmbRoomType.getSelectionModel().select(room);
              txtRoomPrice.setText(room.getRoomTypePrice() + "");
            }
          }

          btnRoomAdd.setDisable(true);
        }
      }
    });

    //When user type in searchbar
    txtSearchRoom.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!(newValue == null)) {
          tblRoomDetails.getItems().clear();
          for (RoomTM rooms : roomDetail) {
            if (rooms.getRoomNumber().contains(newValue)) {
              tblRoomDetails.getItems().add(rooms);
            }
          }
        }
      }
    });

  }

  //Navigate to home
  //Add new room type

  public void btnRoomTypeAdd_OnAction(ActionEvent actionEvent) {
    //Validation
    if (txtRoomTypeName.getText().trim().length() == 0 || txtTypePrice.getText().trim().length() == 0) {
      new Alert(AlertType.ERROR, "Room Type name and price can't be empty", ButtonType.OK).showAndWait();
      return;
    }

    try {
      boolean rst = roomTypeBo.saveRoomType(txtRoomTypeName.getText(), Double.parseDouble(txtTypePrice.getText()));
      if (!rst) {
        new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).showAndWait();
        return;
      } else {
        new Alert(AlertType.INFORMATION, "Successfully added", ButtonType.OK).showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    loadAllRoomTypes();
    clearRoomType();
  }
  //Update room type

  public void btnRoomTypeUpdate_OnAction(ActionEvent actionEvent) {
    //Validation
    if (tblRoomType.getSelectionModel().isEmpty()) {
      new Alert(AlertType.ERROR, "Please select a room type to update", ButtonType.OK).showAndWait();
      return;
    }

    int selectedReservationId = tblRoomType.getSelectionModel().getSelectedItem().getRoomTypeId();
    try {
      result = roomTypeBo.updateRoomType(selectedReservationId,
          txtRoomTypeName.getText(), Double.parseDouble(txtTypePrice.getText()));
      if (!result) {
        new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).showAndWait();
      } else {
        new Alert(AlertType.CONFIRMATION, "Successfully updated", ButtonType.OK).showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    tblRoomType.refresh();
    loadAllRoomTypes();
    clearRoomType();
  }
  //Button for delete a room type

  public void btnRoomTypeDelete_OnAction(ActionEvent actionEvent) {
    //Validation
    if (tblRoomDetails.getSelectionModel().isEmpty()) {
      new Alert(AlertType.ERROR, "You need to select a room type to delete", ButtonType.OK).showAndWait();
      return;
    }

    Alert alert = new Alert(AlertType.WARNING, "Do you want to really delete the room", ButtonType.YES, ButtonType.NO);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {
      try {
        result = roomTypeBo.deleteRoomType(tblRoomType.getSelectionModel().getSelectedItem().getRoomTypeId() + "");
        if (!result) {
          new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).showAndWait();
        } else {
          new Alert(AlertType.CONFIRMATION, "Successfully deleted", ButtonType.OK).showAndWait();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      loadAllRoomTypes();
    }
    clearRoomType();

  }
  //Method for load all room types to room type combo box and table

  void loadAllRoomTypes() {
    tblRoomType.getItems().clear();
    cmbRoomType.getItems().clear();
    try {
      List<RoomTypeTM> allRoomTypes = roomTypeBo.getAllRoomTypes();
      ObservableList<RoomTypeTM> roomTypes = FXCollections.observableArrayList(allRoomTypes);
      tblRoomType.setItems(roomTypes);
      cmbRoomType.setItems(roomTypes);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  //Clear the text fields in room type

  private void clearRoomType() {
    txtRoomTypeName.clear();
    txtTypePrice.clear();
    btnRoomTypeAdd.setDisable(false);
  }
  //-------------------------------------------------------------------------------------------------------------------
  //Let's start room management
  //Add new room

  public void btnRoomAdd_OnAction(ActionEvent actionEvent) {
    //Validation
    if (cmbRoomType.getSelectionModel().isEmpty()) {
      new Alert(AlertType.ERROR, "You need to select a room type", ButtonType.OK).showAndWait();
      return;
    }

    if (cmbRoomStatus.getSelectionModel().isEmpty()) {
      new Alert(AlertType.ERROR, "You need to select a room status", ButtonType.OK).showAndWait();
      return;
    }
    try {
      result = roomBO.saveRoom(txtRoomNo.getText(), cmbRoomType.getSelectionModel().getSelectedItem().getRoomTypeId(),
          cmbRoomStatus.getSelectionModel().getSelectedItem());
      if (!result) {
        new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).showAndWait();
      } else {
        new Alert(AlertType.INFORMATION, "Successfully added", ButtonType.OK).showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    loadAllRooms();
    clearRoom();
    generateAutoId();
  }
  //Delete a room

  public void btnRoomDelete_OnAction(ActionEvent actionEvent) {
    //Validation
    if (tblRoomDetails.getSelectionModel().isEmpty()) {
      new Alert(AlertType.ERROR, "You need to select a room to delete", ButtonType.OK).showAndWait();
      return;
    }

    Alert alert = new Alert(AlertType.WARNING, "Do you want to really delete the room", ButtonType.YES, ButtonType.NO);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {
      try {
        result = roomBO.deleteRoom(tblRoomDetails.getSelectionModel().getSelectedItem().getRoomNumber());
        if (!result) {
          new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).showAndWait();
        } else {
          new Alert(AlertType.INFORMATION, "Successfully deleted", ButtonType.OK).showAndWait();
        }
        loadAllRooms();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    clearRoom();
    generateAutoId();
  }

  //Update  a room

  public void btnRoomUpdate_OnAction(ActionEvent actionEvent) {
    //Validation
    if (tblRoomDetails.getSelectionModel().isEmpty()) {
      new Alert(AlertType.ERROR, "You need to select a room to update", ButtonType.OK).showAndWait();
      return;
    }

    try {
      result = roomBO.updateRoom(tblRoomDetails.getSelectionModel().getSelectedItem().getRoomNumber(),
          cmbRoomType.getSelectionModel().getSelectedItem().getRoomTypeId(),
          cmbRoomStatus.getSelectionModel().getSelectedItem());
      if (!result) {
        new Alert(AlertType.ERROR, "Something went wrong,Please try again!", ButtonType.OK).showAndWait();
      } else {
        new Alert(AlertType.INFORMATION, "Successfully updated", ButtonType.OK).showAndWait();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    loadAllRooms();
    clearRoom();
    generateAutoId();
  }
  //Method for load all rooms

  void loadAllRooms() {
    tblRoomDetails.getItems().clear();
    try {
      List<RoomTM> allRoomDetails = roomBO.getAllRooms();
      ObservableList<RoomTM> roomDetails = FXCollections.observableArrayList(allRoomDetails);
      tblRoomDetails.setItems(roomDetails);

      //Add roomTM for empty Array,which create for searching purpose
      for (RoomTM allRoomDetail : allRoomDetails) {
        roomDetail.add(new RoomTM(allRoomDetail.getRoomNumber(), allRoomDetail.getRoomType(),
            allRoomDetail.getRoomPrice(), allRoomDetail.getRoomStatus()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  //Method for generate auto room number

  private void generateAutoId() {
    txtRoomNo.setEditable(false);
    try {
      String newRoomId = roomBO.getNewRoomId();
      txtRoomNo.setText(newRoomId);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  //Clear room text fields

  private void clearRoom() {
    txtRoomNo.clear();
    cmbRoomType.getSelectionModel().clearSelection();
    cmbRoomStatus.getSelectionModel().clearSelection();
    txtRoomPrice.clear();
    tblRoomDetails.getSelectionModel().clearSelection();
    btnRoomAdd.setDisable(false);
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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
