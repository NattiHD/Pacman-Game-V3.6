import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class Pacman2 extends Players implements KeyListener,
        Runnable{
    private static volatile Pacman2 instance; //משתנה לסינגלטון
    public static boolean canEatGhost = false; //האם יכול לאכול
    public static int lives = 3;
    public static final String pathUp = "src/imgPlayer/pacmanUp.png";
    public static final String pathDown = "src/imgPlayer/pacmanDown.png" ;
    public static final String pathLeft = "src/imgPlayer/pacmanLeft.png";
    public static final String pathRight = "src/imgPlayer/pacmanRight.png";

    boolean up, down, left, right; //כיוון הליכה
    boolean up1, down1, left1, right1; //כיוון הליכה



    private Pacman2() {
        super(225, 450, Color.black, pathRight);
    }

    @Override
    public boolean canMove(int newX, int newY) {
        // קריאה להסרה של האוכל במיקום שהשחקן עבר
        DefaultMap.deleteEat(newX / size, newY / size);
        return super.canMove(newX, newY);
    }

    @Override
    public void move() {
        int size = Pacman2.size / 5; //הפיכת משתנה סטטי לפרטי
        int newX = xPlayer;
        int newY = yPlayer;

        if (up) {
            newY = yPlayer - size;
            updateImage(pathUp); //עדכון תמונה
        }
        if (down) {
            newY = yPlayer + size;
            updateImage(pathDown);
        }
        if (left) {
            newX = xPlayer - size;
            updateImage(pathLeft);
        }
        if (right) {
            newX = xPlayer + size;
            updateImage(pathRight);
        }

        if (canMove(newX, newY)) {
            xPlayer = newX;
            yPlayer = newY;
        }

        setLocSide(xPlayer, yPlayer); //קריאה לפונקציית מערה
        this.setLocation(xPlayer, yPlayer);
        Massage.score.setText("ניקוד: " + DefaultMap.score);
        Massage.livesLeb.setText("חיים: " + lives);
        System.out.println("Pacman   x= " + xPlayer + "  y= " + yPlayer);
    }

    //עדכון תמונה לפי הכיוון החדש
    private void updateImage(String path) {
        this.pathImage = path;
        // טעינת התמונה לפי ה-path החדש
        ImageIcon newIcon = new ImageIcon(pathImage);
        Image image = newIcon.getImage();
        Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        this.imageIcon = new ImageIcon(scaledImage);

        // עדכון התמונה ב-JLabel
        this.label.setIcon(imageIcon);
    }

    // v2 אכילת אוכל מיוחד
    //הפונקציה עוסקת בשחקן ויותר הגיוני לכותבה כאן
    public boolean eatSpecialFood2() {
        if (DefaultMap.map[yPlayer / size][xPlayer / size] == 2) {
            DefaultMap.score += 50;
            System.out.println("כילה מיוחד");
            for (Ghost ghost: Ghost.ghosts){ //הופך רוחות לאדום - סימן לאוכל
                 ghost.updateImage(Ghost.bluePathLeft); //הפוך רוחות לכחולות
            }
            DefaultMap.map[yPlayer / size][xPlayer / size] = 1;
            DefaultMap.deleteEat(xPlayer / size, yPlayer / size);
//            massage(); //הודעה על ניצחון אם אכל הכול
            Pacman2.canEatGhost = true;
        }
        return Pacman2.canEatGhost;
    }

    @Override //ללא
    public void keyTyped(KeyEvent e) {

    }

    @Override //מאזין לחיצה
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_UP:
                up = true;
                this.pathImage = pathUp;
                down = left = right = false;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                this.pathImage = pathDown;
                up = left = right = false;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                this.pathImage = pathLeft;
                up = down = right = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                this.pathImage = pathRight;
                up = down = left = false;
                break;
        }
    }


    @Override // ללא
    public void keyReleased(KeyEvent e) {}

    // פונקציה סטטית להחזרת האובייקט היחיד
    public static Pacman2 getInstance() {
        if (instance == null) {
            synchronized (Pacman2.class) {
                if (instance == null) {
                    instance = new Pacman2();
                }
            }
        }
        return instance;
    }

    @Override //מריץ את השחקן
    public void run() {
        while (true) {
            move();
            repaint();
            try {Thread.sleep(300/5);}
            catch (InterruptedException e) {
                throw new RuntimeException(e);}
        }
    }


    //בתהליך
//    public void massage() {
//        if (DefaultMap.eat == 0){
//            int choice = JOptionPane.showConfirmDialog(this, "Hurray. You've won. \n Your score is: " + DefaultMap.score + "\n" + "You want to continue?",
//                    "FINISH", YES_NO_OPTION);
//            if (choice == YES_OPTION) {
//                Main.restartGame();
//
//            } else {
//                System.exit(0);
//            }
//        }
//
//
//    }
}

