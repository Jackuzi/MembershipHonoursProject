package com.membershipApp;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

public class NotificationHandler {
//private String message = "";


    public void added(String message){
        Notifications.create().owner(MobileApplication.getInstance().getView()).position(Pos.BOTTOM_RIGHT).darkStyle().graphic(MaterialDesignIcon.ADD.graphic()).text(message).show();
    }

    public void removed(String message) {
        Notifications.create().owner(MobileApplication.getInstance().getView()).position(Pos.BOTTOM_RIGHT).darkStyle().graphic(MaterialDesignIcon.DELETE.graphic()).text(message).show();

    }

    public void updated(String message) {
        Notifications.create().owner(MobileApplication.getInstance().getView()).position(Pos.BOTTOM_RIGHT).darkStyle().text(message).graphic(MaterialDesignIcon.UPDATE.graphic()).show();

    }



}
