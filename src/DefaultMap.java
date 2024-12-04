import javax.swing.*;
import java.awt.*;

public class DefaultMap {
    //מפת המשחק
    public static final int[][] map = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 2, 0},
            {0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},
            {4, 4, 4, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 4, 4, 4},
            {0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0},
            {4, 4, 4, 4, 1, 1, 1, 0, 0, 4, 4, 0, 0, 1, 1, 1, 4, 4, 4, 4},
            {0, 0, 0, 0, 1, 0, 1, 0, 4, 4, 4, 4, 0, 1, 0, 1, 0, 0, 0, 0},
            {4 ,4, 4, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 4, 4, 4},
            {0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 2, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 2, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public static final int row = map.length; // שורות
    public static final int col = map[0].length;//עמודות
    public static final int widF = row * Players.size, hetF = col * Players.size; //גודל החלון והמפה
    public static final int allEat = 159; // מספר הפאנלים לאכילה הכולל

    public static int score; // חישוב הניקוד
    public static int eat = allEat; //פאנלים שנשארו לאכילה
    public static JPanel[][] eatMap = new JPanel[row][col]; //מערך מייצג את מקומות האכילה

    //יצירת פאנל המפה
    public static JPanel createMap(){
        //יצירת פאנל מפת המשחק בפריסת טבלא
        JPanel map = new JPanel(new GridLayout(row, col));
        map.setBounds(0,0,widF,hetF);
        return map;
    }

    //יצירת פאנלים קטנים למילוי המפה עם איפיון של כל אחד
    public static void fillMap(JFrame game, JPanel map, boolean flag) {
        if (flag) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    JPanel jPanel = new JPanel();
                    switch (DefaultMap.map[i][j]) {
                        case 0:
                            jPanel.setBackground(Color.blue);//קירות
                            break;
                        case 1:
                            jPanel.setBackground(Color.BLACK);//מעבר
                            JPanel eat = addEat(jPanel, Color.YELLOW); //אכילה רגיל
                            eatMap[i][j] = eat; //סעמון מפת האכילה במקום שיש אוכל
                            break;
                        case 2:
                            jPanel.setBackground(Color.BLACK);//אכילה מיוחד
                            JPanel et = addEat(jPanel, Color.MAGENTA);
                            eatMap[i][j] = et; //אכילה מיוחד
                            break;
                        case 4:
                            jPanel.setBackground(Color.black);//קיר שחור
                            break;
                    }
                    map.add(jPanel);
                }
                game.add(map);
                game.pack();
            }
        }
    }

    //ניהול שכבות במפה והוספה לחלון
    public static void layerManager(JFrame game, JPanel map, Players pacman) {
        JLayeredPane jLayeredPane = new JLayeredPane();//מנהל שכבות בפאנלים
        jLayeredPane.setLayout(null); //פריסה
        //קובע גודל שכבה
        jLayeredPane.setPreferredSize(new Dimension(widF, hetF));
        map.setBounds(0, 0, widF, hetF);//קביעת מיקום מפה
        //השכבה הראשונה היא המפה בגודל החלון ומיקום השכבה - 0
        //בגלל שהוספנו למפה הרבנ פאנלים הם נחשבים כחלק מהשכבה
        jLayeredPane.add(map, JLayeredPane.DEFAULT_LAYER);
        //הוספת השכבה של השחקן - שכבה 2
        jLayeredPane.add(pacman, JLayeredPane.PALETTE_LAYER);
        //הוספת רוחות מהמערך ברמת השחקן
        jLayeredPane.add(Ghost.ghosts.get(0), JLayeredPane.PALETTE_LAYER);
        jLayeredPane.add(Ghost.ghosts.get(1), JLayeredPane.PALETTE_LAYER);
        //הוספת כיתוב ניקוד וחיים על הפאנל הראשי
        jLayeredPane.add(Massage.score, JLayeredPane.PALETTE_LAYER);
        jLayeredPane.add(Massage.livesLeb, JLayeredPane.PALETTE_LAYER);
        game.add(jLayeredPane); //הוספת מנהל השכבות לחלון
    }

    //הוספת אוכל למעבר
    public static JPanel addEat(JPanel trans, Color color) {
        JPanel eat = new JPanel();
        //הוספת פאנל לפאנל במיקום מסויים
        eat.setPreferredSize(new Dimension(10, 10));
        eat.setBackground(color);
        trans.add(eat);
        return eat;
    }

    //הורדת אוכל אחרי שהשחקן עבר
    public static void deleteEat(int mapX, int mapY){
        if (eatMap[mapY][mapX] != null) {
            eatMap[mapY][mapX].setVisible(false);//כבה את הפאנל הקטן
            eatMap[mapY][mapX] = null; //הסר אותו לגמרי
            score += 10; //הוסף לניקוד
            eat--; //הורד מכמות האוכל הכולל
            outGhost(); //קריאה להוצאת רוח כל רב מכמות האוכל שנאכל
        }
        vicMassage(); //הודעת ניצחון כשכל האוכל נאכל
//        Massage massage = new Massage();
//        massage.massageWin();

    }

    //יציאת רוח מהתור כל רבע מכמות האוכל שנאכל
    public static void outGhost(){
        if (eat % (allEat / 4) == 0){ //
            if (!Ghost.ghostQueue.isEmpty()) {
                Ghost.ghostQueue.poll().isCanMove = true;

            }
        }
    }

    //הודעת ניצחון אם נגמר האוכל
    public static void vicMassage(){
        if (eat == 0)  Massage.massageVic();
    }

}

