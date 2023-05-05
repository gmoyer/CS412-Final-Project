import java.util.ArrayList;

//server and client side
//communicates with SocketThread, Database, and Entry
public class AccountManager {
    private Database database;
    private Entry entry; //active account entry
    private ArrayList<String> leaderboard;

    public AccountManager() {
        database = Database.getInstance();
        entry = null;
    }

    public ReqResult createAccount(String name, String username, String password, String confirmPassword) {

        if (username.length() == 0 || name.length() == 0 || password.equals(Util.sha256("")))
            return ReqResult.INCOMPLETE;

        if (!password.equals(confirmPassword)) {
            return ReqResult.PASS_DO_NOT_MATCH;
        }
        if (database.getID(username) != -1) {
            return ReqResult.NON_UNIQUE_USERNAME;
        }

        if (username.length() > 10) {
            return ReqResult.LONG_USERNAME;
        }
        if (name.length() > 15) {
            return ReqResult.LONG_NAME;
        }

        if (!(Util.validString(name) && Util.validString(username) && Util.validString(password) && Util.validString(confirmPassword)) )
            return ReqResult.BAD_CHARACTERS;
        



        entry = new Entry(username, true);
        entry.setField(Field.NAME, name);
        entry.setField(Field.PASSWORD, password);

        entry.setField(Field.ACTIVE, 1);
        return ReqResult.SUCCESS;
    }

    public ReqResult loadAccount(String username, String password) {

        if (username.length() == 0 || password.equals(Util.sha256("")))
            return ReqResult.INCOMPLETE;

        if (database.getID(username) == -1) {
            return ReqResult.BAD_AUTH;
        }

        entry = new Entry(username, false);

        //System.out.println("Comparing " + entry.getField(Field.PASSWORD) + " and " + password);

        if (!entry.getField(Field.PASSWORD).equals(password))
            return ReqResult.BAD_AUTH;

        if ((boolean)entry.getField(Field.ACTIVE))
            return ReqResult.ALREADY_ACTIVE;

        entry.setField(Field.ACTIVE, 1);
        return ReqResult.SUCCESS;
    }

    public Entry getActiveEntry() {
        return entry;
    }
    public boolean signedIn() {
        return entry != null;
    }

    public ArrayList<String> getLeaderboard(boolean saveOutput) {
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
        if (saveOutput)
            leaderboard = leaderboardText;
        return leaderboardText;
    }
    public ArrayList<String> getLeaderboard() {
        return getLeaderboard(false);
    }

    public boolean leaderboardUpdated() {
        ArrayList<String> postLeaderboard = getLeaderboard();
        if (leaderboard.size() != postLeaderboard.size()) {
            return true;
        }
        for (int i = 0; i < leaderboard.size(); i++) {
            if (!leaderboard.get(i).equals(postLeaderboard.get(i)))
                return true;
        }
        return false;
    }


    public void signout() {
        if (entry != null)
            entry.setField(Field.ACTIVE, false);
        entry = null;
    }
}
