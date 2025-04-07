import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class KnapsackPlanner extends JDialog {

    public KnapsackPlanner(JFrame parent, User user) {
        super(parent, "📈 Knapsack Planner", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);

        // Budget input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel budgetLabel = new JLabel("Enter your budget (₹): ");
        JTextField budgetField = new JTextField(10);
        JButton planBtn = new JButton("Plan");

        inputPanel.add(budgetLabel);
        inputPanel.add(budgetField);
        inputPanel.add(planBtn);

        // Output area
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button action
        planBtn.addActionListener(e -> {
            String input = budgetField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❗ Please enter a budget.");
                return;
            }

            try {
                int budget = Integer.parseInt(input);
                if (budget <= 0) {
                    throw new NumberFormatException();
                }

                String result = runKnapsack(user, budget);
                resultArea.setText(result);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❗ Please enter a valid positive number.");
            }
        });

        setVisible(true);
    }

    private String runKnapsack(User user, int budget) {
        int n = user.expenses.size();
        if (n == 0) return "No expenses available to plan.";

        int[] cost = new int[n];
        int[] value = new int[n];

        for (int i = 0; i < n; i++) {
            cost[i] = user.expenses.get(i).cost;
            value[i] = user.expenses.get(i).value;
        }

        int[][] dp = new int[n + 1][budget + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= budget; w++) {
                if (cost[i - 1] <= w) {
                    dp[i][w] = Math.max(value[i - 1] + dp[i - 1][w - cost[i - 1]], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Backtrack to find selected items
        ArrayList<Expense> selected = new ArrayList<>();
        int w = budget;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                Expense e = user.expenses.get(i - 1);
                selected.add(e);
                w -= e.cost;
            }
        }

        // Build result string
        StringBuilder sb = new StringBuilder();
        sb.append("📊 Optimal Value: ").append(dp[n][budget]).append("\n");
        sb.append("📋 Selected Expenses:\n");

        if (selected.isEmpty()) {
            sb.append("No expenses can be selected within this budget.");
        } else {
            for (Expense e : selected) {
                sb.append("✅ ").append(e.name)
                        .append(" | ₹").append(e.cost)
                        .append(" | Value: ").append(e.value)
                        .append(" | Category: ").append(e.category)
                        .append("\n");
            }
        }

        return sb.toString();
    }
}
