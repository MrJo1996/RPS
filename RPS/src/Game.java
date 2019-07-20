import Utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

class MainFrame extends JFrame {
    public static final int WIDTH_FRAME = (int) (1920 * 0.5);
    public static final int HEIGHT_FRAME = (int) (1080 * 0.5);

    GamePanel gamePanel;
    EndPanel endPanel;
    HomePanel homePanel;
    InfoPanel infoPanel;

    public MainFrame() {
        this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Resources.getImage("/GUI/Images/logoRPS.png"));
        this.setTitle("RPS");
        this.setLayout(null);

        this.homePanel = new HomePanel(this);
        this.getContentPane().add(homePanel);
        homePanel.setVisible(true);
        homePanel.setFocusable(true);
        homePanel.requestFocus();

        this.gamePanel = new GamePanel(this);
        this.getContentPane().add(gamePanel);
        gamePanel.setVisible(false);
        gamePanel.setFocusable(false);

        this.infoPanel = new InfoPanel(this);
        this.getContentPane().add(infoPanel);
        infoPanel.setVisible(false);
        infoPanel.setFocusable(false);

    }

    public void switchPanels(JPanel panelToDisable, JPanel panelToSetVisible) {
        panelToDisable.setVisible(false);
        panelToDisable.setFocusable(false);

        panelToSetVisible.setVisible(true);
        panelToSetVisible.setFocusable(true);
        panelToSetVisible.requestFocus();
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}

class GamePanel extends JPanel {

    private Image background;

    private Image rockImg;
    private Image paperImg;
    private Image scissorsImg;

    private Image rockImgAI;
    private Image paperImgAI;
    private Image scissorsImgAI;

    private Rectangle rockSelect;
    private Rectangle paperSelect;
    private Rectangle scissorsSelect;

    private int playerChoice = 0;
    private int AIchoice = 0;

    private int scorePlayer = 0;
    private int scoreIA = 0;
    private int WINNER;
    rpsAI opponent = new rpsAI();//crea nuova AI

    private final int WINNING_SCORE = 5; //punteggio per la vittoria
    MainFrame mainFrame;

    public GamePanel(MainFrame pMainFrame) {

        this.setSize(MainFrame.WIDTH_FRAME, MainFrame.HEIGHT_FRAME);
        this.setLayout(null);
        this.mainFrame = pMainFrame;
        this.addMouseListener(new MyMouseListener());

        this.background = Resources.getImage("/GUI/Images/background.png");

        this.rockImg = Resources.getImage("/GUI/Images/rockMove.png");
        this.paperImg = Resources.getImage("/GUI/Images/paperMove.png");
        this.scissorsImg = Resources.getImage("/GUI/Images/scissorsMove.png");

        this.rockImgAI = Resources.getImage("/GUI/Images/rockMoveAI.png");
        this.paperImgAI = Resources.getImage("/GUI/Images/paperMoveAI.png");
        this.scissorsImgAI = Resources.getImage("/GUI/Images/scissorsMoveAI.png");

        this.rockSelect = new Rectangle();
        this.rockSelect.setBounds(310, 420, 80, 80);

        this.paperSelect = new Rectangle();
        this.paperSelect.setBounds(310 + 80 + 30, 420, 80, 80);

        this.scissorsSelect = new Rectangle();
        this.scissorsSelect.setBounds(310 + 80 + 80 + 60, 420, 80, 80);

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);

