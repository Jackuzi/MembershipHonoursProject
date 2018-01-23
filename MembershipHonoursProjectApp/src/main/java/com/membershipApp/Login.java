package com.membershipApp;

import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.plugins.LifecycleService;
import javafx.scene.control.ButtonType;
import javafx.util.Pair;
import org.controlsfx.dialog.LoginDialog;

import java.util.Optional;

public class Login {
  private LoginDialog dlg;


  public Login() {
  }

  public void getLogin() {
    dlg = new LoginDialog(null, null);
    Optional<Pair<String, String>> result = dlg.showAndWait();
    dlg.setResultConverter(dialogButton -> {
      if (dlg.getDialogPane().lookupButton(ButtonType.CANCEL).isPressed()) {
        Pair<String, String> user = new Pair<>(result.get().getValue(), result.get().getKey());
        System.out.println(user);
        System.out.println("here");
        return user;

      } else if (dlg.getDialogPane().getButtonTypes().get(0).getButtonData().isDefaultButton()) {
        System.out.println("here2");
        Services.get(LifecycleService.class).ifPresent(LifecycleService::shutdown);

      }
      return null;
    });
    //dlg.showAndWait();
    // if (result.equals("kamil cyganiak")) {
    //System.out.println(result.toString());
    // }
    //Object initialUserInfo;
    // Object authenticator;
    //dlg.getDialogPane().getButtonTypes().get(0);
    //System.out.println(dlg.getDialogPane().getButtonTypes().get(1));
    //System.out.println(result.toString());
  }


  public void loggedUser() {
    // get logged user from database
  }
}
