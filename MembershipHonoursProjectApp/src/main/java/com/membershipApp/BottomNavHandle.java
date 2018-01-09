package com.membershipApp;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.BottomNavigation;
import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;

public class BottomNavHandle {
    private BottomNavigationButton btn0;
    private BottomNavigationButton btn1;
    private BottomNavigationButton btn3;
    private BottomNavigationButton btn4;
    private BottomNavigationButton btn2;

    public BottomNavHandle() {
        this.btn0 = new BottomNavigationButton("Home", MaterialDesignIcon.HOME.graphic());
        this.btn0.setSelected(true);

        this.btn1 = new BottomNavigationButton("Manage Members", MaterialDesignIcon.PEOPLE
                .graphic());
        this.btn4 = new BottomNavigationButton("Settings", MaterialDesignIcon.SETTINGS.graphic
                ());
        this.btn3 = new BottomNavigationButton("Reminder", MaterialDesignIcon.ACCESS_TIME.graphic
                ());
        this.btn2 = new BottomNavigationButton("Membership", MaterialDesignIcon.CARD_MEMBERSHIP.graphic());
    }

    public BottomNavigation createBottomNavigation() {


        BottomNavigation bottomNavigation = new BottomNavigation();


        //Home View

        btn0.setOnAction((event) -> {
            // Button was clicked, do something...
            MobileApplication.getInstance().switchView(MembershipAppMain.HOME_VIEW);

            MobileApplication.getInstance().getView().setTop(bottomNavigation);
            btn0.setSelected(true);
            MobileApplication.getInstance().getAppBar().setVisible(false);

        });

        //Manage View


        btn1.setOnAction((event) -> {
            // Button was clicked, do something...
            MobileApplication.getInstance().switchView(MembershipAppMain.MANAGE_VIEW);
            MobileApplication.getInstance().getView().setTop(bottomNavigation);
            btn1.setSelected(true);
            MobileApplication.getInstance().getAppBar().setVisible(false);

        });
        //Membership View

        btn2.setOnAction((event) -> {
            // Button was clicked, do something...
            MobileApplication.getInstance().switchView(MembershipAppMain.MEMBERSHIP_VIEW);
            MobileApplication.getInstance().getView().setTop(bottomNavigation);
            btn2.setSelected(true);
            MobileApplication.getInstance().getAppBar().setVisible(false);

        });

        //Reminder View

        btn3.setOnAction((event) -> {
            // Button was clicked, do something...
            MobileApplication.getInstance().switchView(MembershipAppMain.REMINDER_VIEW);
            MobileApplication.getInstance().getView().setTop(bottomNavigation);
            btn3.setSelected(true);
            MobileApplication.getInstance().getAppBar().setVisible(false);

        });
        //Settings View

        btn4.setOnAction((event) -> {
            // Button was clicked, do something...
            MobileApplication.getInstance().switchView(MembershipAppMain.SETTINGS_VIEW);
            MobileApplication.getInstance().getView().setTop(bottomNavigation);
            btn4.setSelected(true);
            MobileApplication.getInstance().getAppBar().setVisible(false);

        });


        bottomNavigation.getActionItems().addAll(btn0, btn3, btn1, btn2, btn4);

        return bottomNavigation;
    }


}
