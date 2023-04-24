import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public abstract class DataSender {
    protected Socket socket;
    protected ObjectOutputStream dataWriter;
    protected ObjectInputStream dataReader;
    
    protected Thread commThread;

    protected Object mutex;
    protected HashMap<Instruct, Dataflow> dataReceived;

    protected boolean initialized = false;

    protected final int allowedDelay = 10000; //10 seconds

    public DataSender(Socket conn) {
        init(conn);
    }
    public DataSender() {
        initialized = false;
    }

    public void init(Socket conn) {
        if (!initialized) {
            try {
                socket = conn;
                dataWriter = new ObjectOutputStream(socket.getOutputStream());
                dataReader = new ObjectInputStream(socket.getInputStream());
                initialized = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            dataReceived = new HashMap<Instruct, Dataflow>();
            mutex = new Object();
        }
    }

    protected void sendData(Dataflow in) {
        if (initialized) {
            try {
                dataWriter.writeObject(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: datasender not initialized");
        }
    }

    //WARNING: must be run through a thread
    protected Dataflow receiveData() {
        if (initialized) {
            try {
                Dataflow df = (Dataflow)dataReader.readObject();
                synchronized(mutex) {
                    dataReceived.put(df.getInstruct(), df);
                }
                return df;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("Error: datasender not initialized");
            return null;
        }
    }

    protected Dataflow query(Dataflow df, Instruct i) {

        dataReceived.put(i, null);
        sendData(df);

        long sentTimeStamp = System.currentTimeMillis();

        Dataflow receivedData = null;
        while (receivedData == null) {
            synchronized(mutex) {
                receivedData = dataReceived.get(i);
            }
            if (System.currentTimeMillis() > sentTimeStamp + allowedDelay)
                return null;
        }
        return receivedData;
    }
}