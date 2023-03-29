import java.util.HashMap;

//database entry, server side

public class Entry {
    private HashMap<String, Object> fields;
    private Database database;

    public Entry(Database db, boolean newEntry) {
        database = db;
        fields = new HashMap<String, Object>();
        /*
        for (Field f : Field.values()) {
            fields.put(f.getName(), f.getDefault());
        }
        */
        if (newEntry) {
            database.executeUpdate("INSERT INTO users DEFAULT VALUES");
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

    }
}
