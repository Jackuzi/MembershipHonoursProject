package com.membershipApp;

import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.CharmListView;
import com.gluonhq.charm.glisten.control.ListTile;
import com.membershipApp.views.ReminderPresenter;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MembershipCells extends CharmListCell<MemberModel> {


  private final ListTile tile;
  //private final ImageView imageView;
  private MemberModel item;
  private int click = 0;
  private final ImageView imageView;


  public MembershipCells(CharmListView listView, int option) {
    this.tile = new ListTile();
    //imageView = new ImageView();
    //tile.setPrimaryGraphic(imageView);
    imageView = new ImageView();
    tile.setPrimaryGraphic(imageView);
    setText(null);
    tile.setOnMouseClicked(e -> {
      System.out.println("Selected Item!!! -> " + item);
      listView.setSelectedItem(item);
      Platform.runLater(() -> tile.requestFocus());
      if (option == 2) {
        click++;
        System.out.println("click: " + click);
        if (click == 2) {
          click = 0;
          new ReminderPresenter().actionHandle(item);
        }
      }

    });

  }


  @Override
  public void updateItem(MemberModel item, boolean empty) {
    super.updateItem(item, empty);
    if (item != null && !empty) {
      this.item = item;
      tile.textProperty().setAll(item.getSurname().toUpperCase() + " " + item.getName() + " (" + item.getEmail() + ")",
              "City: " + item.getCity() + "    Postcode: " + item.getPostcode(),
              "Valid From: " + item.getdFrom() + "     To: " + item
                      .getdTo());
      final Image image = new Image(MembershipAppMain.class.getResourceAsStream("/man.png"));
      if (image != null) {
        imageView.setImage(image);
      }
      setGraphic(tile);
    } else {
      setGraphic(null);
    }
  }
}
