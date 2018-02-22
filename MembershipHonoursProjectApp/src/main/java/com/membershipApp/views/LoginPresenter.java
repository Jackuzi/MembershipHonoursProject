package com.membershipApp.views;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.BottomNavHandle;
import com.membershipApp.DatabaseConnectionHandler;
import com.membershipApp.MembershipAppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginPresenter extends GluonPresenter<MembershipAppMain> {
  @FXML
  private View loginView;
  @FXML
  private Button loginBut;
  @FXML
  private Button cancelBut;
  @FXML
  private TextField userField;
  @FXML
  private ResourceBundle resources;
  @FXML
  private PasswordField passField;
  @FXML
  private ImageView memImg;
  @FXML
  private Label userLabel;
  private Image img = new Image("com/membershipApp/icons/memIcon1.png");
  private String currentlyLoggedUser;

  @FXML
  void exitApp(ActionEvent event) {
    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
  }


  @FXML
  void loginAttempt(ActionEvent event) {
    String surname = null;
    String email;
    String password;
    if ((!passField.equals("")) && (!userField.equals("")) && MembershipAppMain.loggedUser.isEmpty()) {
      try {
        DatabaseConnectionHandler db = new DatabaseConnectionHandler();
        db.dbServerStart();
        String sql = "SELECT NAME,SURNAME,EMAIL,PASSWORD FROM PUBLIC.EMPLOYEE WHERE EMAIL=? AND PASSWORD =? ";
        PreparedStatement pst = db.getConn().prepareStatement(sql);
        pst.setString(1, userField.getText());
        pst.setString(2, passField.getText());
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
          currentlyLoggedUser = rs.getString("NAME");
          surname = rs.getString("SURNAME");
          email = rs.getString("EMAIL");
          password = rs.getString("PASSWORD");
          System.out.println("U r logged as: " + currentlyLoggedUser + " " + surname);
          MembershipAppMain.loggedUser.put("user", currentlyLoggedUser);

        }

      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        if (currentlyLoggedUser != null) {
          Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "You are logged in as: " + currentlyLoggedUser + " " + surname);
          alert.showAndWait();
        }
      }
      if (currentlyLoggedUser != null) {
        if (Platform.isAndroid() || Platform.isIOS()) {
          MobileApplication.getInstance().switchView(MembershipAppMain.LOGIN_VIEW);
          MobileApplication.getInstance().getView().setBottom(new BottomNavHandle().createBottomNavigation());
        } else if (Platform.isDesktop() || (MobileApplication.getInstance().getScreenHeight() > 800) && (MobileApplication.getInstance().getScreenWidth() > 1000) && (!Platform.isAndroid())) {
          MembershipAppMain.scene.getWindow().centerOnScreen();
          MobileApplication.getInstance().switchView(MembershipAppMain.LOGIN_VIEW);
          MobileApplication.getInstance().getView().setBottom(new BottomNavHandle().createBottomNavigation());
          //if not succeded
        } else {
          MobileApplication.getInstance().switchView(MembershipAppMain.LOGIN_VIEW);
          MobileApplication.getInstance().getView().setBottom(new BottomNavHandle().createBottomNavigation());
        }
      } else {
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "Please correct details");
        alert.showAndWait();
        System.out.println("Please correct details");
      }
    } else {
      Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "No such user or already logged in. Please check details again");
      alert.showAndWait();
      System.out.println("No such user or already logged in. Please check details again");
    }
    //System.out.println(getCurrentlyLoggedUser());
  }


  public void initialize() {
    userLabel.setText(resources.getString("label.text"));
    memImg.setCache(true);
    memImg.setImage(img);
    loginView.setCache(true);
    loginView.setCacheShape(true);
    loginView.setCacheHint(CacheHint.SPEED);
    loginView.setShowTransitionFactory(BounceInUpTransition::new);


  }

  public String getCurrentlyLoggedUser() {
    return currentlyLoggedUser;
  }

  public void setCurrentlyLoggedUser(String currentlyLoggedUser) {
    this.currentlyLoggedUser = currentlyLoggedUser;
  }
}
