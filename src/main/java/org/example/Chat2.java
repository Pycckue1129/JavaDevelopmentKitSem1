package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.*;


public class Chat2 extends JFrame {
    private static final int WINDOW_HEIGHT = 500;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_POS_X = 50;
    private static final int WINDOW_POSY = 50;
    JButton btnSend = new JButton("Send");
    JLabel lblLogin = new JLabel("Login:");
    JLabel lblPassword = new JLabel("Password:");
    JLabel lblIP = new JLabel("IP:");
    JLabel lblMessage = new JLabel("Messages:");
    JTextField txtFieldLogin = new JTextField();
    JTextField txtFieldPassword = new JTextField();
    JTextField txtFieldIP = new JTextField();
    JTextField txtFieldMessage = new JTextField();
    JTextArea areaMessage = new JTextArea();
    JPanel panServer = new JPanel(new GridLayout(6, 1));
    JPanel panMessage = new JPanel(new GridLayout(1, 2));
    JPanel panClient = new JPanel(new GridLayout(2, 1));
    String message;

    Chat2() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POS_X, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("ChatWindow");
        setResizable(true);
        panServer.add(lblLogin);
        panServer.add(txtFieldLogin);
        panServer.add(lblPassword);
        panServer.add(txtFieldPassword);
        panServer.add(lblIP);
        panServer.add(txtFieldIP);
        panMessage.add(lblMessage);
        panMessage.add(areaMessage);
        panClient.add(txtFieldMessage);
        panClient.add(btnSend);

        ActionListener sendMessageListener = e -> sendMessage();

        btnSend.addActionListener(sendMessageListener);

        txtFieldMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        setLayout(new GridLayout(3, 1));
        add(panServer);
        add(panMessage);
        add(panClient);
        loadChatHistory();
        setVisible(true);
    }

    private void sendMessage() {
        message = txtFieldLogin.getText() + ": " + txtFieldMessage.getText() + "\n";
        areaMessage.append(message);
        System.out.println("Отправлено сообщение: " + message);
        saveChatHistory(message);
    }
    private void saveChatHistory(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("chat_history.txt", true))) {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadChatHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("chat_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                areaMessage.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Chat2();
    }
}