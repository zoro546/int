import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
        String hostname = "localhost"; // The server's hostname or IP address
        int port = 12345; // The port number on which the server is listening

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the echo server");

            // Create input and output streams for the connection to the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            System.out.println("Type a message to send to the server, or 'exit' to quit:");
            while ((userInput = consoleReader.readLine()) != null) {
                if (userInput.equalsIgnoreCase("exit")) {
                    break; // Exit the loop if the user types 'exit'
                }
                out.println(userInput); // Send user input to the server
                System.out.println("Server response: " + in.readLine()); // Read and print the server's response
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + hostname);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}
