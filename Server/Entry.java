import java.util.HashMap;

//database entry, server side
//communicates with Database
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
        database.createEntry(username);
        fields.put("id", database.getID(username)); //get primary key
    }
    private boolean fetch() { //existing entry
        return database.fetchEntry(this);
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
            database.updateValue(this, f);
        }
    }

    public void delete() {
        database.deleteEntry(this);
    }
}
