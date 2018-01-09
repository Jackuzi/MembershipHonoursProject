package com.membershipApp.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.layout.Layer;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.membershipApp.MembershipAppMain;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PrimaryPresenter {


    @FXML
    private View primary;

    @FXML
    private Layer manageLayer;

    @FXML
    private View welcomeView;

    @FXML
    private VBox content;

    @FXML
    private Label label;

    @FXML
    private View manageView;


    @FXML
    private VBox container;


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


}


// To samo co kontroler. Dla widoku Desktopowego