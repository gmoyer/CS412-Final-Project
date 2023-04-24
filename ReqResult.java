public enum ReqResult {
    DEFAULT (false, "Error, please report"),
    CONN_FAIL (false, "Request timed out. Please try again."),
    BAD_AUTH (false, "The username and password do not match."),
    SUCCESS (true),
    PASS_DO_NOT_MATCH(false, "Passwords must match."),
    NON_UNIQUE_USERNAME(false, "Username must be unique."),
    BAD_REQUEST(false, "The request is invalid"), //used for when a client sends data it should not be sending
    NOT_ENOUGH_MONEY(false, "Not enough money to make the bet");

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
