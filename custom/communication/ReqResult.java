public enum ReqResult {
    CONN_FAIL (false, "Request timed out. Please try again."),
    BAD_AUTH (false, "The username and password do not match."),
    GOOD_AUTH (true, "Login success!"),
    PASS_DO_NOT_MATCH(false, "Passwords must match."),
    NON_UNIQUE_USERNAME(false, "Username must be unique.");

    private final boolean success;
    private final String reqMessage;

    private ReqResult(Boolean success, String reqMessage) {
        this.reqMessage = reqMessage;
        this.success = success;
    }

    public String getMessage() {
        return reqMessage;
    }

    public boolean isSuccessful() {
        return success;
    }
}
