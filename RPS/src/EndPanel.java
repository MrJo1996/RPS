import Utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EndPanel extends JPanel {
    Image background;
    int winner;
    String nameWinner;
    MainFrame mainFrame;
    private Rectangle btnHome;

    public EndPanel(MainFrame pMainFrame) {
        this.mainFrame = pMainFrame;

        this.setSize(MainFrame.WIDTH_FRAME, MainFrame.HEIGHT_FRAME);
        this.setLayout(null);

        this.addMouseListener(new MyMouseListener());

        this.btnHome = new Rectangle();
        this.btnHome.setBounds((int) (25 * 0.5), (int) (10 * 0.5), 80, 80);

        this.background = Resources.getImage("/GUI/Images/sfondoEndPanel.png");

        this.winner = this.mainFrame.getGamePanel().getWINNER();

        System.out.println("nome: " + this.mainFrame.getGamePanel().getWINNER());

        if (this.winner == 0) {
            nameWinner = "You";
        }

        if (this.winner == 1) {
            nameWinner = "the IA";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));

        g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);

        g.drawImage(Resources.getImage("/GUI/Images/homeBtn.png"), btnHome.x, btnHome.y, btnHome.width, btnHome.height, null);

        g.drawString("The winner is " + nameWinner + " !", 333, 265);

    }

    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {

            if (btnHome.contains(e.getPoint())) {
                mainFrame.gamePanel.resetGame();

                mainFrame.switchPanels(mainFrame.endPanel, mainFrame.homePanel);
            }
        }
    }
}
