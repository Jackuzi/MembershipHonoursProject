package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.membershipApp.MembershipAppMain;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;

public class PrimaryPresenter extends GluonPresenter<MembershipAppMain> {


  @FXML
  private View primary;

  public void initialize() {
    primary.setCache(true);
    primary.setCacheShape(true);
    primary.setCacheHint(CacheHint.SPEED);
    primary.setShowTransitionFactory(v -> new BounceInUpTransition(v));
    primary.showingProperty().addListener((obs, oldValue, newValue) -> {
      if (newValue) {
        AppBar appBar = MobileApplication.getInstance().getAppBar();
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                MobileApplication.getInstance().showLayer(MembershipAppMain.MENU_LAYER)));
        appBar.setTitleText("Primary");
        appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                System.out.println("Search")));
      }
    });
  }

}
// To samo co kontroler. Dla widoku Desktopowego