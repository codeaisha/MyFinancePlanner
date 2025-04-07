public class Main {
    public static void main(String[] args) {
        // Use OS-native look and feel for better appearance
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("⚠️ Unable to apply system look and feel.");
        }

        // Launch the Swing GUI
        new GUI();
    }
}