        g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);

        //bottoni per scegliere l'azione da compiere
        g.drawImage(Resources.getImage("/GUI/Images/rockBtn.png"), rockSelect.x, rockSelect.y, rockSelect.width, rockSelect.height, null);
        g.drawImage(Resources.getImage("/GUI/Images/paperBtn.png"), paperSelect.x, paperSelect.y, paperSelect.width, paperSelect.height, null);
        g.drawImage(Resources.getImage("/GUI/Images/scissorsBtn.png"), scissorsSelect.x, scissorsSelect.y, scissorsSelect.width, scissorsSelect.height, null);


        //disegno il sasso, la carta o le forbici in base alla scelta del player
        switch (playerChoice) {
            case 1:
                g.drawImage(rockImg, 180, 210, 140, 115, null);
                break;
            case 2:
                g.drawImage(paperImg, 182, 210, 140, 115, null);
                break;
            case 3:
                g.drawImage(scissorsImg, 182, 210, 140, 115, null);
                break;

        }

        //disegno il sasso, la carta o le forbici dell'AI
        switch (AIchoice) {
            case 1:
                g.drawImage(rockImgAI, MainFrame.WIDTH_FRAME - 180 - 140, 210, 140, 115, null);

                break;
            case 2:
                g.drawImage(paperImgAI, MainFrame.WIDTH_FRAME - 180 - 140, 210, 140, 115, null);

                break;
            case 3:
                g.drawImage(scissorsImgAI, MainFrame.WIDTH_FRAME - 180 - 140, 210, 140, 115, null);

                break;
        }


        //stampo la catena markoviana
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 15));
        g.drawString("Sasso - Sasso: " + opponent.markovChain[0][0] + " Sasso - Carta: " + opponent.markovChain[0][1] + " Sasso - Forbici: " + opponent.markovChain[0][2], 265, 40);
        g.drawString("Carta - Sasso: " + opponent.markovChain[1][0] + " Carta - Carta: " + opponent.markovChain[1][1] + " Carta - Forbici: " + opponent.markovChain[1][2], 265, 60);
        g.drawString("Forbici - Sasso: " + opponent.markovChain[2][0] + " Forbici - Carta: " + opponent.markovChain[2][1] + " Forbici - Forbici: " + opponent.markovChain[2][2], 265, 80);

        //check stato match
        checkWinner(playerChoice, AIchoice);

        //stampa punteggi
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.BOLD, 30));
        g.drawString(String.valueOf(scorePlayer), 70, 270);
        g.drawString(String.valueOf(scoreIA), 880, 270);
    }


    private void checkWinner(int playerChoice, int aIchoice) {
        switch (playerChoice) {
            case 1: //sasso
                if (aIchoice == 1) {
                    //tie
                }
                if (aIchoice == 2) {
                    scoreIA++;
                }
                if (aIchoice == 3) {
                    scorePlayer++;
                }
                break;
            case 2: //carta
                if (aIchoice == 1) {
                    scorePlayer++;
                }
                if (aIchoice == 2) {
                    //tie
                }
                if (aIchoice == 3) {
                    scoreIA++;
                }
                break;
            case 3: //forbici
                if (aIchoice == 1) {
                    scoreIA++;
                }
                if (aIchoice == 2) {
                    scorePlayer++;
                }
                if (aIchoice == 3) {
                    //tie
                }
                break;
        }

        if ((scorePlayer == WINNING_SCORE)) {
            setWINNER(0); //vincitore GIOCATORE

            //reset var
            resetGame();

            mainFrame.endPanel = new EndPanel(mainFrame);
            mainFrame.getContentPane().add(mainFrame.endPanel);
            mainFrame.endPanel.setVisible(false);
            mainFrame.endPanel.setFocusable(false);
            mainFrame.switchPanels(mainFrame.gamePanel, mainFrame.endPanel);

        }
        if (scoreIA == WINNING_SCORE) {
            setWINNER(1); //vincitore IA

            //reset var
            resetGame();

            mainFrame.endPanel = new EndPanel(mainFrame);
            mainFrame.getContentPane().add(mainFrame.endPanel);
            mainFrame.endPanel.setVisible(false);
            mainFrame.endPanel.setFocusable(false);
            mainFrame.switchPanels(mainFrame.gamePanel, mainFrame.endPanel);

        }
    }

    public int getWINNER() {
        return WINNER;
    }

    public void setWINNER(int WINNER) {
        this.WINNER = WINNER;
    }

    public void resetGame() {
        scoreIA = 0;
        scorePlayer = 0;
        AIchoice = 0;
        playerChoice = 0;
    }

    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (rockSelect.contains(e.getPoint())) {
                playerChoice = 1;
                AIchoice = Game.play(playerChoice, opponent);
            }

            if (paperSelect.contains(e.getPoint())) {
                playerChoice = 2;
                AIchoice = Game.play(playerChoice, opponent);
            }

            if (scissorsSelect.contains(e.getPoint())) {
                playerChoice = 3;
                AIchoice = Game.play(playerChoice, opponent);
            }

            repaint();

        }
    }
}

class rpsAI {
    public float[][] markovChain; //usato per salvara le probabilità della catena di Markov
    private int[] timesPlayed; // contiene il numero di volte che il player ha effettuato quella mossa

    private int lastMove; //ultima mossa player
    private int moveBeforeLast; //penultima mossa player

    rpsAI() {//costruttore
        markovChain = new float[][]{{0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}, {0.33f, 0.33f, 0.33f}}; //setto valori iniziali catena
        timesPlayed = new int[]{0, 0, 0}; //setto valori iniziali
    }

    public int makeMove() {//in maniera probabilistica punta a determinare la prossima mossa del player
        Random rand = new Random();//stabilisce un generatore random
        float ranFloat = rand.nextFloat();//genera un num float random
        if (ranFloat <= markovChain[lastMove][1]) {//usa il float assieme alla catena di Markov che contiene le mosse probabili, per generare un movimento probabilistico
            return 2;//ritorna il contatore alla mossa predetta
        } else if (ranFloat <= markovChain[lastMove][2] + markovChain[lastMove][1]) {//addizionati per modellare l'intero spazio a disposizione
            return 3;
        } else {
            return 1;
        }
    }

    public void update(int newMove) {//Per aggiornare la catena di Markov utilizza i dati rilevanti delle precedenti mosse effettuate dal giocatore
        moveBeforeLast = lastMove;
        if (newMove == 1) {
            lastMove = 0;
        } else if (newMove == 2) {
            lastMove = 1;
        } else {
            lastMove = 2;
        }

        /* Aggiornamento Catena
         * 1. Moltiplica i valori associati ad una mossa (ad esempio "carta") per il numero di volte che quella mossa è stata scelta.
         * 2. Incrementa il valore associato alla penultima mossa e all'ultima mossa.
         * 3. incrementa il valore delle volte che una mossa è stata scelta
         * 4. Divide tutti i valori associati ad una mossa per il numero di volte appena incrementato.
         */

        for (int i = 0; i < 3; i++) { //1.
            markovChain[moveBeforeLast][i] *= timesPlayed[moveBeforeLast];
        }

        //2.
        markovChain[moveBeforeLast][lastMove] += 1;

        //3.
        timesPlayed[moveBeforeLast]++;

        //4.
        for (int j = 0; j < 3; j++) {
            markovChain[moveBeforeLast][j] /= timesPlayed[moveBeforeLast];
        }

    }
}

public class Game {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }

    static int play(int scelta, rpsAI opponent) {//play() - core gioco

        boolean playing = true;
        int AIMove;

        do {
            int playermove = scelta;

            AIMove = opponent.makeMove();//generazione mossa IA

            playing = false;

            opponent.update(playermove);//aggiornamento IA

        } while (playing);

        return AIMove;
    }

}