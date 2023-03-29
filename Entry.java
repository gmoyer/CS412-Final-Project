import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

//database entry, server side

public class Entry {
    private HashMap<String, Object> fields;
    private Database database;

    public Entry(Database db, String username, boolean newEntry) {
        this.database = db;
        fields = new HashMap<String, Object>();

        //populate with default values
        for (Field f : Field.values()) {
            fields.put(f.getName(), f.getDefault());
        }

        //populate username
        setField(Field.USERNAME, username);

        if (newEntry) {
            create();
        } else {
            fetch();
        }
    }

    private void create() { //new entry
        String username = (String)getField(Field.USERNAME);
        database.executeUpdate(String.format("INSERT INTO users (username) VALUES ('%s');", username));

        fields.put("id", database.getID(username)); //get primary key
    }
    private boolean fetch() { //existing entry
        String username = (String)getField(Field.USERNAME);
        int id = database.getID(username);
        String cmd = "SELECT * FROM users WHERE id="+id;
        try {
            ResultSet rs = database.executeQuery(cmd);
            if (rs.getFetchSize() != 1) {
                return false; //unsuccessful fetch
            }

            for (Field f : Field.values()) {
                setField(f, rs.getObject(f.getName(), f.getType()));
            }
            return true; //successful fetch
        } catch (SQLException e) {
            e.printStackTrace();
            return false; //unsuccessful fetch
        }
    }


    //getters and setters
    public Object getField(Field f) {
        return fields.get(f.getName());
    }

    public void setField(Field f, Object val) {
        fields.put(f.getName(), val);
        //update database
        update();
    }

    public void update() {
        for (Field f : Field.values()) {
            String cmd = String.format("UPDATE users SET %s = %s WHERE id = %d;", getField(Field.NAME).toString(), f.SQL(getField(f).toString()), getField(Field.ID));
            database.executeUpdate(cmd);
        }
    }

    public void delete() {
        String cmd = String.format("DELETE FROM users WHERE id = %d;", (int)getField(Field.ID));
        database.executeUpdate(cmd);
    }
}
