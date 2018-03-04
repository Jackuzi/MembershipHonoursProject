package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Alert;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
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

  private MemberModel mem;
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
    int count = 0;
    String columnLabel = null;
    if (option == 1) {
      columnLabel = "CURRENT_VALUE";

    } else if (option == 2) {
      columnLabel = "maxid";
    }
    Statement st2 = db.getConn().createStatement();
    try (ResultSet rs = st2.executeQuery(sqlSelect)) {
      //id2 = 0;
      while (rs.next()) {
        count = rs.getInt(columnLabel);
      }
    }
    System.out.println(count + 1);
    return count + 1;
  }

  @FXML
  void addMember(ActionEvent event) {
    String sql = null;
    String sqlMembership = null;
    try {
      db.dbServerStart();
      id = getMaxId("SELECT * FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA = 'PUBLIC' AND SEQUENCE_NAME = 'SYSTEM_SEQUENCE_9C07FDDC_36D2_4686_A895_13A1FA4370A6'", 1);
      addressId = getMaxId("SELECT MAX(ADDRESSID) as maxid FROM ADDRESS", 2);
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    System.out.println("add");
    if ((isFieldEmpty()) && (!isDuplicate(getN(), getS(), getE(), 0)) && (isEmailValid())) {
      //add to database
      if (newEmployeeCheckbox.isSelected()) {
        System.out.println("new employee ticked");
        sql = "INSERT INTO PUBLIC.EMPLOYEE(NAME, SURNAME, ADDRESSID, DOB, EMAIL, TELEPHONE) VALUES (?,?,?,?,?,?)";

      } else if (!newEmployeeCheckbox.isSelected()) {
        members.getMemberData().add(new MemberModel(id, getN(), getS(), getSt(), getH(), Long.valueOf(getT()), getE(), getP(), getC(), getD(), getCou(), null, null, null, null, null, 0));
        System.out.println("new customer");
        sql = "INSERT INTO PUBLIC.CUSTOMER(NAME, SURNAME, ADDRESSID, DOB, EMAIL, TELEPHONE) VALUES (?,?,?,?,?,?)";
        sqlMembership = "INSERT INTO PUBLIC.MEMBERSHIP (CUSTOMERID) VALUES (?)";
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
        if (newEmployeeCheckbox.isSelected()) {
          //  ps.setInt(7, addressId);
        }
        ps2.executeUpdate();
        ps.executeUpdate();
        if (!newEmployeeCheckbox.isSelected()) {
          PreparedStatement ps3 = db.getConn().prepareStatement(sqlMembership);
          ps3.setInt(1, id);
          ps3.executeUpdate();
          ps.close();
        }
        ps2.close();
        ps.close();

      } catch (SQLException e1) {
        e1.printStackTrace();

      } finally {
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Member added succesfully");
        alert.showAndWait();
        message = "Member added succesfully";
        MobileApplication.getInstance().showMessage(message);
        clearFields();
        members.getMemberData().clear();
        members.retrieveData(0);
        custTable.refresh();
      }

    } else if ((!isFieldEmpty())) {
      Alert alert = new Alert(javafx.scene.control.Alert.AlertType.WARNING, "Please correct empty fields");
      alert.showAndWait();
      message = "Please correct empty fields";
      //consider using that, check performance first
      MobileApplication.getInstance().showMessage(message);
    } else if (!isEmailValid()) {
      Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "Email validation failed");
      alert.showAndWait();
      message = " Email validation failed";
      MobileApplication.getInstance().showMessage(message);
    } else if (isDuplicate(getN(), getS(), getE(), 0)) {
      Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "User with same name and surname or email already exists in the database");
      alert.showAndWait();
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

  private boolean isDuplicate(String name, String surname, String email, int option) {
    if (option == 0) {
      for (MemberModel members : members.getMemberData()) {
        if (name.equals(members.getName()) && surname.equals(members.getSurname()) || email.equals(members.getEmail())) {
          System.out.println("duplicate");
          return true;
        }
      }
    } else if (option == 1) {
      for (MemberModel members : members.getMemberData()) {
        if (email.equals(members.getEmail())) {
          System.out.println("duplicate");
          return true;
        }
      }
    }
    return false;
  }

  @FXML
  void clearFields() {
    newEmployeeCheckbox.setSelected(false);
    newEmployeeCheckbox.setDisable(false);
    custTable.getSelectionModel().clearSelection();
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
    System.out.println("remove method");
    Alert alert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION, "Are you sure to delete this record?");
    Optional result = alert.showAndWait();
    if (result.isPresent() && result.get().equals(ButtonType.OK)) {
      mem = custTable.getSelectionModel().getSelectedItem();
      //if ok
      String delAddressSql = " DELETE FROM PUBLIC.ADDRESS WHERE ADDRESSID = ? ";
      try {
        db.dbServerStart();
        PreparedStatement ps2 = db.getConn().prepareStatement(delAddressSql);
        ps2.setInt(1, mem.getAddressId());
        ps2.executeUpdate();
        ps2.close();
        db.getConn().close();
      } catch (SQLException e) {
        e.printStackTrace();

      } finally {
        alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Record removed");
        alert.showAndWait();
        message = "Record removed";
        MobileApplication.getInstance().showMessage(message);
        clearFields();
        members.getMemberData().clear();
        members.retrieveData(0);
        custTable.refresh();
      }
    }
  }

  @FXML
  void updateMember(ActionEvent event) {
    System.out.println("update");
    //if ok
    mem = custTable.getSelectionModel().getSelectedItem();
    if (!custTable.getSelectionModel().isEmpty()) {
      String sql = "UPDATE PUBLIC.CUSTOMER SET NAME=?, SURNAME=?, DOB=?, EMAIL=?, TELEPHONE=?WHERE ADDRESSID=?";
      String sqlAddress = "UPDATE PUBLIC.ADDRESS SET HOUSENUMBER=?, STREET=?, CITY=?, POSTCODE=?, COUNTRY=? WHERE ADDRESSID=?";
      try {
        db.dbServerStart();
        PreparedStatement ps = db.getConn().prepareStatement(sql);
        PreparedStatement ps2 = db.getConn().prepareStatement(sqlAddress);
        ps.setString(1, getN());
        ps.setString(2, getS());
        ps.setString(3, getD());
        ps.setString(4, getE());
        ps.setLong(5, Long.valueOf(getT()));
        ps.setInt(6, mem.getAddressId());
        ps2.setString(1, getH());
        ps2.setString(2, getSt());
        ps2.setString(3, getC());
        ps2.setString(4, getP());
        ps2.setString(5, getCou());
        ps2.setInt(6, mem.getAddressId());
        // ps2.setInt(6, addressId);
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
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Update successfully");
        alert.showAndWait();
        message = "updated successfully";
      } catch (SQLException e) {
        e.printStackTrace();
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "Something went wrong. Update failed");
        alert.showAndWait();
        message = "Something went wrong. Update failed";
      } finally {
        clearFields();
        members.getMemberData().clear();
        members.retrieveData(0);
        custTable.refresh();
      }
      MobileApplication.getInstance().showMessage(message);
    }
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
    db = new DatabaseConnectionHandler();
  }

  public void initialize() {
    custAvatarHolder.setImage(new Image(MembershipAppMain.class.getResourceAsStream("/user.png")));
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
    if ((MobileApplication.getInstance().getScreenHeight() < 800) || (MobileApplication.getInstance().getScreenWidth() < 1000)) {
      initLayers();
    }

  }

  @FXML
  void tableListener() {
    newEmployeeCheckbox.setDisable(true);
    if (!custTable.getSelectionModel().isEmpty()) {
      mem = custTable.getSelectionModel().getSelectedItem();
      System.out.println("here");
      nameField.setText(mem.getName());
      //nameField.setFloatText("");
      surField.setText(mem.getSurname());
      if (mem.getDob() != null) {
        dobLabel.setText(mem.getDob().toString());
        //System.out.println(custTable.getDob().toLocalDate());
      } //else dobField.getEditor().clear();
      streetField.setText(mem.getStreet());
      cityField.setText(mem.getCity());
      postField.setText(mem.getPostcode());
      countryField.setText((mem.getCountry()));
      telField.setText(mem.getTel().toString());
      emailField.setText(mem.getEmail());
      houseField.setText(mem.getHouse());
      /*
      if (custTable.getProfile() != null && new File(custTable.getProfile()).isFile()) {
        Image image = new Image("file:///" + custTable.getProfile());
        imgField.setImage(image);
      } else if (custTable.getProfile() == null) {
        imgField.setImage(getDefaultImage());
      } else if (!new File(custTable.getProfile()).isFile()) {
        imgField.setImage(getDefaultImage());

      }*/
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

  private void addFieldFilter() {
    telField.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue.matches("[0-9]*")) {
        Platform.runLater(() -> telField.textProperty().set(oldValue));
      }
    });
  }
}