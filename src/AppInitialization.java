import java.io.IOException;
import java.sql.SQLException;

import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitialization extends Application {

  public static void main(String[] args) {
    launch(args);
    try {
      DBConnection.getInstance().getConnection().close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }

  }

  @Override
  public void start(Stage primaryStage) {

    try {
      Parent root = FXMLLoader.load(this.getClass().getResource("/view/roomManagement.fxml"));
      Scene dashBoardScene = new Scene(root);
      primaryStage.setScene(dashBoardScene);
      primaryStage.centerOnScreen();
      primaryStage.setResizable(false);
      //primaryStage.initStyle(StageStyle.UNDECORATED);
      primaryStage.setTitle("WELCOME TO THE LOGIN");
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
