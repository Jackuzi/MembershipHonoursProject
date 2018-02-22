package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.DatabaseConnectionHandler;
import com.membershipApp.MemberModel;
import com.membershipApp.Members;
import com.membershipApp.MembershipAppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SettingsPresenter extends GluonPresenter<MembershipAppMain> {

  @FXML
  private View settingsView;

  @FXML
  private TableView<MemberModel> employeeTable;

  @FXML
  private TableColumn<MemberModel, Integer> idColumn;

  @FXML
  private TableColumn<MemberModel, String> nameColumn;

  @FXML
  private TableColumn<MemberModel, String> surnameColumn;

  @FXML
  private TableColumn<MemberModel, String> emailColumn;

  @FXML
  private TableColumn<MemberModel, String> passColumn;

  @FXML
  private Button showEmpBut;

  @FXML
  private Button updBut;

  @FXML
  private Button delBut;

  @FXML
  private Button cancelBut;

  @FXML
  void deleteRecord(ActionEvent event) {
  }

  @FXML
  void reloadTable(ActionEvent event) {
    members.getMemberData().retainAll();
    employeeTable.refresh();
    members.retrieveData(1);
  }

  @FXML
  void updateRecord(ActionEvent event) {
    employeeTable.requestFocus();
    for (MemberModel m : members.getMemberData()) {
      System.out.println(m.toString());
      //System.out.println(nameColumn.getS);
    }
    if (!employeeTable.getSelectionModel().isEmpty()) {
      DatabaseConnectionHandler db = new DatabaseConnectionHandler();
      String sql = "UPDATE PUBLIC.EMPLOYEE SET NAME = ?,SURNAME=?, EMAIL = ?, PASSWORD = ? WHERE EMPLOYEEID=?";
      try {
        db.dbServerStart();
        MemberModel mem = employeeTable.getSelectionModel().getSelectedItem();
        System.out.println(mem.getName());
        PreparedStatement ps = db.getConn().prepareStatement(sql);
        ps.setString(1, mem.getName());
        ps.setString(2, mem.getSurname());
        ps.setString(3, mem.getEmail());
        ps.setString(4, mem.getPassword());
        ps.setInt(5, mem.getCustomerId());
        ps.executeUpdate();
        ps.close();
        System.out.println(mem.getName() + " " + mem.getSurname() + " " + mem.getEmail());
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Record updated");
        alert.showAndWait();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        employeeTable.refresh();
      }

    }
  }

  private Members members = new Members();

  public void initialize() {
    settingsView.setCache(true);
    settingsView.setCacheShape(true);
    settingsView.setCacheHint(CacheHint.SPEED);
    settingsView.setShowTransitionFactory(v -> new BounceInUpTransition(v));
    members.retrieveData(1);//employee
    showEmployeesInTable();
  }

  private void showEmployeesInTable() {
    employeeTable.setItems(members.getMemberData());
    idColumn.setCellValueFactory(
            new PropertyValueFactory<>("customerId"));
    nameColumn.setCellValueFactory(
            new PropertyValueFactory<>("name"));
    //enable editing column
    nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    nameColumn.setOnEditCommit(
            event -> ((MemberModel) event.getTableView().getItems().get(
                    event.getTablePosition().getRow())
            ).setName(event.getNewValue()));
    // //enable editing column
    surnameColumn.setCellValueFactory(
            new PropertyValueFactory<>("surname"));
    surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    surnameColumn.setOnEditCommit(
            event -> ((MemberModel) event.getTableView().getItems().get(
                    event.getTablePosition().getRow())
            ).setSurname(event.getNewValue()));
    emailColumn.setCellValueFactory(
            new PropertyValueFactory<>("email"));
    //enable editing column
    emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    emailColumn.setOnEditCommit(
            event -> ((MemberModel) event.getTableView().getItems().get(
                    event.getTablePosition().getRow())
            ).setEmail(event.getNewValue()));
    passColumn.setCellValueFactory(
            new PropertyValueFactory<>("password"));
    //enable editing column
    passColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    passColumn.setOnEditCommit(
            event -> ((MemberModel) event.getTableView().getItems().get(
                    event.getTablePosition().getRow())
            ).setPassword(event.getNewValue()));


  }

}

