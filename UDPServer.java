import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class UDPServer {
    private static JTextArea serverTextArea;
    private static Set<InetSocketAddress> clientAddresses = new HashSet<>();
    private static Map<InetSocketAddress, Integer> clientIds = new HashMap<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("UDP Server");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        serverTextArea = new JTextArea();
        serverTextArea.setEditable(false);
        panel.add(new JScrollPane(serverTextArea), BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);

        try (DatagramSocket socket = new DatagramSocket(9876)) {
            serverTextArea.append("Server is running and waiting for connections...\n");

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket;

            while (true) {
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                InetSocketAddress clientAddress = new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort());

                clientAddresses.add(clientAddress);

                // Get client ID from the message (assuming format "ClientID: message")
                String[] parts = message.split(":", 2);
                int clientId = Integer.parseInt(parts[0].trim());
                message = parts[1].trim(); // Get the actual message

                // Save or update the client ID
                clientIds.put(clientAddress, clientId);

                serverTextArea.append("Message from Client " + clientId + " (" + clientAddress + "): " + message + "\n");

                // Send the message to all connected clients
                for (InetSocketAddress address : clientAddresses) {
                    if (!address.equals(clientAddress)) {
                        // Include client ID in the message
                        String messageToSend = "Client " + clientId + ": " + message;
                        DatagramPacket sendPacket = new DatagramPacket(messageToSend.getBytes(), messageToSend.length(), address.getAddress(), address.getPort());
                        socket.send(sendPacket);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
