import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ExpenseViewer extends JDialog {
    public ExpenseViewer(JFrame parent, User currentUser) {
        super(parent, "📋 All Expenses", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);

        List<Expense> expenses = currentUser.expenses;

        String[] columns = {"Name", "Cost (₹)", "Value", "Category"};
        Object[][] data = new Object[expenses.size()][4];

        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            data[i][0] = e.name;
            data[i][1] = e.cost;
            data[i][2] = e.value;
            data[i][3] = e.category;
        }

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model) {
            // Override renderer for zebra striping
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 240, 255));
                } else {
                    c.setBackground(new Color(180, 205, 255)); // selected row
                }
                return c;
            }
        };

        // Font Styling
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(200, 220, 255));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        setVisible(true);
    }
}
