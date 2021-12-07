package dungeonview;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.*;

import dungeoncontroller.Features;
import dungeonmodel.Arrow;
import dungeonmodel.GameStatus;
import dungeonmodel.Item;
import dungeonmodel.ReadOnlyDungeonModel;
import dungeonmodel.Treasure;
import structureddata.PlayerDescription;

class PlayerPanel extends JPanel {
  DungeonSpringView view;
  JButton playAgain, killMonster;
  JTextArea textArea;
  String outputString;

  PlayerPanel(DungeonSpringView view) {
    this.view = view;
    outputString = "";
    this.setLayout(null);

    textArea = new JTextArea();
    textArea.setFont(new Font("default", Font.BOLD, 20));
    textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
    textArea.setBounds(350, 10, 450, 100);
    textArea.setLineWrap(true);
    this.add(textArea);

    playAgain = new JButton("Play Again");
    playAgain.setBounds(350, 120, 100, 50);
    this.add(playAgain);

    killMonster = new JButton("Kill Monster");
    killMonster.setBounds(350, 120, 100, 50);
    this.add(killMonster);
  }

  @Override
  protected void paintComponent(Graphics g) {
    if (view.getModel() == null) {
      throw new IllegalStateException("Model cannot be null when painting player description.");
    }

    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    List<Treasure> treasures = new ArrayList<>();
    treasures.add(Treasure.DIAMOND);
    treasures.add(Treasure.RUBY);
    treasures.add(Treasure.SAPPHIRE);

    ReadOnlyDungeonModel model = view.getModel();
    PlayerDescription player = model.getPlayerDescription();
    Map<Treasure, Integer> treasureMap = player.getCollectedTreasures();

    int i = 0;
    try {
      for (Treasure treasure : treasures) {
        BufferedImage image = ImageIO.read(new File(Utilities.getImageName(treasure)));
        int numberOfTreasure = treasureMap.getOrDefault(treasure, 0);
        g2d.setFont(new Font("default", Font.BOLD, 25));
        g2d.drawString(treasure.getStringFromNumber(numberOfTreasure) + ".", 50, 50 + i * 50);
        g2d.drawImage(image, 200, i * 50 + 25, this);
        g2d.drawString("x" + numberOfTreasure, 250, 50 + i * 50);
        i++;
      }
      BufferedImage image = ImageIO.read(new File(Utilities.getImageName(Arrow.CROOKED_ARROW)));
      g2d.drawString(Arrow.CROOKED_ARROW.getStringFromNumber(player.countArrows()) + ".", 50, 50 + i * 50);
      g2d.drawImage(image, 200, i * 50 + 25 + 13, 30, 7, this);
      g2d.drawString("x" + player.countArrows(), 250, 50 + i * 50);
    } catch (IOException e) {
      view.showErrorMessage(e.getMessage());
    }

//    g2d.drawString(outputString, 350, 50);


    if (model.getGameStatus() == GameStatus.GAME_CONTINUE) {
      playAgain.setVisible(false);
      if (model.getCurrentLocation().containsAboleth()) {
        killMonster.setVisible(true);
        textArea.setText("Location contains an Aboleth. Kill it before it sees you.");
      } else {
        killMonster.setVisible(false);
        textArea.setText(outputString);
      }
    } else {
      playAgain.setVisible(true);
      textArea.setText(outputString);
    }
  }


  protected void setFeatures(Features features) {
    playAgain.addActionListener(l -> {
      features.resetModel();
      view.requestFocus();
    });
    killMonster.addActionListener(l -> {
      features.killMonster();
      view.requestFocus();
    });
  }

  protected void showString(String s) {
    outputString = s;
    this.repaint();
  }
}
