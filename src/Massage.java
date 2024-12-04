import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class Massage extends Component {
    //מחלקה שאחראית על ניקוד
    public static JLabel score; //אובייקט טסטט
    public static JLabel livesLeb;

    static {
        score = new JLabel();
        score.setFont(new Font("Ariel",Font.ITALIC,20));
        score.setForeground(Color.white);
        score.setBounds(10,10,200,30);
        score.setLocation(0,0);
    }

    static {
        livesLeb = new JLabel();
        livesLeb.setFont(new Font("Ariel",Font.ITALIC,20));
        livesLeb.setForeground(Color.white);
        livesLeb.setBounds(10,10,200,30);
        livesLeb.setLocation(440,0);
    }

    public static void massageVic(){
        JOptionPane.showMessageDialog(null, "מזל טוב ! נצחת במשחק \n הניקוד הסופי שלך הוא: " + DefaultMap.score);
        System.exit(0);
    }
    public static void massageLos(){
        JOptionPane.showMessageDialog(null,  "הפסדת במשחק. \n הניקוד שלך הוא: " + DefaultMap.score);
        System.exit(0);
    }

    //בתהליך עידכון

//    public void massageWin() {
//        if (DefaultMap.eat == 0){
//            int choice = JOptionPane.showConfirmDialog(this, "Hurray. You've won. \n Your score is: " + DefaultMap.score + "\n" + "You want to continue?",
//                    "FINISH", YES_NO_OPTION);
//            if (choice == YES_OPTION) {
//                restartGame();
//
//            } else {
//                System.exit(0);
//            }
//        }
////
////
//    }
//
//    public void massageLos() {
//        if (Pacman2.lives == 0){
//            int choice = JOptionPane.showConfirmDialog(this, "Sorry, you lost. \n Your score is: " + DefaultMap.score + "\n" + "You want to continue?",
//                    "FINISH", YES_NO_OPTION);
//            if (choice == YES_OPTION) {
//                restartGame();
//
//            } else {
//                System.exit(0);
//            }
//        }
//
//    }
//    public static void restartGame() {
//        JFrame game = Main.createGameFrame("Pacman V3.5"); //יצירת חלון
//        JPanel map = DefaultMap.createMap(); //יצירת מפת המשחק
//        Pacman2.lives = 3;
//        DefaultMap.score = 0;
//        DefaultMap.fillMap(game, map, true);
//    }






}
