package com.membershipApp.views;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.BottomNavHandle;
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

  @FXML
  void exitApp(ActionEvent event) {
    Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
  }


  @FXML
  void loginAttempt(ActionEvent event) {
    //set window size
    //if succeeded
    if (Platform.isDesktop()) {
      System.out.println(MobileApplication.getInstance().getView().getName());
      MembershipAppMain.scene.getWindow().setHeight(800);
      MembershipAppMain.scene.getWindow().setWidth(1280);
      MembershipAppMain.scene.getWindow().centerOnScreen();
      MobileApplication.getInstance().switchView(MembershipAppMain.LOGIN_VIEW);
      MobileApplication.getInstance().getView().setBottom(new BottomNavHandle().createBottomNavigation());
      //if not succeded
    } else {
      MobileApplication.getInstance().switchView(MembershipAppMain.LOGIN_VIEW);
      MobileApplication.getInstance().getView().setTop(new BottomNavHandle().createBottomNavigation());

    }
  }


  public void initialize() {
    userLabel.setText(resources.getString("label.text"));
    memImg.setCache(true);
    memImg.setImage(img);
    loginView.setCache(true);
    loginView.setCacheShape(true);
    loginView.setCacheHint(CacheHint.SPEED);
    loginView.setShowTransitionFactory(v -> new BounceInUpTransition(v));


  }

}
