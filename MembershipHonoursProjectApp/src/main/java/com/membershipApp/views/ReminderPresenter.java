package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.MembershipAppMain;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ReminderPresenter extends GluonPresenter<MembershipAppMain> {

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;


  @FXML
  private View reminderView;


  public void initialize() {
    reminderView.setCache(true);
    reminderView.setCacheShape(true);
    reminderView.setCacheHint(CacheHint.SPEED);
    reminderView.setShowTransitionFactory(v -> new BounceInUpTransition(v));
  }


  @PostConstruct
  public void init() {
    System.out.println("init");


  }
}
