package com.membershipApp.views;

import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.membershipApp.MembershipAppMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryPresenter implements Initializable {


    @FXML
    private View primary;

    public void initialize() {


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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primary.setShowTransitionFactory(v -> new BounceInUpTransition(v));


    }
}


// To samo co kontroler. Dla widoku Desktopowego