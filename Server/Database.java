import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

//server side
//communicates with Entry and AccountManager
public class Database {
    Connection conn;
    
    public Database() {
        //create the SQL library if not created
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:serverdata.db");
            String cmd = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY," +
                    "name STRING," +
                    "username STRING," +
                    "password STRING," +
                    "money INTEGER" +
                    "flipCount INTEGER);";
            executeUpdate(cmd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //database communication
    private ResultSet executeQuery(String cmd) {
        try {
            return conn.createStatement().executeQuery(cmd);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void executeUpdate(String cmd) {
        try {
            conn.createStatement().executeUpdate(cmd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createEntry(String username) {
        executeUpdate(String.format("INSERT INTO users (username) VALUES ('%s');", username));
    }
    public int getID(String username) {
        String cmd = "SELECT id FROM users WHERE username='"+username+"'";
        ResultSet rs = executeQuery(cmd);
        try {
            int resultsFetched = rs.getFetchSize();
            if (resultsFetched == 0) {
                return -1; //username not found
            } else if (resultsFetched == 1) {
                //good
                rs.absolute(1);
                return rs.getInt("id");
            } else { //username overlap (uh oh)
                System.out.println("CRITICAL: USERNAME OVERLAP");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public boolean fetchEntry(Entry e) {
        String username = (String)e.getField(Field.USERNAME);
        int id = getID(username);
        String cmd = "SELECT * FROM users WHERE id="+id;
        try {
            ResultSet rs = executeQuery(cmd);
            if (rs.getFetchSize() != 1) {
                return false; //unsuccessful fetch
            }

            for (Field f : Field.values()) {
                e.setField(f, rs.getObject(f.getName(), f.getType()));
            }
            return true; //successful fetch
        } catch (SQLException err) {
            err.printStackTrace();
            return false; //unsuccessful fetch
        }
    }

    public void updateValue(Entry e, Field f) {
        String cmd = String.format("UPDATE users SET %s = %s WHERE id = %d;", e.getField(Field.NAME).toString(), f.SQL(e.getField(f).toString()), e.getField(Field.ID));
        executeUpdate(cmd);
    }

    public void deleteEntry(Entry e) {
        String cmd = String.format("DELETE FROM users WHERE id = %d;", (int)e.getField(Field.ID));
        executeUpdate(cmd);
    }

    /*
    public void newEntry(String name, String username, String password) {
    }

    public void removeEntry(int index) {

    }


    public Entry addEntry() {
        Entry e = new Entry(this);
        return e;
    }
    public void updateEntry(int index) {
        entries.get(index).update();
    }

    public void readEntries() {
        entries.clear();
        try {
            ResultSet rs = executeQuery("SELECT * FROM users;");
            while(rs.next()){
                Entry e = new Entry(this, false);
                for (Field f : Field.values()) {
                    e.setField(f, rs.getObject(f.getName(), f.getType()));
                }
                entries.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public TreeMap<String, Integer> getLeaderboard() {
        TreeMap<String, Integer> leaderboard = new TreeMap<String, Integer>();



        return leaderboard;
    }

    */
}