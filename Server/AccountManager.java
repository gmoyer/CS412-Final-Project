
//server and client side
//communicates with SocketThread, Database, and Entry
public class AccountManager {
    private String errMessage;
    private Database database;
    private Entry entry; //active account entry
    private static AccountManager am;

    private AccountManager() {
        database = Database.getInstance();
    }

    public static AccountManager getInstance() {
        if (am == null)
            am = new AccountManager();
        return am;
    }

    public boolean createAccount(String name, String username, String password, String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            errMessage = "Passwords must match";
            return false;
        }
        if (database.getID(username) != -1) {
            errMessage = "Username must be unique";
            return false;
        }

        entry = new Entry(database, username, true);
        entry.setField(Field.NAME, name);
        entry.setField(Field.PASSWORD, password);
        
        return true;
    }

    public boolean loadAccount(String username, String password) {

        if (database.getID(username) == -1) {
            errMessage = "Username not found in system";
            return false;
        }

        entry = new Entry(database, username, false);

        if (entry.getField(Field.PASSWORD).equals(password)) {
            return true;
        } else {
            errMessage = "Incorrect password";
            return false;
        }
    }

    public String getErrMessage() {
        return errMessage;
    }
}
