package com.membershipApp.views;

import com.gluonhq.charm.glisten.afterburner.GluonPresenter;
import com.gluonhq.charm.glisten.animation.BounceInUpTransition;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.mvc.View;
import com.membershipApp.MemberModel;
import com.membershipApp.Members;
import com.membershipApp.MembershipAppMain;
import com.membershipApp.MembershipCells;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MembershipPresenter extends GluonPresenter<MembershipAppMain> {
  @FXML
  private View membershipView;
  @FXML
  private StackPane buttonsStackPane;

  @FXML
  private CharmListView<MemberModel, Comparable> membershipList;
  private Members members = new Members();
  private ObservableList<MemberModel> mem = members.getMemberData();


  public void initialize() {
    //System.out.println(member.getName());
    membershipView.setShowTransitionFactory(v -> new BounceInUpTransition(v));
    members.retrieveData(0);
    membershipList = new CharmListView();
    membershipList.setItems(mem);
    membershipView.setCenter(membershipList);
    membershipList.setCellFactory(p -> new MembershipCells());
    System.out.println("here2");

  }
}