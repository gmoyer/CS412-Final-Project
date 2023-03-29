public enum Field {
    ID ("id", Integer.class, -1),
    NAME ("name", String.class, ""),
    USERNAME ("username", String.class, ""),
    PASSWORD ("password", String.class, ""),
    MONEY ("money", Integer.class, 500),
    FLIPCOUNT ("flipCount", Integer.class, 0);

    private final String name;
    private final Class<?> type;
    private final Object defaultVal;
    private Field(String name, Class<?> type, Object defaultVal) {
        this.name = name;
        this.type = type;
        this.defaultVal = defaultVal;
    }

    public Class<?> getType(){
        return this.type;
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
