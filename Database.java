import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

//server side
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

    public ResultSet executeQuery(String cmd) {
        try {
            return conn.createStatement().executeQuery(cmd);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void executeUpdate(String cmd) {
        try {
            conn.createStatement().executeUpdate(cmd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
