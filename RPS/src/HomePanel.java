import Utils.Resources;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomePanel extends JPanel {
    Image background;
    MainFrame mainFrame;
    private Rectangle btnPlay;
    private Rectangle btnInfo;

    public HomePanel(MainFrame pMainFrame) {
        this.mainFrame = pMainFrame;

        this.setSize(MainFrame.WIDTH_FRAME, MainFrame.HEIGHT_FRAME);
        this.setLayout(null);

        this.addMouseListener(new MyMouseListener());

        this.btnPlay = new Rectangle();
        this.btnPlay.setBounds((int) (295 * 0.5), (int) (800 * 0.5), 80, 80);

        this.btnInfo = new Rectangle();
        this.btnInfo.setBounds((int) (1390 * 0.5), (int) (800 * 0.5), 80, 80);


        this.background = Resources.getImage("/GUI/Images/sfondoRPS.png");

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);

        g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);

    }

    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (btnPlay.contains(e.getPoint())) {
                mainFrame.switchPanels(mainFrame.homePanel, mainFrame.gamePanel);
                System.out.println("PLAY");
            }

            if (btnInfo.contains(e.getPoint())) {

                System.out.println("info");

                mainFrame.switchPanels(mainFrame.homePanel, mainFrame.infoPanel);
            }
        }
    }

}
