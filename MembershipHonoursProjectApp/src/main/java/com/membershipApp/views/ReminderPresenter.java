package com.membershipApp.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
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
    private View welcome;
    @FXML
    private BottomNavigationButton welcome1;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(welcome.getName());


    }

    @FXML
    void initManageLayer(ActionEvent event) {

        MobileApplication.getInstance().switchView(MembershipAppMain.MANAGE_VIEW);
        System.out.println("hello1");

    }


    @PostConstruct
    public void init() {
        System.out.println("init");


    }
}
