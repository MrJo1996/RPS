import Utils.Resources;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InfoPanel extends JPanel {

    Image background;
    MainFrame mainFrame;
    private Rectangle btnBack;

    public InfoPanel(MainFrame pMainFrame) {
        this.mainFrame = pMainFrame;

        this.setSize(MainFrame.WIDTH_FRAME, MainFrame.HEIGHT_FRAME);
        this.setLayout(null);

        this.addMouseListener(new MyMouseListener());

        this.btnBack = new Rectangle();
        this.btnBack.setBounds((int) (25 * 0.5), (int) (10 * 0.5), 80, 80);

        this.background = Resources.getImage("/GUI/Images/infoSfondo.png");

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);

        g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);

    }
    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (btnBack.contains(e.getPoint())){
                mainFrame.switchPanels(mainFrame.infoPanel,mainFrame.homePanel);
            }
        }
    }
}
