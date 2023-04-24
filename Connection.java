public class Connection {
    private SocketThread socketThread;
    private Thread thread;

    public Connection(SocketThread socketThread, Thread thread) {
        this.socketThread = socketThread;
        this.thread = thread;
    }

    public SocketThread getSocketThread() {
        return socketThread;
    }
    public Thread getThread() {
        return thread;
    }
}
