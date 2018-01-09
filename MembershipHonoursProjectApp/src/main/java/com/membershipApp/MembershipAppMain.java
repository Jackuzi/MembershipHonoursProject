package com.membershipApp;


import com.gluonhq.charm.down.Platform;
import com.gluonhq.charm.glisten.animation.FadeInLeftBigTransition;
import com.gluonhq.charm.glisten.animation.FadeOutRightBigTransition;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.LifecycleEvent;
import com.gluonhq.charm.glisten.control.ProgressIndicator;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.SplashView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.charm.glisten.visual.Theme;
import com.membershipApp.views.*;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.scenicview.ScenicView;

public class MembershipAppMain extends MobileApplication {

    public static String PRIMARY_VIEW = HOME_VIEW; //Home View
    public static String SECONDARY_VIEW = "Secondary View";
    public static final String MENU_LAYER = "Side Menu";
    public static String REMINDER_VIEW = "Reminder View";
    public static String MANAGE_VIEW = "Manage View";
    public static String SETTINGS_VIEW = "Settings View";
    public static String MEMBERSHIP_VIEW = "Membership View";


    @Override

    public void init() {
        //showSplashScreen();
        if (Platform.isDesktop()) {
            addViewFactory(PRIMARY_VIEW, () -> {
                View view = (View) new PrimaryView().getView();
                view.setTop(new BottomNavHandle().createBottomNavigation());
                return view;
            });
        } else if (!Platform.isDesktop()) {
            SECONDARY_VIEW = HOME_VIEW;
            addViewFactory(SECONDARY_VIEW, () -> {
                return (View) new SecondaryView().getView();
            });
        }

        addLayerFactory(MENU_LAYER, () -> {
            return new SidePopupView(new DrawerManager().getDrawer());
        });

        addViewFactory(REMINDER_VIEW, () -> {
            ReminderView wv = new ReminderView();
            return (View) wv.getView();
        });
        addViewFactory(MANAGE_VIEW, () -> {
            ManageView wv = new ManageView();
            return (View) wv.getView();
        });
        addViewFactory(SETTINGS_VIEW, () -> {
            SettingsView sv = new SettingsView();
            return (View) sv.getView();
        });
        addViewFactory(MEMBERSHIP_VIEW, () -> {
            MembershipView mv = new MembershipView();
            return (View) mv.getView();
        });


    }


    // Will add loading on splashscreen , percentage or something
    private void showSplashScreen() {
        addViewFactory(MobileApplication.SPLASH_VIEW, () -> {
            ProgressIndicator pi = new ProgressIndicator();
            pi.setRadius(60);
            SplashView splashView = new SplashView(pi);
            splashView.setOnShown((LifecycleEvent e) -> {
                FadeInLeftBigTransition fadein = new FadeInLeftBigTransition(splashView.getParent());
                fadein.play();
                fadein.setOnFinished((ActionEvent a) -> {
                    FadeOutRightBigTransition fadeout = new FadeOutRightBigTransition((splashView.getParent()));
                    fadeout.setDelay(Duration.seconds(3));
                    fadeout.play();
                    fadeout.setOnFinished(((ActionEvent f) -> {
                        splashView.hideSplashView();
                    }));
                });
            });
            return splashView;
        });
    }

    @Override
    public void postInit(Scene scene) {
        if (Platform.isDesktop()) {
            scene.getWindow().setHeight(800);
            scene.getWindow().setWidth(1280);
        }
        Swatch.INDIGO.assignTo(scene);
        Theme.DARK.assignTo(scene);

        scene.getStylesheets().add(MembershipAppMain.class.getResource("style.css").toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(MembershipAppMain.class.getResourceAsStream("/icon.png")));

        //scenicView to delete at final app
        ScenicView.show(scene);


    }


}