package semestrov.testclient.GUI;

import javax.swing.*;
import java.awt.*;

public class WaitingWindow extends JFrame {

    public WaitingWindow() {

    }

    public void remove(){
        dispose();
    }

    public void init() {
        setTitle("Окно ожидания");
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.decode("#FEDDE5"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(new JLabel("Ожидание второго игрока"),gbc);

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User1\\IdeaProjects\\secondSemestrovWork\\src\\main\\resources\\pingPong\\loading.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        gbc.gridy = 1;
        add(imageLabel,gbc);

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

