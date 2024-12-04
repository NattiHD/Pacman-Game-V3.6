import javax.swing.*;
import java.awt.*;

public abstract class Players extends JPanel {
    public static final int size = 25; // גודל שחקן
    // מיקומי התחלה של השחקן
    final int startX;
    final int startY;

    // המיקום הנוכחי
    int xPlayer;
    int yPlayer;

    //יצירת התמונה
    ImageIcon imageIcon;
    JLabel label;
    String pathImage;


    public Players(int xPlayer, int yPlayer, Color color, String pathImg) {
        this.xPlayer = xPlayer; // אתחול מיקום
        this.yPlayer = yPlayer;
        startX = xPlayer;
        startY = yPlayer;
        this.setLocation(xPlayer, yPlayer);

        this.setSize(size, size);
        this.setPreferredSize(new Dimension(size, size));
        this.setLayout(null); // ללא layout כדי להקפיד על מיקומים ידניים
        this.setVisible(true);
        this.setBackground(color);

        // טעינת התמונה או ה-GIF
        this.pathImage = pathImg;
        this.imageIcon = new ImageIcon(this.pathImage);

        // שינוי גודל התמונה כך שיתאים לגודל השחקן
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        this.imageIcon = new ImageIcon(scaledImage);

        // יצירת JLabel עם התמונה המותאמת
        this.label = new JLabel(this.imageIcon);

        // מיקום ה-JLabel בפאנל
        this.label.setBounds(0, 0, size, size); // מיקום עליון של ה-JLabel
        this.add(label);

        // הצגת ה-JLabel הכי עליון
        this.setComponentZOrder(label, 0); // הצגת ה-JLabel הכי עליון
    }

    // בדיקה על כל הכיוונים
    public boolean canMove(int newX, int newY) {
       return  _canMove(newX, newY) && _canMove(newX, newY + size -1)
               && _canMove(newX + size - 1, newY) && _canMove(newX + size -1, newY+size -1);
    }
    // בדיקה אם שחקן יכול לזוז
    private boolean _canMove(int newX, int newY) {
        int mapX = newX / size;
        int mapY = newY / size;
        return mapX >= 0 && mapX < DefaultMap.col
                && mapY >= 0 && mapY < DefaultMap.row
                && DefaultMap.map[mapY][mapX] != 0;
    }

    // פונקציה לזוז במערה
    public void setLocSide(int newX, int newY) {
        if (newX == 0 && newY == 250) xPlayer = 475;
        if (newX == 475 && newY == 250) xPlayer = 0;
    }

    // פונקציה אבסטרקטית להזזת השחקן
    public abstract void move() throws InterruptedException;



}
