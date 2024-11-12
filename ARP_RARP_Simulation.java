import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class ARP_RARP_Simulation {

    private HashMap<String, String> arpTable = new HashMap<>();
    private HashMap<String, String> rarpTable = new HashMap<>();
    public void loadARPTable(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                String[] parts = line.trim().split("\\s+");
                if (parts.length >= 2) {
                    String ip = parts[0];
                    String mac = parts[1];
                    arpTable.put(ip, mac);  
                    rarpTable.put(mac, ip);  
                }
            }
            System.out.println("ARP table loaded from " + filePath);
        } catch (IOException e) {
            System.out.println("Error loading ARP table: " + e.getMessage());
        }
    }

    public void simulateARPAndRARP() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter IP Address to resolve MAC (ARP): ");
        String ip = scanner.nextLine();
        if (arpTable.containsKey(ip)) {
            System.out.println("Resolved MAC Address: " + arpTable.get(ip));
        } else {
            System.out.println("Resolved MAC Address: IP Address not found in ARP table");
        }

        System.out.println("Enter MAC Address to resolve IP (RARP): ");
        String mac = scanner.nextLine();
        if (rarpTable.containsKey(mac)) {
            System.out.println("Resolved IP Address: " + rarpTable.get(mac));
        } else {
            System.out.println("Resolved IP Address: MAC Address not found in RARP table");
        }

        scanner.close();
    }

    public static void main(String[] args) throws IOException {
        ARP_RARP_Simulation arpRarpSimulation = new ARP_RARP_Simulation();
        String filePath = "C:\\Java\\arp_table.txt";  
        arpRarpSimulation.loadARPTable(filePath);    
        arpRarpSimulation.simulateARPAndRARP();
    }
}
