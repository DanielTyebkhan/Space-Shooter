import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Game {
    public static final String NAME = "Shoot Things";
    public static final String GAME_OVER = "Game Over";
    public static final String FINAL_SCORE = "Your Final Score: ";
    public static final String HIGH_SCORE_MSG = "Highscore: ";
    public static final String REPLAY_MSG = "Play Again";
    public static final String NEW_HIGH_SCORE = "Congratulations, You got a new Highscore!";
    public static final String HIGH_SCORE_FILE = "highscore.txt";
    public static final String NO_SCORE_FILE_MSG = "Error the Highscorefile is missing";


    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;

    static JFrame frame;
    static Background maingame = new Background();
    static JPanel endpanel;
    static int highscore;
    static int gamescore;

    /**
     * Constructor
     */
    public Game(){
        try(Scanner input = new Scanner(new File(HIGH_SCORE_FILE))){
            highscore = input.nextInt();
        }catch(FileNotFoundException e){
            System.out.println(NO_SCORE_FILE_MSG);
        }
        frame = new JFrame(NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(WIDTH,HEIGHT);
        frame.add(maingame);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new Game();
    }

    /**
     * Ends the game
     */
    public static void endGame(){
        frame.remove(maingame);
        gamescore = maingame.getScore();
        highscore();
        endpanel = new JPanel();
        endpanel.setBackground(Color.black);
        endpanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel endlabel1 = new JLabel(GAME_OVER);
        endlabel1.setForeground(Color.green);
        c.gridx = 0;
        c.gridy = 0;
        endpanel.add(endlabel1, c);
        JLabel endlabel2 = new JLabel(FINAL_SCORE + gamescore);
        endlabel2.setForeground(Color.green);
        c.gridx = 0;
        c.gridy = 1;
        endpanel.add(endlabel2, c);
        JLabel endlabel3 = new JLabel(HIGH_SCORE_MSG + highscore);
        endlabel3.setForeground(Color.green);
        c.gridx = 0;
        c.gridy = 2;
        endpanel.add(endlabel3, c);
        JButton rbutton = new JButton(REPLAY_MSG);
        class RBListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent a){
                frame.setVisible(false);
                maingame = new Background();
                new Game();
            }
        }
        rbutton.addActionListener(new RBListener());
        c.gridx = 0;
        c.gridy = 3;
        endpanel.add(rbutton, c);
        frame.add(endpanel);
        frame.repaint();
        frame.revalidate();
    }

    /**
     * Sets the new high score
     */
    public static void highscore(){
        if(gamescore > highscore){
            //new thread prevents concurrent modification exception
            new Thread(() -> JOptionPane.showMessageDialog(frame, NEW_HIGH_SCORE)).start();
            try(PrintWriter out = new PrintWriter(HIGH_SCORE_FILE)){
                highscore = gamescore;
                out.println(highscore);
            }catch(FileNotFoundException e){
                System.out.println(NO_SCORE_FILE_MSG);
            }
        }
    }
}
