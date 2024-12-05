import javax.swing.*;

import java.awt.*;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class Main extends Component {
    //יצירת חלון המשחק
    public static JFrame createGameFrame(String title){
        JFrame jFrame = new JFrame(title);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        return jFrame;
    }

    //סיום יצירת החלון
    public static void finishCreateFrame(JFrame jFrame){
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }


    public static void main(String[] args)  {
        JFrame game = createGameFrame("Pacman V3.6"); //יצירת חלון
        JPanel map = DefaultMap.createMap(); //יצירת מפת המשחק
        DefaultMap.fillMap(game, map, true); //מילוי המפה בפאנלים לפי איפיון

        Pacman2 pacman = Pacman2.getInstance(); //יצירת שחקן סינגלטון
        game.addKeyListener(pacman); //הוספה לשליטה במקלדת
        new Thread(pacman).start(); //התנעת פעולת השחקן

        //יצירת רוחות והוספה למערך ולתור
        Ghost ghostGreen = Ghost.createGhost("green", pacman);
        Ghost ghostPink = Ghost.createGhost("pink", pacman);
        Ghost.ghosts.add(ghostGreen);//הוספה למערך
        Ghost.ghosts.add(ghostPink);
        Ghost.ghostQueue.offer(ghostGreen); //הוספה לתור
        Ghost.ghostQueue.offer(ghostPink);
        new Thread(ghostGreen).start(); //הפעלת רוחות


        DefaultMap.layerManager(game, map, pacman); //ניהול שכבות
        finishCreateFrame(game); //אריזת החלון
    }
}