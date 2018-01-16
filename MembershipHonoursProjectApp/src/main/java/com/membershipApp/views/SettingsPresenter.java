package com.membershipApp.views;

import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPresenter implements Initializable {

    @FXML
    private View settingsView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsView.setShowTransitionFactory(v -> new BounceInUpTransition(v));

    }
}
