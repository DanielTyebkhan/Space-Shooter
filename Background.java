import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Background extends JPanel implements KeyListener{
    Ship ship;
    boolean missilepaint;
    boolean missilelimiter;
    ArrayList <Missile> toremovem = new ArrayList<Missile>();
    ArrayList <Missile> missiles = new ArrayList<Missile>();
    ArrayList <Alien> toremovea = new ArrayList<Alien>();
    ArrayList <Alien> aliens = new ArrayList<Alien>();
    ArrayList <SpeedBoost> speedboosts = new ArrayList<SpeedBoost>();
    ArrayList <SpeedBoost> toremoves = new ArrayList<SpeedBoost>();
    JLabel scorelabel;
    int score;
    javax.swing.Timer maintimer;
    javax.swing.Timer alientimer;
    javax.swing.Timer speedboostspawntimer;
    javax.swing.Timer speedboostpowertimer;
    public static final Sound DEATHSOUND = new Sound("deathsound.au");
    public static final Sound LASERSOUND = new Sound("lasersound.aiff");
    public static final Sound KILLSOUND = new Sound("killsound.aiff");

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
        g2d.drawImage(ship.getImage(), ship.x, ship.y, this);
        //cehcks if new missiles should be drawn/added to arraylist
        if(missilepaint){
            Missile m = new Missile(ship.x, ship.y, 1);
            m.x = m.x + ship.width/2 - m.width/2;
            missiles.add(m);
            LASERSOUND.play();
            missilepaint = false;
        }

        for(Missile mis: missiles){
            g2d.drawImage(mis.getImage(), mis.x, mis.y, this); 
        }

        for(Alien al: aliens){
            if(al.y + al.height > ship.y){
                DEATHSOUND.play(); 
                Game.endGame();
            }
            g2d.drawImage(al.getImage(), al.x, al.y, this);
        }
        //draws speedboosts
        for(SpeedBoost s: speedboosts){
            g2d.drawImage(s.getImage(), s.x, s.y, this);
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
        if((m.x > a.x && m.x + m.width < a.x + a.width && m.y <= a.y + a.height)
        ||(m.x < a.x && m.x + m.width > a.x + a.width && m.y <= a.y + a.height)
        ||(m.x > a.x && m.x < a.x + a.width && m.y <= a.y + a.height)
        ){
            return true;
        }else{
            return false;
        }
    }

    public void checkCollisions(){
        for(Missile m: missiles){
            if(m.y<0){
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
                if(s.y>ship.y){
                    toremoves.add(s);
                }
                if(collided(m, s)){
                    toremovem.add(m);
                    toremoves.add(s);
                    ship.speed = 2*ship.speed;
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
            ship.speed = ship.speed/2;
            speedboostpowertimer.stop();
        }
    }
}
