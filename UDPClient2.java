import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class UDPClient2 {
    private static JTextArea textArea;
    private static DatagramSocket socket;
    private static InetAddress serverAddress;
    private static int serverPort = 9876;
    private static int clientId; 

    public static void main(String[] args) {
        JFrame frame = new JFrame("UDP Client");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JTextField textField = new JTextField();
        JButton sendButton = new JButton("Send");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);

        try {
            socket = new DatagramSocket();
            serverAddress = InetAddress.getByName("localhost");

            // Start a thread to listen for incoming messages
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] receiveData = new byte[1024];
                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                        while (true) {
                            socket.receive(receivePacket);
                            String incomingMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                            textArea.append(incomingMessage + "\n");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }).start();

            clientId = new Random().nextInt(1000);

            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String message = textField.getText();
                    if (!message.isEmpty()) {
                        // Send message with client ID
                        String messageToSend = clientId + ": " + message;
                        textArea.append("Client " + clientId + ": " + message + "\n");
                        sendMessage(messageToSend);
                        textField.setText("");
                    }
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void sendMessage(String message) {
        try {
            DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), serverAddress, serverPort);
            socket.send(sendPacket);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
