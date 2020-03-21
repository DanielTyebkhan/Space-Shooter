import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Background extends JPanel implements KeyListener{

    public static final Sound DEATHSOUND = new Sound("deathsound.au");
    public static final Sound LASERSOUND = new Sound("lasersound.aiff");
    public static final Sound KILLSOUND = new Sound("killsound.aiff");

    private Ship ship;

    private boolean missilepaint;
    private boolean missilelimiter;

    private ArrayList <Missile> toremovem = new ArrayList<>();
    private ArrayList <Missile> missiles = new ArrayList<>();
    private ArrayList <Alien> toremovea = new ArrayList<>();
    private ArrayList <Alien> aliens = new ArrayList<>();
    private ArrayList <SpeedBoost> speedboosts = new ArrayList<>();
    private ArrayList <SpeedBoost> toremoves = new ArrayList<>();

    private JLabel scorelabel;

    private int score;

    private javax.swing.Timer maintimer;
    private javax.swing.Timer alientimer;
    private javax.swing.Timer speedboostspawntimer;
    private javax.swing.Timer speedboostpowertimer;

    /**
     * Constructor
     */
    public Background(){
        score = 0;
        scorelabel = new JLabel(score +" points");
        scorelabel.setForeground(Color.green);
        this.add(scorelabel);
        setBackground(Color.black);
        ship = new Ship(400, 700); 
        this.setFocusable(true);
        addKeyListener(this);
        missilepaint = false;
        missilelimiter = false;
        alientimer = new javax.swing.Timer(1000, new AlienTimerListener());
        maintimer = new javax.swing.Timer(1, new TimerListener());
        speedboostspawntimer = new javax.swing.Timer(10000, new SpeedBoostSpawnListener());
        speedboostpowertimer = new javax.swing.Timer(4000, new SpeedBoostPowerListener());
        speedboostspawntimer.start();
        maintimer.start();
        alientimer.start();
    }

    @Override  
    public void paintComponent(Graphics g){
        scorelabel.setText(score +" points");
        checkCollisions();
        missiles.removeAll(toremovem);
        aliens.removeAll(toremovea);
        speedboosts.removeAll(toremoves);
        super.paintComponent(g);    
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
        //checks if new missiles should be drawn/added to arraylist
        if(missilepaint){
            Missile missile = new Missile(ship.getX(), ship.getY(), 1);
            missile.setX(missile.getX() + ship.getWidth()/2 - missile.getWidth()/2);
            missiles.add(missile);
            LASERSOUND.play();
            missilepaint = false;
        }

        for(Missile mis: missiles){
            g2d.drawImage(mis.getImage(), mis.getX(), mis.getY(), this);
        }

        for(Alien al: aliens){
            if(al.getY() + al.getHeight() > ship.getY()){
                DEATHSOUND.play(); 
                Game.endGame();
            }
            g2d.drawImage(al.getImage(), al.getX(), al.getY(), this);
        }

        for(SpeedBoost s: speedboosts){
            g2d.drawImage(s.getImage(), s.getX(), s.getY(), this);
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        missilelimiter = false;//prevents the user from rapid firing by holding down space bar
        ship.keyReleased(e);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        ship.keyPressed(e);
        ship.move();
        if(!missilelimiter){
            if(Missile.keyPressed(e)){
                missilepaint = true;
                missilelimiter = true;
            }
        }
        repaint();
    }

    public boolean collided(SpaceObject m, SpaceObject a){
        return (m.getX() > a.getX() && m.getX() + m.getWidth() < a.getX() + a.getWidth() && m.getY() <= a.getY() + a.getHeight())
                || (m.getX() < a.getX() && m.getX() + m.getWidth() > a.getX() + a.getWidth() && m.getY() <= a.getY() + a.getHeight())
                || (m.getX() > a.getX() && m.getX() < a.getX() + a.getWidth() && m.getY() <= a.getY() + a.getHeight());
    }

    public void checkCollisions(){
        for(Missile m: missiles){
            if(m.getY()<0){
                toremovem.add(m);
            }
            for(Alien a: aliens){
                if(collided(m, a)){
                    toremovem.add(m);
                    toremovea.add(a);
                    score+=2;
                    KILLSOUND.play();
                }
            }
            for(SpeedBoost s: speedboosts){
                if(s.getY()>ship.getY()){
                    toremoves.add(s);
                }
                if(collided(m, s)){
                    toremovem.add(m);
                    toremoves.add(s);
                    ship.setSpeed(2*ship.getSpeed());
                    speedboostpowertimer.start();
                }
            }
        }
    }

    @Override public void keyTyped(KeyEvent e){
    }
    
    class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a){
            ship.move();
            repaint();
        }
    }

    class AlienTimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a){
            Random random = new Random();
            int alienx = random.nextInt((900-100)+1)+100;
            Alien tempa = new Alien(alienx, 0, 1);
            aliens.add(tempa);
        }
    }

    class SpeedBoostSpawnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a){
            double spawnrandom = Math.random();
            if(spawnrandom > .5){
                Random random = new Random();
                int speedboostx = random.nextInt((900-100)+1)+100;
                SpeedBoost temps = new SpeedBoost(speedboostx, 0, 1);
                speedboosts.add(temps);
            }
        }
    }

    class SpeedBoostPowerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a){
            ship.setSpeed(ship.getSpeed()/2);
            speedboostpowertimer.stop();
        }
    }

    public int getScore() {
        return score;
    }
}
