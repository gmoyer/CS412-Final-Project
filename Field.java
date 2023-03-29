public enum Field {
    ID ("id", Integer.class),
    NAME ("name", String.class),
    USERNAME ("username", String.class),
    PASSWORD ("password", String.class),
    MONEY ("money", Integer.class),
    FLIPCOUNT ("flipCount", Integer.class);

    private final String name;
    private final Class<?> type;
    private Field(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public Class<?> getType(){
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String SQL(String n) {
        if (this.type == String.class) {
            return "'" + n + "'";
        } else {
            return n;
        }
    }
}
