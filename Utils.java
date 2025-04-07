import java.util.Scanner;

public class Utils {
    public static void printHeader(String title) {
        System.out.println("\n===============================");
        System.out.println("📌 " + title);
        System.out.println("===============================\n");
    }

    public static void pause() {
        System.out.print("\nPress Enter to continue...");
        new Scanner(System.in).nextLine();
    }
}
