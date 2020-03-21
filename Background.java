import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Background extends JPanel implements KeyListener{

    public static final Sound DEATH_SOUND = new Sound("deathsound.au");
    public static final Sound LASER_SOUND = new Sound("lasersound.aiff");
    public static final Sound KILL_SOUND = new Sound("killsound.aiff");

    public static final int MAIN_TIME = 1;
    public static final int ALIEN_SPAWN_TIME = 1000;
    public static final int SPEED_BOOST_SPAWN_TIME = 10000;
    public static final int SHIP_START_X = 400;
    public static final int SHIP_START_Y = 700;
    public static final int SPAWN_SPEEDS = 1;

    public static final String POINTS_MSG = " points";

    private Ship ship;

    private boolean missilePaint;
    private boolean missileLimiter;

    private ArrayList <Missile> toRemoveMissiles = new ArrayList<>();
    private ArrayList <Missile> missiles = new ArrayList<>();
    private ArrayList <Alien> toRemoveAliens = new ArrayList<>();
    private ArrayList <Alien> aliens = new ArrayList<>();
    private ArrayList <SpeedBoost> speedBoosts = new ArrayList<>();
    private ArrayList <SpeedBoost> toRemoveSpeedBoosts = new ArrayList<>();

    private JLabel scoreLabel;

    private int score;

    private javax.swing.Timer mainTimer;
    private javax.swing.Timer alienTimer;
    private javax.swing.Timer speedBoostSpawnTimer;
    private javax.swing.Timer speedBoostPowerTimer;

    /**
     * Constructor
     */
    public Background(){
        score = 0;
        scoreLabel = new JLabel(score + POINTS_MSG);
        scoreLabel.setForeground(Color.green);
        this.add(scoreLabel);
        setBackground(Color.black);
        ship = new Ship(SHIP_START_X, SHIP_START_Y);
        this.setFocusable(true);
        addKeyListener(this);
        missilePaint = false;
        missileLimiter = false;
        alienTimer = new javax.swing.Timer(ALIEN_SPAWN_TIME, new AlienTimerListener());
        mainTimer = new javax.swing.Timer(MAIN_TIME, new TimerListener());
        speedBoostSpawnTimer = new javax.swing.Timer(SPEED_BOOST_SPAWN_TIME, new SpeedBoostSpawnListener());
        speedBoostPowerTimer = new javax.swing.Timer(SpeedBoost.DURATION, new SpeedBoostPowerListener());
        speedBoostSpawnTimer.start();
        mainTimer.start();
        alienTimer.start();
    }

    /**
     * Calls the parent's paint method
     * @param g The graphics object
     */
    @Override  
    public void paintComponent(Graphics g){
        scoreLabel.setText(score + POINTS_MSG);
        checkCollisions();
        missiles.removeAll(toRemoveMissiles);
        aliens.removeAll(toRemoveAliens);
        speedBoosts.removeAll(toRemoveSpeedBoosts);
        super.paintComponent(g);    
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
        //checks if new missiles should be drawn/added to arraylist
        if(missilePaint){
            Missile missile = new Missile(ship.getX(), ship.getY(), 1);
            missile.setX(missile.getX() + ship.getWidth()/2 - missile.getWidth()/2);
            missiles.add(missile);
            LASER_SOUND.play();
            missilePaint = false;
        }

        for(Missile m: missiles){
            g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
        }

        for(Alien a: aliens){
            if(a.getY() + a.getHeight() > ship.getY()){
                DEATH_SOUND.play();
                Game.endGame();
            }
            g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
        }

        for(SpeedBoost s: speedBoosts){
            g2d.drawImage(s.getImage(), s.getX(), s.getY(), this);
        }
    }

    /**
     * Prevents the user from rapid firing by holding down space bar
     * @param e Not used
     */
    @Override
    public void keyReleased(KeyEvent e){
        missileLimiter = false;
        ship.keyReleased(e);
        repaint();
    }

    /**
     * Moves the ship or shoots a missile based on the player's action
     * @param e the player's action
     */
    @Override
    public void keyPressed(KeyEvent e){
        ship.keyPressed(e);
        ship.move();
        if(!missileLimiter){
            if(Missile.keyPressed(e)){
                missilePaint = true;
                missileLimiter = true;
            }
        }
        repaint();
    }

    /**
     * Checks if two objects have collided
     * @param m The first object
     * @param a The second object
     * @return true if they collided, else false
     */
    public boolean collided(SpaceObject m, SpaceObject a){
        return (m.getX() > a.getX() && m.getX() + m.getWidth() < a.getX() + a.getWidth() && m.getY() <= a.getY() + a.getHeight())
                || (m.getX() < a.getX() && m.getX() + m.getWidth() > a.getX() + a.getWidth() && m.getY() <= a.getY() + a.getHeight())
                || (m.getX() > a.getX() && m.getX() < a.getX() + a.getWidth() && m.getY() <= a.getY() + a.getHeight());
    }

    /**
     * Checks if missiles collided with aliens or speedboosts
     */
    public void checkCollisions(){
        for(Missile m: missiles){
            if(m.getY()<0){
                toRemoveMissiles.add(m);
            }
            for(Alien a: aliens){
                if(collided(m, a)){
                    toRemoveMissiles.add(m);
                    toRemoveAliens.add(a);
                    score+=2;
                    KILL_SOUND.play();
                }
            }
            for(SpeedBoost s: speedBoosts){
                if(s.getY()>ship.getY()){
                    toRemoveSpeedBoosts.add(s);
                }
                if(collided(m, s)){
                    toRemoveMissiles.add(m);
                    toRemoveSpeedBoosts.add(s);
                    ship.setSpeed(SpeedBoost.MULITPLIER*ship.getSpeed());
                    speedBoostPowerTimer.start();
                }
            }
        }
    }

    @Override public void keyTyped(KeyEvent e){
    }

    /**
     * Moves the ship
     */
    class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a){
            ship.move();
            repaint();
        }
    }

    /**
     * Spawns aliens
     */
    class AlienTimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a){
            Random random = new Random();
            int alienx = random.nextInt((900-100)+1)+100;
            aliens.add(new Alien(alienx, 0, SPAWN_SPEEDS));
        }
    }

    /**
     * Spawns SpeedBoosts
     */
    class SpeedBoostSpawnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a){
            double spawnrandom = Math.random();
            if(spawnrandom > .5){
                Random random = new Random();
                int speedboostx = random.nextInt((900-100)+1)+100;
                speedBoosts.add(new SpeedBoost(speedboostx, 0, SPAWN_SPEEDS));
            }
        }
    }

    /**
     * Adjusts the ship's speed when the speedboosts duration runs out
     */
    class SpeedBoostPowerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent a){
            ship.setSpeed(ship.getSpeed()/SpeedBoost.MULITPLIER);
            speedBoostPowerTimer.stop();
        }
    }

    /**
     * Gets the score
     * @return the current score
     */
    public int getScore() {
        return score;
    }
}
