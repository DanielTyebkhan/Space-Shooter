import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class Game {
    static JFrame frame;
    static Background maingame = new Background();
    static JPanel endpanel;
    static int highscore;
    static int gamescore;
    public Game(){
        try(Scanner input = new Scanner(new File("highscore.txt"))){
            highscore = input.nextInt();
        }catch(FileNotFoundException e){
            System.out.println("Error the Highscorefile is missing");
        }
        frame = new JFrame("Shoot Things");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1000,1000);
        frame.add(maingame);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        Game run = new Game();
    }

    public static void endGame(){
        frame.remove(maingame);
        gamescore = maingame.getScore();
        highscore();
        endpanel = new JPanel();
        endpanel.setBackground(Color.black);
        endpanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JLabel endlabel1 = new JLabel("Game Over");
        endlabel1.setForeground(Color.green);
        c.gridx = 0;
        c.gridy = 0;
        endpanel.add(endlabel1, c);
        JLabel endlabel2 = new JLabel("Your Final Score: " + gamescore);
        endlabel2.setForeground(Color.green);
        c.gridx = 0;
        c.gridy = 1;
        endpanel.add(endlabel2, c);
        JLabel endlabel3 = new JLabel("Highscore: " + highscore);
        endlabel3.setForeground(Color.green);
        c.gridx = 0;
        c.gridy = 2;
        endpanel.add(endlabel3, c);
        JButton rbutton = new JButton("Play Again");
        class RBListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent a){
                frame.setVisible(false);
                maingame = new Background();
                Game run = new Game();
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

    public static void highscore(){
        if(gamescore > highscore){
            //new thread prevents concurrent modification exception
            new Thread(() -> JOptionPane.showMessageDialog(frame, "Congratulations, You got a new Highscore!")).start();
            try(PrintWriter out = new PrintWriter("highscore.txt")){
                highscore = gamescore;
                out.println(highscore);
            }catch(FileNotFoundException e){
                System.out.println("filenotfound");
            }
        }
    }
}
