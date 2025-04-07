import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    private static User currentUser;

    public static void main(String[] args) {
        // Load user data (hardcoded for now)
        String username = JOptionPane.showInputDialog(null, "Enter your username:");
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username cannot be empty.");
            return;
        }

        currentUser = new User(username);
        FileHandler.loadUserData(currentUser);

        // Main Frame
        JFrame frame = new JFrame("Smart Personal Finance Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(250, 245, 255)); // Soft pastel background

        // Title
        JLabel title = new JLabel("\uD83D\uDDC4 Smart Personal Finance Planner \uD83D\uDDD2", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(72, 61, 139));
        title.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        frame.add(title, BorderLayout.NORTH);

        // Button Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        panel.setOpaque(false); // Transparent for background effect

        // Stunning Buttons 
        panel.add(createGradientButton("Add Expense", new Color(255, 182, 193), new Color(255, 105, 180)));
        panel.add(createGradientButton("View Expenses", new Color(173, 216, 230), new Color(100, 149, 237)));
        panel.add(createGradientButton("Plan with Knapsack", new Color(255, 223, 186), new Color(255, 160, 122)));
        panel.add(createGradientButton("View Summary", new Color(204, 255, 229), new Color(102, 205, 170)));
        panel.add(createGradientButton("Save Data", new Color(255, 240, 245), new Color(255, 182, 193)));
        panel.add(createGradientButton("Exit", new Color(255, 192, 203), new Color(255, 99, 71)));

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JButton createGradientButton(String text, Color startColor, Color endColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();

                GradientPaint gp = new GradientPaint(0, 0, startColor, w, h, endColor);
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, w, h, 30, 30);
                g2.setColor(Color.DARK_GRAY);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (w - fm.stringWidth(getText())) / 2;
                int y = (h - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // No border
            }
        };

        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 40));
        button.setForeground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setFont(button.getFont().deriveFont(Font.BOLD, 18f));
            }

            public void mouseExited(MouseEvent e) {
                button.setFont(button.getFont().deriveFont(Font.BOLD, 16f));
            }

            public void mouseClicked(MouseEvent e) {
                // Attach actions manually below if needed
                String label = button.getText();
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(button);

                switch (label) {
                    case "Add Expense":
                        new ExpenseForm(frame, currentUser);
                        break;
                    case "View Expenses":
                        new ExpenseViewer(frame, currentUser);
                        break;
                    case "Plan with Knapsack":
                        new KnapsackPlanner(frame, currentUser);
                        break;
                    case "View Summary":
                        new SummaryViewer(frame, currentUser);
                        break;
                    case "Save Data":
                        FileHandler.saveUserData(currentUser);
                        JOptionPane.showMessageDialog(frame, "Data saved successfully!");
                        break;
                    case "Exit":
                        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            FileHandler.saveUserData(currentUser);
                            System.exit(0);
                        }
                        break;
                }
            }
        });

        return button;
    }
}
