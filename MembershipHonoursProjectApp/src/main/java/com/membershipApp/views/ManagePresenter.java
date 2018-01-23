package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.DatePicker;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.layout.Layer;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.apache.commons.validator.routines.EmailValidator;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ManagePresenter extends GluonPresenter<MembershipAppMain> {

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
  @FXML
  private Layer tableLayer;
  @FXML
  private StackPane tableStack;

  private NotificationHandler nH;
  private String message;
  private String n, s, st, h, e, p, c, d, cou;
  private String t;
  private DatabaseConnectionHandler db;
  private Members members;
  private final ArrayList<TextField> textFields = new ArrayList<>();
  private DatePicker datePicker = new DatePicker();
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
    int id2;
    try (ResultSet rs = st2.executeQuery("SELECT CUSTOMERID FROM CUSTOMER ")) {
      id2 = 0;
      if (rs.next()) {
        id2 = rs.getInt("customerId");
      }
    }
    System.out.println(id2);
    id = id2 + 1;
    return id;
  }

  @FXML
  void addMember(ActionEvent event) {
    try {
      db.dbServerStart();
      id = getMaxId();

    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    System.out.println("add");
    if ((isFieldEmpty()) && (!isDuplicate(getN(), getS(), getE())) && (isEmailValid())) {
      //db.getConn();
      members.getMemberData().add(new MemberModel(id, getN(), getS(), getSt(), getH(), Integer.parseInt(getT()), getE(), getP(), getC(), getD(), getCou()));
      message = "Member added succesfully";
    } else if ((!isFieldEmpty())) {
      message = "Please correct empty fields";
      //consider using that, check performance first
      MobileApplication.getInstance().showMessage("hello");
    } else if (!isEmailValid()) {
      message = " Email validation failed";
    } else if (isDuplicate(getN(), getS(), getE())) {
      message = "User with same name and surname or email already exists in the database";
    }
    //nH.added(message);
    System.out.println("Server stopped");
    // db.getServer().stop();
  }

  private boolean isEmailValid() {
    String email = getE();
    boolean valid = EmailValidator.getInstance().isValid(email);
    System.out.println(valid);
    return valid;
  }

  private boolean isFieldEmpty() {
    System.out.println("hello");
    for (TextField textfield : textFields) {
      if ((textfield.getText().trim().isEmpty()) || (getD().equals("Date of Birth"))) {
        System.out.println("empty field number");
        return false;
      }
    }
    return true;  // every field was empty (or else we'd have stopped earlier)
  }

  private boolean isDuplicate(String name, String surname, String email) {
    for (MemberModel members : members.getMemberData()) {
      if (name.equals(members.getName()) && surname.equals(members.getSurname()) || email.equals(members.getEmail())) {
        System.out.println("duplicate");
        return true;
      }
    }
    return false;
  }

  @FXML
  void clearFields(ActionEvent event) {
    System.out.println("clear");
    for (MemberModel member : members.getMemberData()) {
      System.out.println(member.toString());
    }
    for (TextField textfield : textFields) {
      textfield.setText("");
      dobLabel.setText("Date of Birth");
    }
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
  public void chooseDate() {
    datePicker.showAndWait().ifPresent(date -> dobLabel.setText(date.toString()));
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
  public void init() {
    nH = new NotificationHandler();
    db = new DatabaseConnectionHandler();


  }


  public void initialize() {
    Task t = new Task() {
      @Override
      protected Object call() throws Exception {
        manageView.setCache(true);
        manageView.setCacheShape(true);
        manageView.setCacheHint(CacheHint.SPEED);
        manageView.setShowTransitionFactory(node -> new BounceInUpTransition(node));
        return null;
      }
    };
    t.run();
    // t.cancel();
    textFields.add(surField);
    textFields.add(nameField);
    textFields.add(streetField);
    textFields.add(postField);
    textFields.add(emailField);
    textFields.add(houseField);
    textFields.add(cityField);
    textFields.add(countryField);
    textFields.add(telField);
    telField.textProperty().getValueSafe();
    members = new Members();
    members.retrieveData();
    showMembersInTable();
    addFieldFilter();
    //add table layer
    //new SidePopupView(tableLayer).hide();
    // MobileApplication.getInstance().getView().getLayers().add(new SidePopupView(tableLayer));
    MobileApplication.getInstance().getView().getLayers().add(new SidePopupView(tableStack));
    MobileApplication.getInstance().addLayerFactory("employeeTable", () -> {
      SidePopupView sidePopupView = new SidePopupView(tableStack);
      return sidePopupView;
    });
  }


  @FXML
  public void showHideTableLayer() {
    //MobileApplication.getInstance().showLayer("Menu");
    MobileApplication.getInstance().showMessage("hello");
  }

  private void addFieldFilter() {
    //telField.setErrorValidator(original -> new String(""));
    telField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("[0-9]*")) {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            telField.textProperty().set(oldValue);
            //telField.requestFocus();
            //telField.getErrorValidator();
          }
        });
      }
      System.out.println(oldValue + " " + newValue);
    });
  }
}