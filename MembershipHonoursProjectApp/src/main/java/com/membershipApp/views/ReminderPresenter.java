package com.membershipApp.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.MembershipAppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ReminderPresenter implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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

    @FXML
    void initWelcomeLayer(ActionEvent event) {
        MobileApplication.getInstance().switchView(MembershipAppMain.REMINDER_VIEW);


    }

    @PostConstruct
    public void init() {
        System.out.println("init");


    }
}
