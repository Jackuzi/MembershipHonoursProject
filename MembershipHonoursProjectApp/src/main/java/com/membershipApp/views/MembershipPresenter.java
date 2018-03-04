package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.*;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.membershipApp.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;

public class MembershipPresenter extends GluonPresenter<MembershipAppMain> {
  @FXML
  private View membershipView;
  @FXML
  private VBox vboxPane;
  @FXML
  private VBox vboxSearch;
  @FXML
  private TextField search;
  @FXML
  private CharmListView<MemberModel, Comparable> membershipList;
  @FXML
  private Button refreshButton;
  private Members members = new Members();
  private ObservableList<MemberModel> mem = members.getMemberData();
  private DatePicker datePicker = new DatePicker();
  private DatabaseConnectionHandler db = new DatabaseConnectionHandler();
  private LocalDate dateFrom;
  private LocalDate dateTo;
  private CheckBox isCancelled = new CheckBox("Membership cancelled");
  private ListView innerList;
  private FilteredList<MemberModel> filteredList = new FilteredList<>(members.getMemberData(), data -> true);
  //private TextField searchField = new TextField();


  public void initialize() {
    membershipView.setShowTransitionFactory(BounceInUpTransition::new);
    membershipList.setOnPullToRefresh(event -> membershipList.refresh());
    if ((MobileApplication.getInstance().getScreenHeight() < 800) || (MobileApplication.getInstance().getScreenWidth() < 1000)) {
      initLayers();
    }
    filterList();
    Platform.runLater(() -> refreshButton.requestFocus());
  }

  private void filterList() {
    members.retrieveData(0);
    membershipList = new CharmListView<>(filteredList);
    membershipList.setCellFactory(pa -> new MembershipCells(membershipList, 1));
    mem.sort(Comparator.comparing(MemberModel::getExpiration, Comparator.nullsLast(Comparator.naturalOrder())));
    membershipView.setCenter(membershipList);
    innerList = (ListView) membershipList.lookup(".list-view");
    search.textProperty().addListener((observable, oldValue, newValue) -> {
      System.out.println("here");
      filteredList.setPredicate(data -> {
        System.out.println(data.getName());
        if (newValue == null || newValue.isEmpty()) {
          System.out.println("not found or empty");
          return true;
        }
        String lowerCaseSearch = newValue.toLowerCase();
        if (data.getName().toLowerCase().contains(lowerCaseSearch)) {
          System.out.println("found name");
          return true; // Filter matches first name.
        } else if (data.getSurname().toLowerCase().contains(lowerCaseSearch)) {
          System.out.println("found surname");
          return true; // Filter matches last name.
        }
        return false; // Does not match.
      });
    });


  }

  private void initLayers() {
    SidePopupView sidePopupView = new SidePopupView(vboxPane);
    sidePopupView.setSide(Side.RIGHT);
    MobileApplication.getInstance().getView().getLayers().add(new SidePopupView(vboxPane));
    MobileApplication.getInstance().addLayerFactory("membershipButtonsLayer", () -> new SidePopupView(vboxPane));
  }

  @SuppressWarnings("unchecked")
  @FXML
  void changeMembership() {
    innerList = (ListView) membershipList.lookup(".list-view");
    if (membershipList.getSelectedItem().getdFrom() != null && membershipList.getSelectedItem().getdTo() != null) {
      LocalDate dTo = LocalDate.parse(membershipList.getSelectedItem().getdFrom().toString()); //gives nullpointerexce
      LocalDate dFrom = LocalDate.parse(membershipList.getSelectedItem().getdTo().toString()); //nullpointerexce
      dateTo = dTo;
      dateFrom = dFrom;
    }
    //dateTo = membershipList.getSelectedItem().getdTo();
    //expired checkbox
    CheckBox expiration = new CheckBox("Expired in: " + membershipList.getSelectedItem().getExpiration() + " days");
    expiration.setDisable(true);
    expiration.setStyle("-fx-text-fill: #00ff01;");
    if ((membershipList.getSelectedItem().getExpiration() == null) || (Integer.valueOf(membershipList.getSelectedItem().getExpiration())) <= 0) {
      expiration.setSelected(true);
      expiration.setStyle("-fx-text-fill: #ff4100;");
    }
    if (innerList.getSelectionModel().isEmpty()) {
      MobileApplication.getInstance().showMessage("Nothing selected");
      //read membership dates from database
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
        datePicker.showAndWait().ifPresent(date -> validToButton.setText("Valid To: " + " " + date.toString()));
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
          dialog.hide();
          refreshList();
        } else {
          Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Membership is cancelled, to renew uncheck the checkbox");
          alert.showAndWait();
        }
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
    if (dateFrom != null && dateTo != null) {
      try {
        db.dbServerStart();
        PreparedStatement ps1 = db.getConn().prepareStatement(updateMembership);
        ps1.setInt(1, membershipList.getSelectedItem().getCustomerId());
        ps1.setString(2, dateFrom.toString());
        ps1.setString(3, dateTo.toString());
        ps1.setString(4, null);
        ps1.setInt(5, membershipList.getSelectedItem().getCustomerId());
        ps1.executeUpdate();
        ps1.close();
        db.getConn().close();
        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Membership updated");
        alert.showAndWait();
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        String message = "Membership Updated";
        MobileApplication.getInstance().showMessage(message);
      }
    } else {
      Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR, "Date cannot be null");
      alert.showAndWait();
    }
  }

  @FXML
  void cancelMembership() {
    innerList = (ListView) membershipList.lookup(".list-view");
    String cancelMembershipSql = "UPDATE PUBLIC.MEMBERSHIP SET CANCELLATIONDATE=? WHERE CUSTOMERID =?";
    try {
      if (innerList.getSelectionModel().isEmpty()) {
        MobileApplication.getInstance().showMessage("Nothing selected");
      } else if (membershipList.getSelectedItem().getCancelDate() == null) {
        db.dbServerStart();
        PreparedStatement ps = db.getConn().prepareStatement(cancelMembershipSql);
        ps.setString(1, datePicker.getDate().toString());
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
}