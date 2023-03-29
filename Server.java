import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Server {
    public final static int startingMoney = 50;

    ServerSocket serverSocket;
    ArrayList<Thread> connections;
    Thread acceptConnections;
    Database database;
    public static void main(String args[]) {
        (new Server()).go();
    }

    public void go() {
        try {

            serverSocket = new ServerSocket(5000);
            connections = new ArrayList<Thread>();
            database = new Database();

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
        Thread thread = new Thread(new SocketThread(conn));
        connections.add(thread);

        thread.start();
    }

    public void checkConnectionsStatus() {
        //System.out.println(connections.size());
        connections.removeIf(conn -> !conn.isAlive());
    }
}