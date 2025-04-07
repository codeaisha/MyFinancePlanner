import java.util.*;

public class GreedyPlanner {
    public static void plan(List<Expense> expenses, int budget) {
        Utils.printHeader("Greedy Planner (Fractional Knapsack)");

        expenses.sort((a, b) -> Double.compare((double)b.value / b.cost, (double)a.value / a.cost));

        double totalValue = 0;
        for (Expense e : expenses) {
            if (budget >= e.cost) {
                budget -= e.cost;
                totalValue += e.value;
                System.out.println("✔ Selected: " + e.name + " (Full)");
            } else {
                double fraction = (double)budget / e.cost;
                totalValue += e.value * fraction;
                System.out.printf("✔ Selected: %s (%.2f%%)\n", e.name, fraction * 100);
                break;
            }
        }
        System.out.printf("💰 Total Value Achieved: %.2f\n", totalValue);
    }
}
