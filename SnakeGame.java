import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener
{
    final int Screen_Width = 600;
    final int Screen_Height = 400;
    final int UNIT_SIZE = 20;
    final int GAME_UNITS = (Screen_Width * Screen_Height) / (UNIT_SIZE * UNIT_SIZE);
    int[] x = new int[GAME_UNITS]; // snake x-coordinates
    int[] y = new int[GAME_UNITS]; // snake y-coordinates
    int bodyParts = 5;
    int appleX, appleY;
    int score = 0;
    char direction = 'R'; // U, D, L, R
    boolean running = false;
    Timer timer;
    Random random;

    SnakeGame()
    {
        this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        random = new Random();
        startGame();
    }

    void startGame()
    {
        AddNewApple();
        running = true;
        timer = new Timer(100, this); // refresh every 100ms
        timer.start();
    }

    void AddNewApple()
    {
        appleX = random.nextInt(Screen_Width / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(Screen_Height / UNIT_SIZE) * UNIT_SIZE;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    void draw(Graphics g)
    {
        if (running)
        {
//            //for grids
//            for(int i=0;i<Screen_Height;i++)
//            {
//                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,Screen_Height);
//                g.drawLine(0,i*UNIT_SIZE,Screen_Width,i*UNIT_SIZE);
//            }

            // Draw apple
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // Draw snake
            for (int i = 0; i < bodyParts; i++)
            {
                //for head
                if(i==0)
                {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
                else
                {
                    g.setColor(new Color(45,180,0));
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));//for multi color
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }

            }

            // Draw score
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 10, 20);
        }
        else
        {
            gameOver(g);
        }
    }

    void move()
    {
        for (int i = bodyParts; i > 0; i--)
        {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction)
        {
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
               x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }
    }

    void checkApple()
    {
        if (x[0] == appleX && y[0] == appleY)
        {
            bodyParts++;
            score++;
           AddNewApple();
        }
    }

    void checkCollision()
    {
        // Hit body
        for (int i = bodyParts; i > 0; i--)
        {
            if (x[0] == x[i] && y[0] == y[i])
            {
                running = false;
            }
        }

        // Hit wall
        if (x[0] < 0 || x[0] >= Screen_Width || y[0] < 0 || y[0] >= Screen_Height)
        {
            running = false;
        }

        if (!running)
        {
            timer.stop();
        }
    }

    void gameOver(Graphics g)
    {
        //for print Game over
        g.setColor(Color.RED);
        g.setFont(new Font("Ink free", Font.BOLD, 40));
        g.drawString("Game Over", Screen_Width / 2 - 100, Screen_Height / 2);

        //for print Score
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Ink free", Font.BOLD, 20));
        g.drawString("Score: " + score, Screen_Width / 2 - 30, Screen_Height / 2 + 40);
    }

    //for make things happen for movement ,for checking apple and for checking collision
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (running)
        {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    // Key listener to change direction
    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;


                //for W=Up,A=left,D=right and S=down
            case KeyEvent.VK_A:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_D:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_W:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_S:
                if (direction != 'U') direction = 'D';
                break;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

}