package com.membershipApp;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.application.ViewStackPolicy;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.membershipApp.views.MembershipPresenter;
import com.membershipApp.views.ReminderPresenter;
import com.membershipApp.views.SettingsPresenter;
import javafx.concurrent.Task;
import javafx.scene.control.Button;

import static com.membershipApp.MembershipAppMain.MANAGE_VIEW;
import static com.membershipApp.MembershipAppMain.MEMBERSHIP_VIEW;

public class BottomNavHandle {
  private BottomNavigationButton btn0;
  private BottomNavigationButton btn1;
  private BottomNavigationButton btn3;
  private BottomNavigationButton btn4;
  private BottomNavigationButton btn2;
  private BottomNavigation bottomNavigation;
  private ReminderPresenter rp = new ReminderPresenter();
  private MembershipPresenter mp = new MembershipPresenter();
  private SettingsPresenter sp = new SettingsPresenter();
 

  public BottomNavHandle() {
    this.btn0 = new BottomNavigationButton("", MaterialDesignIcon.HOME.graphic());
    this.btn0.setSelected(true);
    this.btn1 = new BottomNavigationButton("", MaterialDesignIcon.PEOPLE.graphic());
    this.btn4 = new BottomNavigationButton("", MaterialDesignIcon.SETTINGS.graphic());
    this.btn3 = new BottomNavigationButton("", MaterialDesignIcon.ACCESS_TIME.graphic());
    this.btn2 = new BottomNavigationButton("", MaterialDesignIcon.CARD_MEMBERSHIP.graphic());

  }

  public BottomNavigation createBottomNavigation() {
    BottomNavigation bottomNavigation = new BottomNavigation();
    setBottomNavigation(bottomNavigation);
    loadLogin();
    loadManage();
    loadMembership();
    loadReminder();
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
          MobileApplication.getInstance().getAppBar().setVisible(true);
          // Button was clicked, do something...
          MobileApplication.getInstance().switchView(MembershipAppMain.SETTINGS_VIEW, ViewStackPolicy.SKIP);
          MobileApplication.getInstance().getAppBar().getActionItems().add(0, MaterialDesignIcon.SEARCH.button(event1 -> sp.search()));
          MobileApplication.getInstance().getAppBar().setTitleText("SETTINGS");
          MobileApplication.getInstance().getView().setBottom(bottomNavigation);
          btn4.setSelected(true);
          if ((MobileApplication.getInstance().getScreenHeight() > 800) || (MobileApplication.getInstance().getScreenWidth() > 1000)) {
            MobileApplication.getInstance().getAppBar().setVisible(true);

          }
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
          MobileApplication.getInstance().switchView(MembershipAppMain.LOGIN_VIEW, ViewStackPolicy.SKIP);
          MobileApplication.getInstance().getAppBar().setTitleText("HOME");
          btn0.setSelected(true);
          if (MobileApplication.getInstance().getAppBar().getActionItems().isEmpty()) {
            Button logoutButton = new Button();
            //logging out
            logoutButton.setOnAction(event1 -> {
              System.out.println("Trying to logout");
              MembershipAppMain.loggedUser.clear();
              MembershipAppMain.getInstance().getAppBar().clear();
              System.out.println(MembershipAppMain.loggedUser.get("user"));
              bottomNavigation.setVisible(false);
            });
            logoutButton.setText("Log out");
            MobileApplication.getInstance().getAppBar().getActionItems().add(logoutButton);
            bottomNavigation.setVisible(true);
          }
          if (!MembershipAppMain.loggedUser.isEmpty()) {
            MobileApplication.getInstance().getAppBar().setVisible(true);
            MobileApplication.getInstance().getView().setBottom(bottomNavigation);
            System.out.println(MembershipAppMain.loggedUser.get("user"));
            System.out.println("hello2");
          }
          if (!MembershipAppMain.loggedUser.get("user").equals("Admin")) {
            btn4.setDisable(true);
          }
          return null;
        }
      };
      new Thread(loginTask).run();
    });
  }

  private void loadManage() {
    //Manage View
    btn1.setOnAction((event) -> {
      MobileApplication.getInstance().getAppBar().setTitleText("MANAGE");
      MobileApplication.getInstance().viewProperty().addListener((observable) ->
      {
        if (MobileApplication.getInstance().getView().getName().equals(MANAGE_VIEW) && ((!Platform.isDesktop()) || ((MobileApplication.getInstance().getScreenHeight() < 820) && (MobileApplication.getInstance().getScreenWidth() < 1000)))) {
          AppBar appBar = MobileApplication.getInstance().getAppBar();
          appBar.clear();
          // Label controlsText = new Label();
          // Label customersText = new Label();
          //controlsText.setText("Controls");
          //customersText.setText("Customers");
          //appBar.setTitleText(customersText.getText());
          appBar.setNavIcon(MaterialDesignIcon.MENU.button((e) -> {
            MobileApplication.getInstance().showLayer("employeeTable");
            //System.out.println("hello");
            //MaterialDesignIcon.SEARCH.button(e -> System.out.println("search")),
            //MaterialDesignIcon.MENU.button(e -> MobileApplication.getInstance().showLayer("employeeTable")));
          }));
          //appBar.getActionItems().add(controlsText);
          appBar.getActionItems().add(MaterialDesignIcon.MENU.button(event1 -> MobileApplication.getInstance().showLayer(("buttonLayer"))));
        } else {
          // MobileApplication.getInstance().getAppBar().clear();
          MobileApplication.getInstance().getAppBar().setVisible(true);
        }
      });
      Task<Void> manageTask = new Task<Void>() {
        @Override
        protected Void call() {
          // Button was clicked, do something...
          MobileApplication.getInstance().switchView(MANAGE_VIEW, ViewStackPolicy.SKIP);
          MobileApplication.getInstance().getAppBar().setTitleText("MANAGE");
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
          MobileApplication.getInstance().getAppBar().setVisible(true);
          MobileApplication.getInstance().getAppBar().setTitleText("REMINDER");
          MobileApplication.getInstance().getAppBar().getActionItems().add(MaterialDesignIcon.SEARCH.button(event1 -> rp.search()));
          btn3.setSelected(true);
          //test
         /* TextField search = new TextField();
          search.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
          });
          MobileApplication.getInstance().getAppBar().getActionItems().add(search);*/
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
          // Label controlsText = new Label();
          //Label customersText = new Label();
          //controlsText.setText("Controls");
          //appBar.getActionItems().add(controlsText);
          appBar.getActionItems().add(MaterialDesignIcon.MENU.button(event1 -> MobileApplication.getInstance().showLayer(("membershipButtonsLayer"))));
        } else {
          //MobileApplication.getInstance().getAppBar().clear();
          MobileApplication.getInstance().getAppBar().setVisible(true);
        }
      });
      Task<Void> membershipTask = new Task<Void>() {
        @Override
        protected Void call() {
          MobileApplication.getInstance().switchView(MembershipAppMain.MEMBERSHIP_VIEW, ViewStackPolicy.SKIP);
          // MobileApplication.getInstance().getAppBar().getActionItems().add(MaterialDesignIcon.SEARCH.button());
          MobileApplication.getInstance().getAppBar().setTitleText("MEMBERSHIP");
          MobileApplication.getInstance().getView().setBottom(bottomNavigation);
          btn2.setSelected(true);
          //MobileApplication.getInstance().getAppBar().setVisible(true);
          return null;
        }
      };
      new Thread(membershipTask).run();
    });

  }


  private void setBottomNavigation(BottomNavigation bottomNavigation) {
    this.bottomNavigation = bottomNavigation;
  }


}
