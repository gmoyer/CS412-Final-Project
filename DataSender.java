import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class DataSender {
    protected ObjectOutputStream objectWriter;
    protected ObjectInputStream objectReader;
    protected final int allowedDelay = 10000; //10 seconds

    protected void sendData(Dataflow in) {
        try {
            objectWriter.writeObject(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Dataflow receiveData() {
        try {
            return (Dataflow)objectReader.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}