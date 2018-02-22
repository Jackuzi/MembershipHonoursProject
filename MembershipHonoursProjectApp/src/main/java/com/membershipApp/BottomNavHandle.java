package com.membershipApp;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.membershipApp.views.ManagePresenter;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static com.membershipApp.MembershipAppMain.MANAGE_VIEW;
import static com.membershipApp.MembershipAppMain.MEMBERSHIP_VIEW;

public class BottomNavHandle {
  private BottomNavigationButton btn0;
  private BottomNavigationButton btn1;
  private BottomNavigationButton btn3;
  private BottomNavigationButton btn4;
  private BottomNavigationButton btn2;
  private ManagePresenter mp = new ManagePresenter();
  private Label controlsText = new Label();
  private Label customersText = new Label();


  public BottomNavigation getBottomNavigation() {
    return bottomNavigation;
  }

  private BottomNavigation bottomNavigation;


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
    setBottomNavigation(bottomNavigation);
    loadLogin();
    loadManage();
    loadMembership();
    loadReminder();
    //Home View
    loadSettings();
    bottomNavigation.getActionItems().
            addAll(btn0, btn3, btn1, btn2, btn4);
    return bottomNavigation;
  }

  private void loadSettings() {
    btn4.setOnAction((event) ->
    {
      Task<Void> settingsTask = new Task<Void>() {
        @Override
        protected Void call() {
          // Button was clicked, do something...
          MobileApplication.getInstance().switchView(MembershipAppMain.SETTINGS_VIEW, ViewStackPolicy.SKIP);
          MobileApplication.getInstance().getView().setBottom(bottomNavigation);
          btn4.setSelected(true);
          MobileApplication.getInstance().getAppBar().setVisible(false);
          return null;
        }
      };
      new Thread(settingsTask).run();
    });
  }

  private void loadLogin() {
    btn0.setOnAction((event) -> {
      Task<Void> loginTask = new Task<Void>() {
        @Override
        protected Void call() {
          // Button was clicked, do something...
          //MobileApplication.getInstance().switchView(MembershipAppMain.HOME_VIEW);
          //MobileApplication.getInstance().getView().setTop(bottomNavigation);
          MobileApplication.getInstance().switchView(MembershipAppMain.LOGIN_VIEW, ViewStackPolicy.SKIP);
          btn0.setSelected(true);
          Button logoutButton = new Button();
          logoutButton.setText("Log out");
          MobileApplication.getInstance().getAppBar().getActionItems().add(logoutButton);
          // MobileApplication.getInstance().getAppBar().setVisible(false);
          return null;
        }
      };
      new Thread(loginTask).run();
    });
  }

  private void loadManage() {
    //Manage View
    btn1.setOnAction((event) -> {
      MobileApplication.getInstance().viewProperty().addListener((observable) ->
      {
        if (MobileApplication.getInstance().getView().getName().equals(MANAGE_VIEW) && ((!Platform.isDesktop()) || ((MobileApplication.getInstance().getScreenHeight() < 820) && (MobileApplication.getInstance().getScreenWidth() < 1000)))) {
          AppBar appBar = MobileApplication.getInstance().getAppBar();
          appBar.clear();
          Label controlsText = new Label();
          Label customersText = new Label();
          controlsText.setText("Controls");
          customersText.setText("Customers");
          appBar.setTitleText(customersText.getText());
          appBar.setNavIcon(MaterialDesignIcon.MENU.button((e) -> {
            MobileApplication.getInstance().showLayer("employeeTable");
            //System.out.println("hello");
            //MaterialDesignIcon.SEARCH.button(e -> System.out.println("search")),
            //MaterialDesignIcon.MENU.button(e -> MobileApplication.getInstance().showLayer("employeeTable")));
          }));
          appBar.getActionItems().add(controlsText);
          appBar.getActionItems().add(MaterialDesignIcon.MENU.button(event1 -> MobileApplication.getInstance().showLayer(("buttonLayer"))));
        } else {
          MobileApplication.getInstance().getAppBar().clear();
          //MobileApplication.getInstance().getAppBar().setVisible(false);
        }
      });
      Task<Void> manageTask = new Task<Void>() {
        @Override
        protected Void call() {
          // Button was clicked, do something...
          MobileApplication.getInstance().switchView(MANAGE_VIEW, ViewStackPolicy.SKIP);
          MobileApplication.getInstance().getView().setBottom(bottomNavigation);
          btn1.setSelected(true);
          return null;
        }
      };
      new Thread(manageTask).run();
    });
  }

  private void loadReminder() {
    //Reminder View
    btn3.setOnAction((event) ->
    {
      Task<Void> reminderTask = new Task<Void>() {
        @Override
        protected Void call() {
          // Button was clicked, do something...
          MobileApplication.getInstance().switchView(MembershipAppMain.REMINDER_VIEW, ViewStackPolicy.SKIP);
          MobileApplication.getInstance().getView().setBottom(bottomNavigation);
          btn3.setSelected(true);
          MobileApplication.getInstance().getAppBar().setVisible(false);
          return null;
        }
      };
      new Thread(reminderTask).run();
    });
  }

  private void loadMembership() {
    //membership view
    btn2.setOnAction((event) -> {
      MobileApplication.getInstance().viewProperty().addListener((observable) ->
      {
        if (MobileApplication.getInstance().getView().getName().equals(MEMBERSHIP_VIEW) && ((!Platform.isDesktop()) || ((MobileApplication.getInstance().getScreenHeight() < 820) && (MobileApplication.getInstance().getScreenWidth() < 1000)))) {
          AppBar appBar = MobileApplication.getInstance().getAppBar();
          appBar.clear();
          Label controlsText = new Label();
          //Label customersText = new Label();
          controlsText.setText("Controls");
          appBar.getActionItems().add(controlsText);
          appBar.getActionItems().add(MaterialDesignIcon.MENU.button(event1 -> MobileApplication.getInstance().showLayer(("membershipButtonsLayer"))));
        } else {
          MobileApplication.getInstance().getAppBar().clear();
          //MobileApplication.getInstance().getAppBar().setVisible(false);
        }
      });
      Task<Void> membershipTask = new Task<Void>() {
        @Override
        protected Void call() {
          MobileApplication.getInstance().switchView(MembershipAppMain.MEMBERSHIP_VIEW, ViewStackPolicy.SKIP);
          MobileApplication.getInstance().getView().setBottom(bottomNavigation);
          btn2.setSelected(true);
          //MobileApplication.getInstance().getAppBar().setVisible(true);
          return null;
        }
      };
      new Thread(membershipTask).run();
    });

  }


  public void setBottomNavigation(BottomNavigation bottomNavigation) {
    this.bottomNavigation = bottomNavigation;
  }
}
