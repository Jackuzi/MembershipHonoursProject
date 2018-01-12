package com.membershipApp.views;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.DatePicker;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.NotificationHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagePresenter implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private View manageView;

    @FXML
    private Label custDetLabel;

    @FXML
    private Avatar custAvatarHolder;

    @FXML
    private TextField surField;

    @FXML
    private Label dobLabel;

    @FXML
    private TextField streetField;

    @FXML
    private TextField nameField;

    @FXML
    private TableView<?> custTable;

    @FXML
    private TableColumn<?, ?> namCol;

    @FXML
    private TableColumn<?, ?> surCol;

    @FXML
    private Button addBut;

    @FXML
    private Button remBut;

    @FXML
    private Button updBut;

    @FXML
    private Button memBut;

    @FXML
    private Button clearBut;

    private NotificationHandler nH;
    private String message;


    @FXML
    void addMember(ActionEvent event) {

System.out.println("add");

message = "Member added succesfully";
nH.added(message);



    }

    @FXML
    void clearFields(ActionEvent event) {
        System.out.println("clear");
    }

    @FXML
    void membershipProcess(ActionEvent event) {
        System.out.println("membership");
    }

    @FXML
    void removeMember(ActionEvent event) {
        System.out.println("remove");
    }

    @FXML
    void updateMember(ActionEvent event) {
        System.out.println("update");
    }
    @FXML
    void chooseDate(){
        DatePicker datePicker = new DatePicker();
        datePicker.showAndWait().ifPresent(date -> dobLabel.setText(String.valueOf(date)));

    }

    @PostConstruct
    public void inti() {
        nH = new NotificationHandler();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(MobileApplication.getInstance().getView().getName());

    }
}
