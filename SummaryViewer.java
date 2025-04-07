import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SummaryViewer extends JDialog {

    public SummaryViewer(JFrame parent, User user) {
        super(parent, "📊 Expense Summary", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(area);

        add(scrollPane, BorderLayout.CENTER);

        // Build Summary
        StringBuilder summary = new StringBuilder();
        int total = 0;
        HashMap<String, Integer> categoryMap = new HashMap<>();

        for (Expense e : user.expenses) {
            total += e.cost;
            categoryMap.put(e.category, categoryMap.getOrDefault(e.category, 0) + e.cost);
        }

        summary.append("📊 Total Spent: ₹").append(total).append("\n\n");

        summary.append("📁 Category Breakdown:\n");
        for (Map.Entry<String, Integer> entry : categoryMap.entrySet()) {
            summary.append("🔹 ").append(entry.getKey())
                    .append(": ₹").append(entry.getValue()).append("\n");
        }

        if (!user.expenses.isEmpty()) {
            Expense maxValExpense = user.expenses.get(0);
            for (Expense e : user.expenses) {
                if (e.value > maxValExpense.value) {
                    maxValExpense = e;
                }
            }

            summary.append("\n🏆 Most Valuable Expense:\n");
            summary.append("✅ ").append(maxValExpense.name)
                    .append(" | ₹").append(maxValExpense.cost)
                    .append(" | Value: ").append(maxValExpense.value)
                    .append(" | Category: ").append(maxValExpense.category).append("\n");
        } else {
            summary.append("\n(No expenses available.)");
        }

        area.setText(summary.toString());
        setVisible(true);
    }
}
