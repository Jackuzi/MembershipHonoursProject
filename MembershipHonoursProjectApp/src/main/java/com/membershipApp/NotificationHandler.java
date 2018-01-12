package com.membershipApp;

import com.gluonhq.charm.glisten.application.MobileApplication;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

public class NotificationHandler {

    public void added(String message){
        Notifications.create().owner(MobileApplication.getInstance().getView()).position(Pos.BOTTOM_RIGHT).darkStyle().text(message).show();
    }
}
