package com.membershipApp.views;

import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.DatePicker;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.MemberModel;
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
    private TextField postField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField houseField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField telField;

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
    private String n;
    private String s;

    public String getN() {
        n = nameField.getText();
        return n;
    }

    public String getS() {
        s = surField.getText();
        return s;
    }

    public String getSt() {
        st = streetField.getText();
        return st;
    }

    public String getH() {
        h = houseField.getText();
        return h;
    }

    public int getT() {
        t = Integer.parseInt(telField.getText());
        return t;
    }

    public String getE() {
        e = emailField.getText();
        return e;
    }

    public String getP() {
        p = postField.getText();
        return p;
    }

    public String getC() {
        c = cityField.getText();
        return c;
    }

    public String getD() {
        d = dobLabel.getText();
        return d;
    }

    private String st;
    private String h;
    private int t = 0;
    private String e;
    private String p;
    private String c;
    private String d;
    private MemberModel mm;


    @FXML
    void addMember(ActionEvent event) {

        System.out.println("add");

        message = "Member added succesfully";

        //isFieldEmpty();
        new MemberModel(n, s, st, h, t, e, p, c, d);


        nH.added(message);


    }

    private boolean isFieldEmpty() {

        if (n.isEmpty() || s.isEmpty() || st.isEmpty() || h.isEmpty() || (t == 0) || e.isEmpty()
                || p.isEmpty() || c.isEmpty() || d.isEmpty()) {

            System.out.println("Some field is empty");
        }


        return false;
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

        //if ok
        message = "Record removed";
        nH.removed(message);
    }

    @FXML
    void updateMember(ActionEvent event) {
        System.out.println("update");

        //if ok
        message = "updated successfully";
        nH.updated(message);
    }

    @FXML
    void chooseDate() {
        DatePicker datePicker = new DatePicker();
        datePicker.showAndWait().ifPresent(date -> dobLabel.setText(String.valueOf(date)));

    }

    /*  private void retrieveDbImage () throws SQLException {
          ResultSet rs = st.executeQuery(q);
          byte byteImage[];
          Blob blob;
          while(rs.next()){
              blob = rs.getBlob("IMG");
              byteImage = blob.getBytes(1,(int)blob.length());
          }
          Image img = new Image(new ByteArrayInputStream(byteImage));
          custAvatarHolder.setImage(img);
      }
  */
    @PostConstruct
    public void inti() {
        nH = new NotificationHandler();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(MobileApplication.getInstance().getView().getName());
        manageView.setShowTransitionFactory(v -> new BounceInUpTransition(v));


    }
}