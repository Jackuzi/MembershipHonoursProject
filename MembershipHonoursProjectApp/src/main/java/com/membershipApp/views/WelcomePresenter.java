package com.membershipApp.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePresenter implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private View welcome;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(welcome.getName());


    }

    @PostConstruct
    public void init() {
        System.out.println("init");

    }
}
