package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.Alert;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.DatePicker;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.membershipApp.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MembershipPresenter extends GluonPresenter<MembershipAppMain> {
  @FXML
  private View membershipView;
  @FXML
  private StackPane buttonsStackPane;

  @FXML
  private CharmListView<MemberModel, Comparable> membershipList;
  private Members members = new Members();
  private ObservableList<MemberModel> mem = members.getMemberData();
  private DatePicker datePicker = new DatePicker();
  private DatabaseConnectionHandler db = new DatabaseConnectionHandler();
  private LocalDate dateFrom;
  private LocalDate dateTo;
  private CheckBox isCancelled = new CheckBox("Membership cancelled");
  private ListView innerList;
  private boolean saved;


  public void initialize() {
    //System.out.println(member.getName());
    membershipView.setShowTransitionFactory(v -> new BounceInUpTransition(v));
    members.retrieveData(0);
    membershipList = new CharmListView();
    membershipList.setItems(mem);
    membershipView.setCenter(membershipList);
    membershipList.setCellFactory(p -> new MembershipCells());
    membershipList.setOnPullToRefresh(event -> {
      membershipList.refresh();
    });


  }


  @FXML
  void changeMembership(ActionEvent event) {
    innerList = (ListView) membershipList.lookup(".list-view");
    Date dTo = Date.valueOf(membershipList.getSelectedItem().getdFrom().toString());
    Date dFrom = Date.valueOf(membershipList.getSelectedItem().getdTo().toString());
    dateTo = dTo.toLocalDate();
    dateFrom = dFrom.toLocalDate();
    //dateTo = membershipList.getSelectedItem().getdTo();
    //expired checkbox
    CheckBox expiration = new CheckBox("Expired in: " + membershipList.getSelectedItem().getExpiration() + " days");
    expiration.setDisable(true);
    expiration.setStyle("-fx-text-fill: #00ff01;");
    if (membershipList.getSelectedItem().getExpiration() <= 0) {
      expiration.setSelected(true);
      expiration.setStyle("-fx-text-fill: #ff4100;");
    }
    if (innerList.getSelectionModel().isEmpty()) {
      MobileApplication.getInstance().showMessage("Nothing selected");
      //read membershipdates from database
    } else {
      VBox dialogContent = new VBox();
      Dialog dialog = new Dialog(true);
      Label custName = new Label(membershipList.getSelectedItem().getName() + " " + membershipList.getSelectedItem().getSurname());
      custName.setGraphic(MaterialDesignIcon.PERSON.graphic());
      custName.setId("custNameLabel");
      Button validFromButton = new Button("Valid From: " + membershipList.getSelectedItem().getdFrom());
      validFromButton.setGraphic(MaterialDesignIcon.DATE_RANGE.graphic());
      validFromButton.setOnAction(e -> {
        datePicker.showAndWait().ifPresent(date -> validFromButton.setText("Valid From: " + " " + date.toString()));
        System.out.println(datePicker.getDate());
        dateFrom = datePicker.getDate();
      });
      Button validToButton = new Button("Valid To: " + membershipList.getSelectedItem().getdTo());
      validToButton.setGraphic(MaterialDesignIcon.DATE_RANGE.graphic());
      validToButton.setOnAction(e -> {
        datePicker.showAndWait().ifPresent(date -> validToButton.setText("Valid From: " + " " + date.toString()));
        System.out.println(datePicker.getDate());
        dateTo = datePicker.getDate();
      });
      //dialog properties
      if (membershipList.getSelectedItem().getCancelDate() == null) {
        isCancelled.setSelected(false);
        isCancelled.setStyle("-fx-text-fill: #00ff01;");
      } else {
        isCancelled.setSelected(true);
        isCancelled.setStyle("-fx-text-fill: #ff4100;");
      }
      //checkbox listener
      isCancelled.setOnAction(event1 -> {
        if (isCancelled.isSelected()) {
          validFromButton.setDisable(true);
          validToButton.setDisable(true);
          isCancelled.setStyle("-fx-text-fill: #ff4100;");
        } else {
          validFromButton.setDisable(false);
          validToButton.setDisable(false);
          isCancelled.setStyle("-fx-text-fill: #00ff01;");

        }
      });
      dialogContent.setSpacing(50);
      dialogContent.alignmentProperty().set(Pos.CENTER);
      dialogContent.getChildren().addAll(custName, validFromButton, validToButton, isCancelled, expiration);
      dialog.setTitleText("Change/Renew Membership");
      dialog.setContent(dialogContent);
      //saving to database process
      Button saveButton = new Button("SAVE");
      saveButton.setOnAction(event3 -> {
        System.out.println("trying to save membership");
        if (!isCancelled.isSelected()) {
          saveToDatabase();
          Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Membership Updated");
          alert.showAndWait();
          dialog.hide();
          refreshList();

        } else MobileApplication.getInstance().showMessage("Membership is cancelled, to renew uncheck the checkbox");
      });
      dialog.getButtons().addAll(saveButton);
      dialog.showAndWait();
    }
  }

  public void refreshList() {
    members.getMemberData().clear();
    members.retrieveData(0);
    membershipList.refresh();
  }

  private void saveToDatabase() {
    String updateMembership = "UPDATE PUBLIC.MEMBERSHIP SET CUSTOMERID=?,DATEFROM=?,DATETO=?, CANCELLATIONDATE=? WHERE CUSTOMERID=?";
    //String updateMembership = "UPDATE TABLE PUBLIC.MEMBERSHIP SET DATETO =?";
    //String cancelMembership = "UPDATE TABLE PUBLIC.MEMBERSHIP SET EXPIRED=?";
    try {
      db.dbServerStart();
      PreparedStatement ps1 = db.getConn().prepareStatement(updateMembership);
      ps1.setInt(1, membershipList.getSelectedItem().getCustomerId());
      ps1.setDate(2, java.sql.Date.valueOf(dateFrom));
      ps1.setDate(3, java.sql.Date.valueOf(dateTo));
      ps1.setDate(4, null);
      ps1.setInt(5, membershipList.getSelectedItem().getCustomerId());
      ps1.executeUpdate();
      ps1.close();
      db.getConn().close();
    } catch (SQLException e) {
      e.printStackTrace();

    } finally {
      String message = "Membership Updated";
      MobileApplication.getInstance().showMessage(message);
    }
  }

  @FXML
  void cancelMembership(ActionEvent event) {
    innerList = (ListView) membershipList.lookup(".list-view");
    String cancelMembershipSql = "UPDATE PUBLIC.MEMBERSHIP SET CANCELLATIONDATE=? WHERE CUSTOMERID =?";
    try {
      // System.out.println(membershipList.getSelectedItem().getCancelDate());
      if (innerList.getSelectionModel().isEmpty()) {
        //System.out.println("Nothing selected");
        MobileApplication.getInstance().showMessage("Nothing selected");
      } else if (membershipList.getSelectedItem().getCancelDate() == null) {
        db.dbServerStart();
        PreparedStatement ps = db.getConn().prepareStatement(cancelMembershipSql);
        ps.setDate(1, java.sql.Date.valueOf(datePicker.getDate()));
        ps.setInt(2, membershipList.getSelectedItem().getCustomerId());
        ps.executeUpdate();
      } else {
        MobileApplication.getInstance().showMessage("Membership already cancelled");
        System.out.println("already cancelled");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      refreshList();
      Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Membership Cancelled. To renew select renew button");
      alert.showAndWait();
    }

  }


  @FXML
  void searchMembership(ActionEvent event) {
  }

  public boolean isSaved() {
    return saved;
  }

  public void setSaved(boolean saved) {
    this.saved = saved;
  }
}