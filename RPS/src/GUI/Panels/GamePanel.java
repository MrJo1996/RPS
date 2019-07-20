/*
package GUI.Panels;

import GUI.MainFrame;
import Utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private Image background;

    private Image rockImg;
    private Image paperImg;
    private Image scissorsImg;

    private Rectangle rockSelect;
    private Rectangle paperSelect;
    private Rectangle scissorsSelect;

    private int choice = 0;


    public GamePanel() {

        this.setSize(850, 600);
        this.setLayout(null);

        this.addMouseListener(new MyMouseListener());

        this.background = Resources.getImage("/GUI/Images/background.jpg");

        this.rockImg = Resources.getImage("/GUI/Images/rockMove.png");
        this.paperImg = Resources.getImage("/GUI/Images/paperMove.png");
        this.scissorsImg = Resources.getImage("/GUI/Images/scissorsMove.png");

        this.rockSelect = new Rectangle();
        this.rockSelect.setBounds(10, 10, 80, 80);

        this.paperSelect = new Rectangle();
        this.paperSelect.setBounds(90 +20, 10, 80, 80);

        this.scissorsSelect = new Rectangle();
        this.scissorsSelect.setBounds(90+80+40, 10, 80, 80);


    }

    @Override
    protected void paintComponent(Graphics g) {

        g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);

        g.drawImage(Resources.getImage("/GUI/Images/rockBtn.png"), rockSelect.x, rockSelect.y, rockSelect.width, rockSelect.height, null);

        g.drawImage(Resources.getImage("/GUI/Images/paperBtn.png"), paperSelect.x, paperSelect.y, paperSelect.width, paperSelect.height, null);

        g.drawImage(Resources.getImage("/GUI/Images/scissorsBtn.png"), scissorsSelect.x, scissorsSelect.y, scissorsSelect.width, scissorsSelect.height, null);

        //disegno il pugno, la carta o le forbici in base alla scelta del player

        switch (choice) {
            case 1:
                System.out.println("iao");
                g.drawImage(rockImg, 100, 100, 115, 115, null);

                break;
            case 2:
                g.drawImage(paperImg, 100, 100, 115, 115, null);

                break;
            case 3:
                g.drawImage(scissorsImg, 100, 100, 115, 115, null);

                break;

        }


    }

    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (rockSelect.contains(e.getPoint())) {
                choice = 1;
            }
             if (paperSelect.contains(e.getPoint())) {
                 choice = 2;
             }

             if (scissorsSelect.contains(e.getPoint())) {
                 choice = 3;
             }
             repaint();

            }
        }
    }
*/
