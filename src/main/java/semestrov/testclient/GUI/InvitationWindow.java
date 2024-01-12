package semestrov.testclient.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvitationWindow extends JDialog {

    private boolean status = false;
    private boolean isSubmitted = false;
    public InvitationWindow(JFrame parent, String invitingPlayer) {
        super(parent, "Приглашение в игру", true);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.decode("#FEDDE5"));
        setSize(400, 400);
        setLocationRelativeTo(parent);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        JLabel messageLabel = new JLabel(invitingPlayer + " приглашает вас в игру!");
        add(messageLabel, gbc);

        gbc.gridy = 1;
        JButton acceptButton = new JButton("Принять");
        add(acceptButton,gbc);

        gbc.gridy = 2;
        JButton declineButton = new JButton("Отклонить");
        add(declineButton,gbc);



        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status = true;
                isSubmitted = true;
                synchronized (InvitationWindow.this) {
                    InvitationWindow.this.notifyAll();
                }
                dispose();
            }
        });

        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status = false;
                isSubmitted = true;
                synchronized (InvitationWindow.this) {
                    InvitationWindow.this.notifyAll();
                }
                dispose();
            }
        });
    }
    public synchronized boolean getStatusInvitation() {
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

