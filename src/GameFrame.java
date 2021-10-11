import javax.swing.JFrame;

public class GameFrame extends JFrame {
    //constructor
    GameFrame(){
        //GamePanel panel = new GamePanel();
        //get rid of this instance -> another shortcut
        this.add(new GamePanel());
        //this dot add new game panel and this would work the same so that's another
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();  //if we add components to a JFrame this pack func is actually going to take our JFrame and fit it snugly around the components that we add to the frame and this dot set visible true
        this.setVisible(true);
        this.setLocationRelativeTo(null);  // set window screen in the middle

    }
}
