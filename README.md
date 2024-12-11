# UDP Chat Application

A simple UDP-based chat application that allows multiple clients to communicate with each other through a server. This project demonstrates the use of UDP sockets in Java for communication and includes a GUI for both the server and the clients.

## Features

- **UDP Communication**: Clients communicate with each other via a central server using UDP.
- **Client Identification**: Each client has a unique ID that is displayed alongside their messages.
- **Graphical User Interface (GUI)**: Easy-to-use GUI for both server and clients.
- **Message Broadcasting**: Messages sent by one client are broadcasted to all other connected clients.

## How It Works

1. **Server**
   - The server listens on a specific port (default: `9876`) for incoming messages from clients.
   - It maintains a list of connected clients, identified by their IP address, port, and unique ID.
   - When a message is received, the server broadcasts it to all other connected clients, including the sender's ID.

2. **Clients**
   - Each client assigns itself a unique ID upon starting.
   - The client sends messages to the server, which then forwards the messages to other clients.
   - Incoming messages from other clients are displayed in the GUI.

## Usage

### Running the Server
1. Compile the server code:
   ```
   javac UDPServer.java
   ```
2. Run the server:
   ```
   java UDPServer
   ```
   - The server GUI will open, displaying incoming messages and broadcasting them to clients.

### Running the Client
1. Compile the client code:
   ```
   javac UDPClient.java
   ```
2. Run the client:
   ```
   java UDPClient
   ```
   - The client GUI will open, allowing users to send and receive messages.

### Example
- Start the server first.
- Open multiple clients and connect them to the server.
- Send messages from one client; the server will forward the messages to all other clients, displaying the sender's ID.

## Code Overview

### Server Code (`UDPServer.java`)
- Listens for incoming messages using a `DatagramSocket`.
- Maintains a `Set` of client addresses to track connected clients.
- Broadcasts incoming messages to all clients except the sender.

### Client Code (`UDPClient.java`)
- Sends messages to the server using a `DatagramSocket`.
- Listens for incoming messages from the server in a separate thread.
- Displays messages in a simple GUI.

## Limitations

- No encryption: Messages are sent in plain text.
- No guarantee of message delivery (inherent limitation of UDP).
- No message ordering (messages may arrive out of order).

