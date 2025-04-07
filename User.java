import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;
    public List<Expense> expenses;

    public User(String username) {
        this.username = username;
        this.expenses = new ArrayList<>();
    }
}
