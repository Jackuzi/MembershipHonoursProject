package com.membershipApp.views;

import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MembershipPresenter implements Initializable {
    @FXML
    private View membershipView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        membershipView.setShowTransitionFactory(v -> new BounceInUpTransition(v));
    }
}
