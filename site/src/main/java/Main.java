import auth.AccountManager;
import database.DatabaseConnection;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        connection.connect("shop.db");
        AccountManager accountManager = new AccountManager(connection);
        //accountManager.register("alice", "secret");
        System.out.println(accountManager.authenticate("alice", "secret"));
        System.out.println(accountManager.getAccount("alice"));
    }
}
