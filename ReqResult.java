public enum ReqResult {
    CONN_FAIL (false, "Failed to send message to server. Please try again."),
    BAD_AUTH (false, "The username and password do not match.");

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
