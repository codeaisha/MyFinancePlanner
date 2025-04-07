import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ExpenseForm extends JDialog {

    public ExpenseForm(JFrame parent, User user) {
        super(parent, "➕ Add Expense", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Input Fields
        JLabel nameLabel = new JLabel("Expense Name:");
        JTextField nameField = new JTextField();

        JLabel costLabel = new JLabel("Cost (₹):");
        JTextField costField = new JTextField();

        JLabel valueLabel = new JLabel("Value (1-10):");
        JTextField valueField = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        String[] categories = {"Food", "Travel", "Bills", "Shopping", "Health", "Other"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        // Add to form
        add(nameLabel); add(nameField);
        add(costLabel); add(costField);
        add(valueLabel); add(valueField);
        add(categoryLabel); add(categoryBox);
        add(addButton); add(cancelButton);

        // Add expense logic
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String costText = costField.getText().trim();
            String valueText = valueField.getText().trim();
            String category = (String) categoryBox.getSelectedItem();

            if (name.isEmpty() || costText.isEmpty() || valueText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❗ Please fill in all fields.");
                return;
            }

            try {
                int cost = Integer.parseInt(costText);
                int value = Integer.parseInt(valueText);

                if (cost <= 0 || value <= 0) {
                    JOptionPane.showMessageDialog(this, "❗ Cost and Value must be positive.");
                    return;
                }

                Expense newExpense = new Expense(name, cost, value, category);
                user.expenses.add(newExpense);
                JOptionPane.showMessageDialog(this, "✅ Expense added successfully!");
                dispose(); // close this dialog
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "❗ Cost and Value must be numbers.");
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}
