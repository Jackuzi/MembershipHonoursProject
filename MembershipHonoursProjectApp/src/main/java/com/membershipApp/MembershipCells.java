package com.membershipApp;

import com.gluonhq.charm.glisten.control.CharmListCell;
import com.gluonhq.charm.glisten.control.ListTile;

public class MembershipCells extends CharmListCell<MemberModel> {


  private final ListTile tile;
  //private final ImageView imageView;

  public MembershipCells() {
    this.tile = new ListTile();
    //imageView = new ImageView();
    //tile.setPrimaryGraphic(imageView);
    setText(null);
  }

  @Override
  public void updateItem(MemberModel item, boolean empty) {
    super.updateItem(item, empty);
    if (item != null && !empty) {
      tile.textProperty().setAll(item.getSurname().toUpperCase() + " " + item.getName() + " (" + item.getEmail() + ")",
              "City: " + item.getCity() + "    Postcode: " + item.getPostcode(),
              "Valid From: " + item.getdFrom() + "     To: " + item
                      .getdTo());
      // final Image image = USStates.getImage(item.getFlag());
      //   if (image != null) {
      //   imageView.setImage(image);
      //  }
      setGraphic(tile);
    } else {
      setGraphic(null);
    }
  }

}
