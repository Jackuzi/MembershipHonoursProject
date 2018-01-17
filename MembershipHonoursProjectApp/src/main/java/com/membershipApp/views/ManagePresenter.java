package com.membershipApp.views;

import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.DatePicker;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.DatabaseConnectionHandler;
import com.membershipApp.MemberModel;
import com.membershipApp.Members;
import com.membershipApp.NotificationHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
  private TableView<MemberModel> custTable;
  @FXML
  private TableColumn<MemberModel, Integer> idCol;
  @FXML
  private TableColumn<MemberModel, String> namCol;
  @FXML
  private TableColumn<MemberModel, String> surCol;
  @FXML
  private TextField countryField;

  private NotificationHandler nH;
  private String message;
  private String n, s, st, h, e, p, c, d, cou;
  private String t;
  private DatabaseConnectionHandler db;
  private Members members;
  private final ArrayList<TextField> textFields = new ArrayList<>();
  private DatePicker datePicker;
  private int id;

  public String getN() {
    n = nameField.getText();
    return n;
  }

  public String getCou() {
    cou = countryField.getText();
    return cou;
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

  public String getT() {
    t = String.valueOf(telField.getText());
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

  private int getMaxId() throws SQLException {
    Statement st2 = db.getConn().createStatement();
    ResultSet rs = st2.executeQuery("SELECT CUSTOMERID FROM CUSTOMER ");
    int id2 = 0;
    if (rs.next()) {
      id2 = rs.getInt("customerId");
    }
    System.out.println(id2);
    id = id2;
    return id;
  }

  @FXML
  void addMember(ActionEvent event) throws SQLException {
    id = getMaxId();
    System.out.println("add");
    if (isFieldEmpty()) {
      db.getConn();
      //members.getMemberData().add(new MemberModel(id, n, s, st, h, Integer.parseInt(t), e, p, c, d, cou));
      message = "Member added succesfully";
      db.getConn().close();
    } else if (!isFieldEmpty()) {
      message = "Please correct empty fields";

    }
    nH.added(message);
  }

  private boolean isFieldEmpty() {
    for (TextField textfield : textFields) {
      if ((textfield.getText().trim().isEmpty()) || (getD().equals("Date of Birth"))) {
        System.out.println("some field is empty");
        return false;
      }
    }
    return true;  // every field was empty (or else we'd have stopped earlier)
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
  private void showMembersInTable() {
    custTable.setItems(members.getMemberData());
    idCol.setCellValueFactory(
            new PropertyValueFactory<>("customerId"));
    namCol.setCellValueFactory(
            new PropertyValueFactory<>("name"));
    surCol.setCellValueFactory(
            new PropertyValueFactory<>("surname"));

  }

  @PostConstruct
  public void inti() {
    nH = new NotificationHandler();
    datePicker = new DatePicker();
    try {
      db = new DatabaseConnectionHandler();

    } catch (SQLException e1) {
      e1.printStackTrace();
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    manageView.setShowTransitionFactory(BounceInUpTransition::new);
    textFields.add(surField);
    textFields.add(nameField);
    textFields.add(streetField);
    textFields.add(postField);
    textFields.add(emailField);
    textFields.add(houseField);
    textFields.add(cityField);
    textFields.add(countryField);
    textFields.add(telField);
    try {
      members = new Members();
      members.retrieveData();
      showMembersInTable();
    } catch (SQLException e1) {
      e1.printStackTrace();
    }

  }

}