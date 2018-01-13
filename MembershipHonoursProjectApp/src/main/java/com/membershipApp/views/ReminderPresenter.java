package com.membershipApp.views;

import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ReminderPresenter implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private View reminderView;

    @FXML
    private VBox content;

    @FXML
    private CharmListView<?, ?> charmList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }


    @PostConstruct
    public void init() {



    }
}
