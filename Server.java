import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ServerSocket serverSocket;
    ArrayList<Connection> connections;
    Thread acceptConnections;
    Database database;
    public static void main(String args[]) {
        (new Server()).go();
    }

    public void go() {
        try {
            serverSocket = new ServerSocket(5000);
            connections = new ArrayList<Connection>();
            database = Database.getInstance();

            database.signoutAll();

            Thread acceptConnections = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            System.out.println("SERVER: waiting for client...");
                            addConnection(serverSocket.accept());
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            acceptConnections.start();

            while (true) {
                checkConnectionsStatus();
                Util.sleep(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addConnection(Socket conn) {
        System.out.println("SERVER: Recieved client");
        
        //thread the connnection
        SocketThread socketThread = new SocketThread(conn, this);
        Thread thread = new Thread(socketThread);

        Connection connection = new Connection(socketThread, thread);
        connections.add(connection);

        connection.getThread().start();
    }

    public void checkConnectionsStatus() {
        //Find connections to remove

        connections.removeIf(conn -> !conn.getThread().isAlive());
        /*
        ArrayList<Connection> toRemove = new ArrayList<Connection>();
        for (Connection conn : connections) {
            System.out.println(conn.getThread().isAlive());
            if (!conn.getThread().isAlive())
                toRemove.add(conn);
        }

        //remove them
        for (Connection conn : toRemove) {
            connections.remove(conn);
        }
        */
    }

    public void updateAllLeaderboards() {
        for (Connection connection : connections) {
            connection.getSocketThread().sendLeaderboard();
        }
    }
}