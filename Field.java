public enum Field {
    ID ("id", "INTEGER PRIMARY KEY", -1),
    NAME ("name", "STRING", ""),
    USERNAME ("username", "STRING", ""),
    PASSWORD ("password", "STRING", ""),
    MONEY ("money", "INTEGER", 500),
    FLIPCOUNT ("flipCount", "INTEGER", 0),
    ACTIVE ("active", "BIT", 0); //active because just created

    private final String name;
    private final Class<?> type;
    private final String typeName;
    private final Object defaultVal;
    private Field(String name, String typeName, Object defaultVal) {
        this.name = name;
        this.typeName = typeName;
        this.defaultVal = defaultVal;

        switch (typeName) {
            case "INTEGER PRIMARY KEY":
                this.type = Integer.class;
                break;
            case "INTEGER":
                this.type = Integer.class;
                break;
            case "STRING":
                this.type = String.class;
                break;
            case "BIT":
                this.type = Boolean.class;
                break;
            default:
                System.out.println("Unhandled type " + typeName);
                this.type = Object.class;
        }
    }

    public Class<?> getType(){
        return this.type;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getName() {
        return this.name;
    }

    public Object getDefault() {
        return this.defaultVal;
    }

    public String SQL(String n) {
        if (this.type == String.class) {
            return "'" + n + "'";
        } else {
            return n;
        }
    }
}
