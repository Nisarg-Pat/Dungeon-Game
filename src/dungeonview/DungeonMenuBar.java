package dungeonview;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import dungeoncontroller.Features;

class DungeonMenuBar extends JMenuBar {

  DungeonSpringView view;

  JMenu menu, submenu;
  JMenuItem menuItem, resetMenuItem, quitMenuItem;
  JRadioButtonMenuItem rbMenuItem;
  JCheckBoxMenuItem cbMenuItem;

  DungeonMenuBar(DungeonSpringView view){
    super();
    this.view = view;
    menu = new JMenu("Settings");
    menu.setMnemonic(KeyEvent.VK_A);
    menu.getAccessibleContext().setAccessibleDescription(
            "The only menu in this program that has menu items");
    this.add(menu);

    resetMenuItem = new JMenuItem("Reset Game");
    menu.add(resetMenuItem);

    quitMenuItem = new JMenuItem("Quit");
    menu.add(quitMenuItem);

    this.add(menu);
  }


  protected void setFeatures(Features features) {
    resetMenuItem.addActionListener(l -> {
      view.openPopup();
    });
    quitMenuItem.addActionListener(l -> {
      features.exitProgram();
    });
  }
}
