import java.io.*;
import java.util.*;

public class PasswordManager {
    static Scanner sc = new Scanner(System.in);
    static List<PasswordEntry> entries = new ArrayList<>();
    static final String FILE_NAME = "password_data.txt";

    public static void main(String[] args) {
        loadFromFile();

        while (true) {
            System.out.println("\n==== Password Manager ====");
            System.out.println("1. Add Entry");
            System.out.println("2. View All");
            System.out.println("3. Search");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> addEntry();
                case 2 -> viewAll();
                case 3 -> search();
                case 4 -> {
                    saveToFile();
                    System.out.println("Exiting... Saved data.");
                    return;
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    static void addEntry() {
        System.out.print("Enter website: ");
        String website = sc.nextLine();
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        PasswordEntry entry = new PasswordEntry(website, username, password);
        entries.add(entry);
        System.out.println("Entry added!");
    }

    static void viewAll() {
        if (entries.isEmpty()) {
            System.out.println("No entries found.");
        } else {
            System.out.println("\nSaved Passwords:");
            for (PasswordEntry e : entries) {
                System.out.println(e);
            }
        }
    }

    static void search() {
        System.out.print("Enter website to search: ");
        String website = sc.nextLine();
        boolean found = false;

        for (PasswordEntry e : entries) {
            if (e.getWebsite().equalsIgnoreCase(website)) {
                System.out.println("\nMatch Found:\n" + e);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No entry found for: " + website);
        }
    }

    static void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                PasswordEntry entry = PasswordEntry.fromFileFormat(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }
        } catch (IOException e) {
            System.out.println("No saved data found.");
        }
    }

    static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (PasswordEntry entry : entries) {
                writer.println(entry.toFileFormat());
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }
}
