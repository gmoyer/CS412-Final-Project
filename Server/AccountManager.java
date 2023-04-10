
//server and client side
//communicates with SocketThread, Database, and Entry
public class AccountManager {
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

    public ReqResult createAccount(String name, String username, String password, String confirmPassword) {

        if (!password.equals(confirmPassword)) {
            return ReqResult.PASS_DO_NOT_MATCH;
        }
        if (database.getID(username) != -1) {
            return ReqResult.NON_UNIQUE_USERNAME;
        }

        entry = new Entry(database, username, true);
        entry.setField(Field.NAME, name);
        entry.setField(Field.PASSWORD, password);
        
        return ReqResult.GOOD_AUTH;
    }

    public ReqResult loadAccount(String username, String password) {

        if (database.getID(username) == -1) {
            return ReqResult.BAD_AUTH;
        }

        entry = new Entry(database, username, false);

        if (entry.getField(Field.PASSWORD).equals(password)) {
            return ReqResult.GOOD_AUTH;
        } else {
            return ReqResult.BAD_AUTH;
        }
    }

    public Entry getActiveEntry() {
        return entry;
    }
}
