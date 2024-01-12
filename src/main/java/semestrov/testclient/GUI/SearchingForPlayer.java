package semestrov.testclient.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchingForPlayer extends JFrame {
    private JTextField nameField;
    private JTextField nameSecondPlayer;
    private JButton connectButton;
    private String clientName;
    private boolean isSubmitted = false;

    public SearchingForPlayer() {
        setTitle("Поиск игрока");
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.decode("#FEDDE5"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel inputName = new JLabel("Введите имя второго игрока:");
        add(inputName, gbc);

        nameField = new JTextField(20);
        gbc.gridy = 1;
        add(nameField,gbc);

        connectButton = new JButton("Найти");
        gbc.gridy = 2;
        add(connectButton,gbc);

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User1\\IdeaProjects\\secondSemestrovWork\\src\\main\\resources\\pingPong\\player.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        gbc.gridy = 3;
        add(imageLabel,gbc);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientName = nameField.getText();
                isSubmitted = true;
                synchronized (SearchingForPlayer.this) {
                    SearchingForPlayer.this.notifyAll();
                }
                dispose();
            }
        });

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public synchronized String getSecondPlayerName() {
        while (!isSubmitted) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return clientName;
    }
}

