package com.membershipApp.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.layout.Layer;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagePresenter {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Layer manageLayer;

    @FXML
    void initialize() {
        System.out.println(MobileApplication.getInstance().getView().getName());
    }

    @PostConstruct
    public void inti() {

    }
}
