package com.membershipApp.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.MembershipAppMain;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PrimaryPresenter {

    @FXML
    private View primary;

    @FXML
    private VBox content;

    @FXML
    private Label label;

    @FXML
    private BottomNavigationButton welcome;

    @FXML
    private BottomNavigationButton manage;

    @FXML
    private BottomNavigationButton settings;

    @FXML
    private BottomNavigationButton membership;

    @FXML
    private BottomNavigationButton about;

    public void initialize() {
      /*
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
        */
    }

    @FXML
    void buttonClick() {
        label.setText("Hello JavaFX Universe!");
    }

    @FXML
    void initWelcomeLayer() {

        MobileApplication.getInstance().switchView(MembershipAppMain.WELCOME_VIEW);
        MobileApplication.getInstance().getAppBar().setVisible(false);

    }


}


// To samo co kontroler. Dla widoku Desktopowego