package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Avatar;
import com.gluonhq.charm.glisten.control.DatePicker;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.DatabaseConnectionHandler;
import com.membershipApp.MemberModel;
import com.membershipApp.Members;
import com.membershipApp.MembershipAppMain;
import emailvalidator4j.EmailValidator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.CacheHint;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.sql.PreparedStatement;
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
  //@FXML
  //private Layer tableLayer;
  @FXML
  private StackPane tableStack;
  @FXML
  private StackPane buttonStack;
  @FXML
  private VBox vboxMenu;
  @FXML
  private CheckBox newEmployeeCheckbox;


  private String message;
  private String n, s, st, h, e, p, c, d, cou;
  private String t;
  private DatabaseConnectionHandler db;
  private Members members;
  private final ArrayList<TextField> textFields = new ArrayList<>();
  private DatePicker datePicker = new DatePicker();
  private int id;
  private int addressId;

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

  private int getMaxId(String sqlSelect, int option) throws SQLException {
    String columnLabel = null;
    columnLabel = "maxid";
    Statement st2 = db.getConn().createStatement();
    try (ResultSet rs = st2.executeQuery(sqlSelect)) {
      //id2 = 0;
      while (rs.next()) {
        id = rs.getInt(columnLabel);
      }
    }
    System.out.println(id + 1);
    return id + 1;
  }

  @FXML
  void addMember(ActionEvent event) {
    String sql = null;
    try {
      db.dbServerStart();
      id = getMaxId("SELECT MAX(CUSTOMERID) as maxid FROM CUSTOMER ", 1);
      addressId = getMaxId("SELECT MAX(ADDRESSID) as maxid FROM ADDRESS", 2);
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    System.out.println("add");
    if ((isFieldEmpty()) && (!isDuplicate(getN(), getS(), getE())) && (isEmailValid())) {
      //add to database
      if (newEmployeeCheckbox.isSelected()) {
        System.out.println("new employee ticked");
        sql = "INSERT INTO PUBLIC.EMPLOYEE(NAME, SURNAME, ADDRESSID, DOB, EMAIL, TELEPHONE) VALUES (?,?,?,?,?,?)";

      } else if (!newEmployeeCheckbox.isSelected()) {
        members.getMemberData().add(new MemberModel(id, getN(), getS(), getSt(), getH(), Integer.parseInt(getT()), getE(), getP(), getC(), getD(), getCou(), null, null, null, false, null));
        message = "Member added succesfully";
        MobileApplication.getInstance().showMessage(message);
        System.out.println("new customer");
        sql = "INSERT INTO PUBLIC.CUSTOMER(NAME, SURNAME, ADDRESSID, DOB, EMAIL, TELEPHONE) VALUES (?,?,?,?,?,?)";
      }
      try {
        System.out.println(id + "  customerID/employeeID");
        System.out.println(addressId + "  addressID");
        String sqlAddress = "INSERT INTO PUBLIC.ADDRESS(HOUSENUMBER, STREET, CITY, POSTCODE, COUNTRY, ADDRESSID) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = db.getConn().prepareStatement(sql);
        PreparedStatement ps2 = db.getConn().prepareStatement(sqlAddress);
        ps.setString(1, getN());
        ps.setString(2, getS());
        ps.setInt(3, addressId);
        ps.setString(4, getD());
        ps.setString(5, getE());
        ps.setString(6, getT());
        ps2.setString(1, getH());
        ps2.setString(2, getSt());
        ps2.setString(3, getC());
        ps2.setString(4, getP());
        ps2.setString(5, getCou());
        ps2.setInt(6, addressId);
        if (!newEmployeeCheckbox.isSelected()) {
          // ps.setInt(7, id);
        }
        if (newEmployeeCheckbox.isSelected()) {
          //  ps.setInt(7, addressId);
        }
        ps2.executeUpdate();
        ps.executeUpdate();
        ps2.close();
        ps.close();

      } catch (SQLException e1) {
        e1.printStackTrace();

      }

    } else if ((!isFieldEmpty())) {
      message = "Please correct empty fields";
      //consider using that, check performance first
      MobileApplication.getInstance().showMessage(message);
    } else if (!isEmailValid()) {
      message = " Email validation failed";
      MobileApplication.getInstance().showMessage(message);
    } else if (isDuplicate(getN(), getS(), getE())) {
      message = "User with same name and surname or email already exists in the database";
      MobileApplication.getInstance().showMessage(message);
    }
    //nH.added(message);
    System.out.println("Server stopped");
    // db.getServer().stop();
  }

  private boolean isEmailValid() {
    String email = getE();
    boolean valid = false;
    EmailValidator validator = new EmailValidator();
    if (validator.isValid(email)) {
      System.out.println("Valid email");
      valid = true;
    } else {
      System.out.println("Invalid email");
    }
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
  void removeMember(ActionEvent event) {
    System.out.println("remove");
    //if ok
    message = "Record removed";
    MobileApplication.getInstance().showMessage(message);
  }

  @FXML
  void updateMember(ActionEvent event) {
    System.out.println("update");
    //if ok
    message = "updated successfully";
    MobileApplication.getInstance().showMessage(message);
  }

  @FXML
  void membershipProcess(ActionEvent event) {
    System.out.println("membership Process");

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
    //nH = new NotificationHandler();
    db = new DatabaseConnectionHandler();
    // MobileApplication.getInstance().getView().setOnShowing(event -> updateAppBar(MobileApplication.getInstance().getAppBar()));
    //System.out.println(MobileApplication.getInstance().getView().getName());
  }

  public void initialize() {
    Task t = new Task() {
      @Override
      protected Object call() throws Exception {
        manageView.setCache(true);
        manageView.setCacheShape(true);
        manageView.setCacheHint(CacheHint.QUALITY);
        manageView.setShowTransitionFactory(BounceInUpTransition::new);
        return null;
      }
    };
    t.run();
    textFieldsToArray();
    members = new Members();
    members.retrieveData(0);
    showMembersInTable();
    addFieldFilter();
    if (!com.gluonhq.charm.down.Platform.isDesktop()) {
      initLayers();
    }
  }


  private void textFieldsToArray() {
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
  }


  private void initLayers() {
    MobileApplication.getInstance().getView().getLayers().add(new SidePopupView(tableStack));
    MobileApplication.getInstance().addLayerFactory("employeeTable", () -> new SidePopupView(tableStack));
    MobileApplication.getInstance().getView().getLayers().add(new SidePopupView(vboxMenu));
    MobileApplication.getInstance().addLayerFactory("buttonLayer", () -> {
      SidePopupView sidePopupView = new SidePopupView(vboxMenu);
      sidePopupView.setSide(Side.RIGHT);
      return sidePopupView;
    });
  }
 /* private void initiateViewLayers() {
    //if (!com.gluonhq.charm.down.Platform.isDesktop()) {
    MobileApplication.getInstance().getView().getLayers().add(new SidePopupView(tableLayer));
    MobileApplication.getInstance().addLayerFactory("employeeTable", () -> {
      SidePopupView sidePopupView = new SidePopupView(tableLayer);
      return sidePopupView;
    });
    MobileApplication.getInstance().getView().getLayers().add(new SidePopupView(buttonStack));
    MobileApplication.getInstance().addLayerFactory("buttonLayer", () -> {
      SidePopupView sidePopupView = new SidePopupView(buttonStack);
      sidePopupView.setSide(Side.RIGHT);
      return sidePopupView;
    });
    Label controlsText = new Label();
    Label customersText = new Label();
    controlsText.setText("Controls");
    customersText.setText("Customers");
    MobileApplication.getInstance().getAppBar().setTitleText(customersText.getText());
    MobileApplication.getInstance().getAppBar().setNavIcon(MaterialDesignIcon.MENU.button((e) -> {
      MobileApplication.getInstance().showLayer("employeeTable");
      //System.out.println("hello");
      //MaterialDesignIcon.SEARCH.button(e -> System.out.println("search")),
      //MaterialDesignIcon.MENU.button(e -> MobileApplication.getInstance().showLayer("employeeTable")));
    }));
    MobileApplication.getInstance().getAppBar().getActionItems().add(controlsText);
    MobileApplication.getInstance().getAppBar().getActionItems().add(MaterialDesignIcon.MENU.button(event1 -> {
      MobileApplication.getInstance().showLayer(("buttonLayer"));
    }));
    // }
  }
*/

  private void addFieldFilter() {
    //telField.setErrorValidator(original -> new String(""));
    telField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("[0-9]*")) {
        Platform.runLater(() -> telField.textProperty().set(oldValue));
      }
      //System.out.println(oldValue + " " + newValue);
    });
  }
}