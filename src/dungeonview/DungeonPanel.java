package dungeonview;

import java.awt.*;
import java.util.Set;

import javax.swing.*;

import dungeonmodel.DungeonModel;
import structureddata.LocationDescription;
import structureddata.Position;

class DungeonPanel extends JPanel {

  //TODO change to read-only
  DungeonModel model;

  DungeonPanel() {
    this.model = null;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (int i = 0; i < model.getRows(); i++) {
      for (int j = 0; j < model.getColumns(); j++) {
        ImageIcon icon = new ImageIcon(Utilities.getImageName(model.getLocation(new Position(i, j)).getPossibleDirections()));
        g2d.drawImage(icon.getImage(), j * 64+100, i * 64+100, this);
      }
    }

    LocationDescription currentLocation = model.getCurrentLocation();
    int currentRow = currentLocation.getPosition().getRow();
    int currentColumn = currentLocation.getPosition().getColumn();
    System.out.println("Row: "+currentRow+", Column: "+currentColumn);
    g2d.setColor(Color.RED);
    g2d.fillOval( currentColumn*64+132 - 16/2, currentRow*64+132 - 16/2, 16, 16);
  }

  protected void setModel(DungeonModel model) {
    this.model = model;
  }
}
