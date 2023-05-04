public enum ReqResult {
    SUCCESS (true),
    DEFAULT (false, "Unexpected error. Please report"),
    CONN_FAIL (false, "Request timed out. Please try again."),
    BAD_AUTH (false, "The username and password do not match."),
    PASS_DO_NOT_MATCH(false, "Passwords must match."),
    NON_UNIQUE_USERNAME(false, "Username must be unique."),
    BAD_CHARACTERS (false, "Invalid characters. Please only use alphanumeric characters."),
    LONG_USERNAME (false, "Max username: 10 characters."),
    INCOMPLETE (false, "Please fill out all fields"),
    LONG_NAME (false, "Max name: 15 characters."),
    BAD_REQUEST(false, "The request is invalid."), //used for when a client sends data it should not be sending
    ALREADY_ACTIVE(false, "A user is already signed into this account."),
    NOT_ENOUGH_MONEY(false, "Not enough money to make the bet.");

    private final boolean success;
    private final String reqMessage;

    private ReqResult(Boolean success, String reqMessage) {
        this.reqMessage = reqMessage;
        this.success = success;
    }
    private ReqResult(Boolean success) {
        this.success = success;
        this.reqMessage = "";
    }

    public String getMessage() {
        return reqMessage;
    }

    public boolean isSuccessful() {
        return success;
    }
}
