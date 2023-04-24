import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (IOException | InterruptedException e) {
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

        thread.start();
    }

    public void checkConnectionsStatus() {
        //System.out.println(connections.size());
        connections.removeIf(conn -> !conn.getThread().isAlive());
    }

    public void updateAllLeaderboards() {
        for (Connection connection : connections) {
            connection.getSocketThread().sendLeaderboard();
        }
    }
}