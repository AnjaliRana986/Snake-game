import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static  final int SCREEN_HEIGHT = 600;
    static  final int UNIT_SIZE = 25;    //size of objects -> how big the objects will be created
    static  final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 175;  //higher the delay slower the game is
    //arrays hold the coordinates including the head of the snake
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    //constructor
    GamePanel(){
        random = new Random();  // instance of the random class
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    //method
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    int i;
    public void draw(Graphics g) {
        if(running) {
            //grid lines
            for (i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) //draw lines across the X and Y axis
            {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255),random.nextInt(255))); //random color of snake
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD, 75));
            FontMetrics matrices = getFontMetrics(g.getFont());
            g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - matrices.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public  void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE);
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE);


    }
    public  void move(){
        for(int i = bodyParts; i > 0; i--) // shifting of body parts of snakes around
        {
            x[i] = x[i-1]; //shifting all the coordinates over by one spot
            y[i] = y[i-1];
            //switch change the direction
        }
        switch(direction){
            case 'U': //for up
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D': //for down
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L': //for left
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R': //for left
                x[0] = x[0] + UNIT_SIZE;
                break;


        }

    }
    public  void checkApple(){
        if((x[0] == appleX) && y[0] == appleY){
            bodyParts++;
            appleEaten++;
            newApple();
        }

    }
    public void checkCollisions(){
        //checks if head collides with body
        for( i = bodyParts; i > 0; i--)
        {
            if((x[0] == x[i]) && (y[0] == y[i]))
            {
                running = false;
            }
        }
        //if head touches left border
        if(x[0] < 0)
        {
            running = false;

        }
        //if head touches right border
        if(x[0] > SCREEN_WIDTH)
        {
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0)
        {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > SCREEN_HEIGHT)
        {
            running = false;
        }
        if(!running)
        {
            timer.stop();
        }

    }
    public  void gameOver(Graphics g){
        //score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics matrices1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - matrices1.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        FontMetrics matrices2 = getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - matrices2.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();

        }
        repaint();

    }
    public  class MyKeyAdapter extends KeyAdapter {

        public  void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;

            }


        }
    }
}
