import java.io.*;
import java.util.*;

public class FileHandler {
    public static void saveUserData(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(user.username + "_data.txt"))) {
            for (Expense e : user.expenses) {
                writer.println(e.name + "," + e.cost + "," + e.value + "," + e.category);
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving data.");
        }
    }

    public static void loadUserData(User user) {
        File file = new File(user.username + "_data.txt");
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] parts = sc.nextLine().split(",");
                if (parts.length == 4) {
                    Expense e = new Expense(parts[0], Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]), parts[3]);
                    user.expenses.add(e);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error loading data.");
        }
    }
}
