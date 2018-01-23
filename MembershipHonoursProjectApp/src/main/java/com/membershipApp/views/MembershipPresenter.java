package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.MembershipAppMain;
import javafx.fxml.FXML;

public class MembershipPresenter extends GluonPresenter<MembershipAppMain> {
  @FXML
  private View membershipView;


  public void initialize() {
    membershipView.setShowTransitionFactory(v -> new BounceInUpTransition(v));
  }
}
