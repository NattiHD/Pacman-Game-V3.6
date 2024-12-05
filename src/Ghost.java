import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class Ghost extends Players implements Runnable {
    public static ArrayList<Ghost> ghosts = new ArrayList<>();//מערך רוחות
    public static Queue<Ghost> ghostQueue = new LinkedList<>(); //תור הרוחות

    public static final String pinkPathLeft = "src\\img\\imgGhost\\pink_left.png";
    public static final String greenPathLeft = "src/img/imgGhost/green_left.png";
    public static final String bluePathLeft = "src/img/imgGhost/blue_left.png";


    final int up = 0, down = 1, left = 2, right = 3; //ערכים לתזוזה
    String name; // שם הרוח
    Random random = new Random();
    Pacman2 pacman;
    boolean isCanMove; //אחראי על סדר היציאה של הרוחות כל אכילת רבע אוכל

    public Ghost(int xPlayer, int yPlayer, Pacman2 pacman, String path, String name) {
        super(xPlayer, yPlayer, Color.black, path);
        new Thread(this).start();
        this.pacman = pacman;
        this.name = name;

    }
    //מחזיר האם יש פגיעה
    public boolean isTouch (int pacmanRow, int monsterRow, int pacmanCol, int monsterCol) {
        int x = Math.abs(pacmanRow - monsterRow);
        int y = Math.abs(pacmanCol - monsterCol);
        return x < size && y < size;
    }

    //פונקציית מגע בין שחקן לרוח v3
    //מופעלת במחלקת הרוחות ומקבלת את מיקום הרוח החדש
    //מחזירה את כל מיקומי השחקנים להתחלה ומוריד חיים
    public void touch3(int newX, int newY) {
        if (isTouch(this.pacman.xPlayer, this.xPlayer, this.pacman.yPlayer, this.yPlayer)) {
            System.out.println("פסילה");
            pacman.xPlayer = pacman.startX; //מחזיר מיקום שחקן
            pacman.yPlayer = pacman.startY;
            for (Ghost ghost : ghosts) {   //מחזיר מיקומי רוחות
                ghost.xPlayer = ghost.startX;
                ghost.yPlayer = ghost.startY;
                ghostQueue.offer(ghost); //מחזיר רוחות לתור
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Pacman2.lives--;
            pacman.move();
        }
        if (Pacman2.lives == 0) Massage.massageLos();
//        Massage massage = new Massage();
//        massage.massageLos();


    }

    //פונקציית מגע בין שחקן לרוח v4
    //מופעלת במחלקת הרוחות ומקבלת את מיקום הרוח החדש
    //מחזירה את כל מיקום הרוח להתחלה
    public void touch4(int newX, int newY) {
        if (isTouch(this.pacman.xPlayer, this.xPlayer, this.pacman.yPlayer, this.yPlayer)) {
            DefaultMap.score += 200;
            this.xPlayer = this.startX; //מחזיר מיקום הרוח
            this.yPlayer = this.startY;
            ghostQueue.offer(this); //מחזיר רוח לתור

        }
    }

    //פונקציית מגע v5
    //משלבת את פונקציות מגע V3 ו V4
    //לפי תנאי בולייאני
    public void touch(boolean flag){
        if (flag) { //אם אכל אוכל מיוחד
            timeFiveMin(); //הפעלת הטיימר
            touch4(xPlayer, yPlayer); //יכול לאכול רוחות (מגע V4) במשך 5 שניות
        }
        else touch3(xPlayer, yPlayer); //מחזירה את כל השחקנים לנקודת פתיחה ומורידה חיים (מגע V3)
    }

    //אחראי תזמון אכילה מיוחדת ושינוי צבע
    public void timeFiveMin() {
        Timer timer = new Timer();
        // יצירת משימה שתתבצע לאחר 10 שניות
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (Ghost ghost: Ghost.ghosts){
                    switch (ghost.name){
                        case "green" -> ghost.updateImage(greenPathLeft);
                        case "pink" -> ghost.updateImage(pinkPathLeft);
                    }
                }
                Pacman2.canEatGhost = false;
                timer.cancel(); // עצירת הטיימר לאחר ביצוע המשימה
            }
        };
        timer.schedule(task, 10000);
    }

    @Override //קביעת מיקום להזזה
    public void move() throws InterruptedException {
        if (!isCanMove) return; //אם עדיין לא אכל רבע אל תבצע את הפומקציה
//        int size = Players.size / 2; //הפיכת משתנה סטטי לפרטי
        int direc = random.nextInt(0, 4); //בחירה

        int newX = xPlayer;
        int newY = yPlayer;

        switch (direc) {
            case up -> newY = yPlayer - size;
            case down -> newY = yPlayer + size;
            case left -> newX = xPlayer - size;
            case right -> newX = xPlayer + size;

        }
        if (canMove(newX, newY)) {
            xPlayer = newX;
            yPlayer = newY;
        }

        boolean flag = pacman.eatSpecialFood2(); //בודק אכילת אוכל מיוחד v2
        touch(flag); // קריאה לפעולת מגע v5 לפי הבולייאני
        System.out.println("Ghost  x= " + xPlayer + "  y= " + yPlayer);
        setLocSide(xPlayer, yPlayer); //קריאה לפונקציית מערה
        setLocation(xPlayer, yPlayer);

    }

    @Override //מריץ רוחות
    public void run() {
        while (true) {
            try {
                Thread.sleep(300);
                move();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //עדכון תמונה לפי הכיוון החדש
    public void updateImage(String path) {
        this.pathImage = path;
        // טעינת התמונה לפי ה-path החדש
        ImageIcon newIcon = new ImageIcon(pathImage);
        Image image = newIcon.getImage();
        Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        this.imageIcon = new ImageIcon(scaledImage);

        // עדכון התמונה ב-JLabel
        this.label.setIcon(imageIcon);
    }



// בתהליך
//    public void massage() {
//        if (Pacman2.lives == 0){
//            int choice = JOptionPane.showConfirmDialog(this, "Sorry, you lost. \n Your score is: " + DefaultMap.score + "\n" + "You want to continue?",
//                    "FINISH", YES_NO_OPTION);
//            if (choice == YES_OPTION) {
//                Main.restartGame();
//
//            } else {
//                System.exit(0);
//            }
//        }
//
//    }

    //יצירת רוח בהתאם לצבע
    public static Ghost createGhost(String color, Pacman2 pacman){
        switch (color){
            case "green" -> {
                return new Ghost(275, 275, pacman, greenPathLeft, color);
            }
            case "pink" -> {
                return new Ghost(225, 275, pacman, pinkPathLeft, color);
            }
        }
        return null;
    }


}
