import javax.swing.*;
public class SnakeGameMain
{
        public static void main(String[] args) throws Exception
        {
            JFrame frame = new JFrame("Simple Snake Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.add(new SnakeGame());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

}
