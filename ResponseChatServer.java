import java.io.*;
import java.net.*;
import java.util.*;

public class ResponseChatServer {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static Map<String, String> responses = new HashMap<>();

    public static void main(String[] args) {
        initializeResponses();
        System.out.println("Response Chat Server is running...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeResponses() {
        responses.put("hello", "Hi there! How can I help you?");
        responses.put("hi", "Hello! What's up?");
        responses.put("how are you", "I'm good, thank you! How about you?");
        responses.put("bye", "Goodbye! Have a great day!");
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String userName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Get user name
                out.println("Enter your name:");
                userName = in.readLine();
                broadcastMessage(userName + " has joined the chat.");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("quit")) {
                        break;
                    }

                    // Respond based on predefined responses or broadcast the message
                    String response = responses.getOrDefault(message.toLowerCase(), userName + ": " + message);
                    out.println(response);
                    broadcastMessage(userName + ": " + message);
                }

                // Clean up
                broadcastMessage(userName + " has left the chat.");
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void broadcastMessage(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}
