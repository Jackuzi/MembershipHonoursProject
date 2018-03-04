package com.membershipApp.views;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.Dialog;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.charm.glisten.layout.layer.PopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.membershipApp.MemberModel;
import com.membershipApp.Members;
import com.membershipApp.MembershipAppMain;
import com.membershipApp.MembershipCells;
import com.membershipApp.ReportGenertor.ReportGenerator;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ReminderPresenter extends GluonPresenter<MembershipAppMain> {
  @FXML
  private ResourceBundle resources;
  @FXML
  private URL location;
  @FXML
  private View reminderView;

  public CharmListView<MemberModel, Comparable> getReminderList() {
    return reminderList;
  }

  private CharmListView<MemberModel, Comparable> reminderList;
  private Members members = new Members();
  private ObservableList<MemberModel> mem = members.getMemberData();
  private ListView innerList;
  private FilteredList<MemberModel> filteredList;
  private TextField searchFieldOne = new TextField();


  public void initialize() {
    reminderView.setCache(true);
    reminderView.setCacheShape(true);
    reminderView.setCacheHint(CacheHint.SPEED);
    reminderView.setShowTransitionFactory(v -> new BounceInUpTransition(v));
    //reminderList = new CharmListView(mem);
    //myListView.setItems(filteredList);
    //filteredList = new FilteredList<>(mem, data -> true);
    filterList();
    // FloatingActionButton fab = new FloatingActionButton(MaterialDesignIcon.SEARCH.text, e -> search());
    // fab.setFloatingActionButtonHandler(FloatingActionButton.TOP_RIGHT);
    // reminderView.getLayers().add(fab.getLayer());
  }

  private void filterList() {
    members.retrieveData(0);
    reminderList = new CharmListView<>(mem);
    reminderList.setCellFactory(pa -> new MembershipCells(reminderList, 2));
    mem.sort(Comparator.comparing(MemberModel::getExpiration, Comparator.nullsLast(Comparator.naturalOrder())));
    reminderView.setCenter(reminderList);
  }

  @PostConstruct
  public void init() {
    System.out.println("init");
  }

  public void actionHandle(MemberModel item) {
    HBox hbox = new HBox();
    hbox.setSpacing(20);
    hbox.setAlignment(Pos.BASELINE_CENTER);
    Dialog dialog = new Dialog();
    dialog.setTitleText("Membership for: " + item.getName() + " " + item.getSurname() + " expires in: " + item.getExpiration());
    if (!Platform.isAndroid()) {
      hbox.getChildren().addAll(MaterialDesignIcon.PRINT.button(event -> printForm(item)), MaterialDesignIcon.EMAIL.button(event -> sendEmail
              (item)));
    } else if (Platform.isAndroid()) {
    }
    dialog.setContent(hbox);
    dialog.showAndWait();

  }

  private void sendEmail(MemberModel item) {
    //innerList = (ListView) reminderList.lookup(".list-view");
    System.out.println("send email " + item.getEmail());
    MobileApplication.getInstance().getHostServices().showDocument("mailto:" + item.getEmail());


  }

  private void printForm(MemberModel item) {
    if (item.getdTo() != null && item.getdFrom() != null) {
      new ReportGenerator().generatePdfReport(item);
    } else System.out.println("User has no membership");
  }

  public void search() {
    mem.retainAll();
    members.retrieveData(0);
    searchFieldOne.promptTextProperty().setValue("Search name");
    VBox vBox = new VBox();
    vBox.getChildren().addAll(searchFieldOne);
    vBox.setSpacing(5);
    vBox.setAlignment(Pos.CENTER);
    vBox.setMaxHeight(50);
    PopupView p = new PopupView(searchFieldOne);
    p.setContent(vBox);
    p.show();
    FilteredList<MemberModel> filteredList = new FilteredList<>(members.getMemberData(), data -> true);
    searchFieldOne.textProperty().addListener((observable, oldValue, newValue) -> {
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
    reminderList = new CharmListView<>(filteredList);
    mem.sort(Comparator.comparing(MemberModel::getExpiration, Comparator.nullsLast(Comparator.naturalOrder())));
    MobileApplication.getInstance().getView().setCenter(reminderList);
    reminderList.setCellFactory(pa -> new MembershipCells(reminderList, 2));
    innerList = (ListView) reminderList.lookup(".list-view");
  }
}
