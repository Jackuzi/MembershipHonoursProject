package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.MembershipAppMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SettingsPresenter extends GluonPresenter<MembershipAppMain> {

  @FXML
  private View settingsView;

  @FXML
  private TableView<?> employeeTable;

  @FXML
  private TableColumn<?, ?> idColumn;

  @FXML
  private TableColumn<?, ?> nameColumn;

  @FXML
  private TableColumn<?, ?> surnameColumn;

  @FXML
  private TableColumn<?, ?> emailColumn;

  @FXML
  private TableColumn<?, ?> passColumn;

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
  }

  @FXML
  void updateRecord(ActionEvent event) {
  }

  public void initialize() {
    settingsView.setCache(true);
    settingsView.setCacheShape(true);
    settingsView.setCacheHint(CacheHint.SPEED);
    settingsView.setShowTransitionFactory(v -> new BounceInUpTransition(v));

  }
}
