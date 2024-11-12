import java.util.Scanner;

public class Leaky_Bucket {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get inputs
        System.out.print("Enter Bucket Capacity: ");
        int bucketCapacity = scanner.nextInt();

        System.out.print("Enter Constant Leak Rate: ");
        int leakRate = scanner.nextInt();

        System.out.print("Enter Number of Packets: ");
        int numPackets = scanner.nextInt();

        int[] inputPackets = new int[numPackets];
        System.out.println("Enter Packet Sizes:");
        for (int i = 0; i < numPackets; i++) {
            inputPackets[i] = scanner.nextInt();
        }

        // Process the input packets
        int currentPackets = 0;
        for (int packet : inputPackets) {
            System.out.println("\nInput: " + packet + " packets");
            currentPackets += packet;

            // Overflow check
            if (currentPackets > bucketCapacity) {
                System.out.println("Overflow! Dropped " + (currentPackets - bucketCapacity) + " packets.");
                currentPackets = bucketCapacity;
            }

            // Leak packets
            currentPackets = Math.max(0, currentPackets - leakRate);
            System.out.println("After leaking " + leakRate + " packets, bucket contains: " + currentPackets
                    + " packets.");
        }
        scanner.close();
    }
}
