package com.membershipApp;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.membershipApp.views.ManagePresenter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BottomNavHandle {
  private BottomNavigationButton btn0;
  private BottomNavigationButton btn1;
  private BottomNavigationButton btn3;
  private BottomNavigationButton btn4;
  private BottomNavigationButton btn2;
  private ManagePresenter mp = new ManagePresenter();
  private Label controlsText = new Label();
  private Label customersText = new Label();


  public BottomNavHandle() {
    controlsText.setText("Controls");
    customersText.setText("Customers");
    this.btn0 = new BottomNavigationButton("Home", MaterialDesignIcon.HOME.graphic());
    this.btn0.setSelected(true);
    this.btn1 = new BottomNavigationButton("Manage", MaterialDesignIcon.PEOPLE.graphic());
    this.btn4 = new BottomNavigationButton("Settings", MaterialDesignIcon.SETTINGS.graphic());
    this.btn3 = new BottomNavigationButton("Reminder", MaterialDesignIcon.ACCESS_TIME.graphic());
    this.btn2 = new BottomNavigationButton("Membership", MaterialDesignIcon.CARD_MEMBERSHIP.graphic());
  }

  public BottomNavigation createBottomNavigation() {
    BottomNavigation bottomNavigation = new BottomNavigation();
    //Home View
    btn0.setOnAction((event) -> {
      // Button was clicked, do something...
      //MobileApplication.getInstance().switchView(MembershipAppMain.HOME_VIEW);
      //MobileApplication.getInstance().getView().setTop(bottomNavigation);
      MobileApplication.getInstance().switchView(MembershipAppMain.LOGIN_VIEW, ViewStackPolicy.SKIP);
      btn0.setSelected(true);
      Button logoutButton = new Button();
      logoutButton.setText("Log out");
      MobileApplication.getInstance().getAppBar().getActionItems().add(logoutButton);
      // MobileApplication.getInstance().getAppBar().setVisible(false);
    });
    //Manage View
    btn1.setOnAction((event) -> {
      // Button was clicked, do something...
      MobileApplication.getInstance().switchView(MembershipAppMain.MANAGE_VIEW, ViewStackPolicy.SKIP);
      MobileApplication.getInstance().getView().setBottom(bottomNavigation);
      btn1.setSelected(true);
      //MobileApplication.getInstance().getAppBar().setNavIcon(MaterialDesignIcon.MENU.button());
     /* try {
        // if (!Platform.isDesktop()) {
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
      } catch (Exception e) {
        e.printStackTrace();
      }*/
      //Membership View
    });
    btn2.setOnAction((event) -> {
      // Button was clicked, do something...
      MobileApplication.getInstance().switchView(MembershipAppMain.MEMBERSHIP_VIEW, ViewStackPolicy.SKIP);
      MobileApplication.getInstance().getView().setBottom(bottomNavigation);
      btn2.setSelected(true);
      MobileApplication.getInstance().getAppBar().setVisible(false);


    });
    //Reminder View
    btn3.setOnAction((event) -> {
      // Button was clicked, do something...
      MobileApplication.getInstance().switchView(MembershipAppMain.REMINDER_VIEW, ViewStackPolicy.SKIP);
      MobileApplication.getInstance().getView().setBottom(bottomNavigation);
      btn3.setSelected(true);
      MobileApplication.getInstance().getAppBar().setVisible(false);

    });
    //Settings View
    btn4.setOnAction((event) -> {
      // Button was clicked, do something...
      MobileApplication.getInstance().switchView(MembershipAppMain.SETTINGS_VIEW, ViewStackPolicy.SKIP);
      MobileApplication.getInstance().getView().setBottom(bottomNavigation);
      btn4.setSelected(true);
      MobileApplication.getInstance().getAppBar().setVisible(false);

    });
    bottomNavigation.getActionItems().addAll(btn0, btn3, btn1, btn2, btn4);
    return bottomNavigation;
  }


}
