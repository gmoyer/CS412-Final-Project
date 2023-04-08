import java.security.MessageDigest;

//server and client side
//communicates with SocketThread, Database, and Entry
public class AccountManager {
    private String errMessage;
    private Database database;
    private Entry entry; //active account entry

    public AccountManager() {
        database = new Database();
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

    //Client side. Pulled from https://stackoverflow.com/a/11009612
    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) 
                  hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex);
        }
    }
}
