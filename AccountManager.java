import java.util.ArrayList;

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

        entry = new Entry(username, true);
        entry.setField(Field.NAME, name);
        entry.setField(Field.PASSWORD, password);
        
        return ReqResult.SUCCESS;
    }

    public ReqResult loadAccount(String username, String password) {

        if (database.getID(username) == -1) {
            return ReqResult.BAD_AUTH;
        }

        entry = new Entry(username, false);

        //System.out.println("Comparing " + entry.getField(Field.PASSWORD) + " and " + password);

        if (entry.getField(Field.PASSWORD).equals(password)) {
            return ReqResult.SUCCESS;
        } else {
            return ReqResult.BAD_AUTH;
        }
    }

    public Entry getActiveEntry() {
        return entry;
    }

    public ArrayList<String> getLeaderboard() {
        ArrayList<String> leaderboardText = new ArrayList<String>();

        ArrayList<Entry> entries = database.getAllEntries();

        int leaderboardSize = 3;
        if (entries.size() < 3)
            leaderboardSize = entries.size();

        for (int i = 0; i < leaderboardSize; i++) {
            Entry e = entries.get(i);
            String txt = "$" + e.getField(Field.MONEY) + " - " + e.getField(Field.USERNAME);
            leaderboardText.add(txt);
        }

        return leaderboardText;
    }
}
