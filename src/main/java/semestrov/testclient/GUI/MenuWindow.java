package semestrov.testclient.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame {
    private JButton findOtherPlayersButton;
    private JButton existGameButton;
    private int status = 0;
    private boolean isSubmitted = false;

    public MenuWindow() {

        setTitle("Меню");
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.decode("#FEDDE5"));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        findOtherPlayersButton = new JButton("Найти игрока");
        add(findOtherPlayersButton, gbc);

        existGameButton = new JButton("Выйти из игры");
        gbc.gridy = 1;
        add(existGameButton, gbc);



        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User1\\IdeaProjects\\secondSemestrovWork\\src\\main\\resources\\pingPong\\menu.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(170, 170, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        gbc.gridy = 2;
        add(imageLabel,gbc);

        findOtherPlayersButton.setForeground(Color.BLACK);
        existGameButton.setForeground(Color.BLACK);
        findOtherPlayersButton.setBackground(Color.WHITE);
        existGameButton.setBackground(Color.WHITE);


        findOtherPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status = 1;
                isSubmitted = true;
                synchronized (MenuWindow.this) {
                    MenuWindow.this.notifyAll();
                }
                dispose();
            }
        });
        existGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status = 2;
                isSubmitted = true;
                synchronized (MenuWindow.this) {
                    MenuWindow.this.notifyAll();
                }
                dispose();
            }
        });

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public synchronized int getStatusMenu() {
        while (!isSubmitted) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return status;
    }
}

