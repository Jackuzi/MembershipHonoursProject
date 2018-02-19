package com.membershipApp;

import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.SplashView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.charm.glisten.visual.Theme;
import com.membershipApp.views.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class MembershipAppMain extends MobileApplication {

  public static String PRIMARY_VIEW = "Primary View";
  public static String SECONDARY_VIEW = "Secondary View";
  public static final String MENU_LAYER = "Side Menu";
  public static String REMINDER_VIEW = "Reminder View";
  public static String MANAGE_VIEW = "Manage View";
  public static String SETTINGS_VIEW = "Settings View";
  public static String MEMBERSHIP_VIEW = "Membership View";
  public static String LOGIN_VIEW = HOME_VIEW;
  public static Scene scene;
  private SplashView splash = new SplashView();


  @Override
  public void init() {
    System.out.println("java version: " + System.getProperty("java.version"));
    System.out.println("javafx.version: " + System.getProperty("javafx.version"));
    showSplashScreen();
    //Login View
    addViewFactory(LOGIN_VIEW, () -> {
      View view = (View) new LoginView().getView();
      return view;
    });
    //Primary View if Desktop mainly navigation only
    //if (Platform.isDesktop()) {
    addViewFactory(PRIMARY_VIEW, () -> {
      PrimaryView pv = new PrimaryView();
      View v = (View) pv.getView();
      //Views navigation
      //view.setTop(new BottomNavHandle().createBottomNavigation());
      return v;
    });
    //if Mobile Secondary View
    // } else if (!Platform.isDesktop()) {
    //SECONDARY_VIEW = HOME_VIEW;
    //addViewFactory(SECONDARY_VIEW, () -> (View) new SecondaryView().getView());
    // }
    //Menu layer
    addLayerFactory(MENU_LAYER, () -> new SidePopupView(new DrawerManager().getDrawer()));
    //Reminder View
    addViewFactory(REMINDER_VIEW, () -> {
      ReminderView wv = new ReminderView();
      View v = (View) wv.getView();
      return v;
    });
    //Manage View
    addViewFactory(MANAGE_VIEW, () -> {
      ManageView wv = new ManageView();
      viewProperty().addListener((observable) ->
      {
        if (getView().getName().equals(MANAGE_VIEW) && ((!Platform.isDesktop()) || ((getScreenHeight() < 820) && (getScreenWidth() < 1000)))) {
          AppBar appBar = MobileApplication.getInstance().getAppBar();
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
        }
      });
      return (View) wv.getView();
    });
    //Settings View
    addViewFactory(SETTINGS_VIEW, () -> {
      SettingsView sv = new SettingsView();
      return (View) sv.getView();
    });
    //Membership View
    addViewFactory(MEMBERSHIP_VIEW, () -> {
      MembershipView mv = new MembershipView();
      return (View) mv.getView();
    });
  }

  private void showLoginScreen() {
  }


  // Will add loading on splashscreen , percentage or something
  private void showSplashScreen() {
    //MobileApplication.getInstance().switchView(LOGIN_VIEW);
    addViewFactory(MobileApplication.SPLASH_VIEW, () -> {
      ProgressIndicator pi = new ProgressIndicator();
      pi.setRadius(60);
      SplashView splashView = new SplashView(pi);
      splashView.setOnShown((LifecycleEvent e) -> {
        PauseTransition fadein = new PauseTransition(Duration.seconds(2));
        fadein.play();
        fadein.setOnFinished((ActionEvent a) -> {
          splashView.hideSplashView();
        });
      });
      return splashView;
    });
  }

  @Override
  public void postInit(Scene scene) {
    //getLogin();
    System.out.println(Platform.getCurrent().toString());
    MobileApplication.getInstance().getAppBar().setVisible(false);
    if (Platform.isAndroid()) {
    } else if ((getScreenHeight() > 800) && (getScreenWidth() > 1000) && (!Platform.isAndroid())) {
      System.out.println(MobileApplication.getInstance().getView().getName());
      scene.getWindow().setHeight(720);
      scene.getWindow().setWidth(1280);
    } else if (!Platform.isAndroid() || !Platform.isIOS()) {
      scene.getWindow().setHeight(800);
      scene.getWindow().setWidth(480);
    }
    //System.out.println(Platform.getCurrent().name());
    /*if ((getScreenHeight() < 500) && (getScreenWidth() < 900)) {
      scene.getWindow().setHeight(scene.getHeight());
      scene.getWindow().setWidth(scene.getWidth());

    }*/
    this.scene = scene;
    Swatch.TEAL.assignTo(scene);
    Theme.DARK.assignTo(scene);
    scene.getStylesheets().add(MembershipAppMain.class.getResource("style.css").toExternalForm());
    ((Stage) scene.getWindow()).getIcons().add(new Image(MembershipAppMain.class.getResourceAsStream("/icon.png")));
    //close request
    scene.getWindow().setOnCloseRequest((WindowEvent we) -> {
      System.out.println("closing");
      Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);
    });
    //scenicView to delete at final app
    //ScenicView.show(scene);
  }

  public SplashView getSplash() {
    return splash;
  }

  public void setSplash(SplashView splash) {
    this.splash = splash;
  }
}
