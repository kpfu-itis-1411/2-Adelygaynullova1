package semestrov.testclient.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGame extends JFrame {
    private JTextField nameField;
    private JButton connectButton;
    private String clientName;
    private boolean isSubmitted = false;

    public StartGame() {
        setTitle("Ping Pong");
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.decode("#c96568"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel instructionLabel = new JLabel("Введите ваше имя, для начала игры:");
        add(instructionLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridy = 1;
        add(nameField, gbc);

        connectButton = new JButton("Подключиться");
        gbc.gridy = 2;
        add(connectButton,gbc);

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User1\\IdeaProjects\\secondSemestrovWork\\src\\main\\resources\\pingPong\\gameOver.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        gbc.gridy = 3;

        add(imageLabel,gbc);

        connectButton.setForeground(Color.BLACK);
        connectButton.setBackground(Color.WHITE);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientName = nameField.getText();
                isSubmitted = true;
                synchronized (StartGame.this) {
                    StartGame.this.notifyAll();
                }
                dispose();
            }
        });

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public synchronized String getClientName() {
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

