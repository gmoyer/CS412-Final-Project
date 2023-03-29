import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    Socket socket;
    PrintWriter printWriter;
    BufferedReader bufferedReader;

    ObjectOutputStream objectWriter;
    ObjectInputStream objectReader;

    public static void main(String args[]) {
        (new Client()).go();
    }

    public void go() {

        //server connection
        try {
            socket = new Socket("127.0.0.1", 5000);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());

            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectReader = new ObjectInputStream(socket.getInputStream());

            sendInstruct(Instruct.SUCCESSFUL_CONNECTION);

            Instruct line;
            while ((line = receiveInstruct()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInstruct(Instruct in) {
        printWriter.println(in);
        printWriter.flush();
    }

    public Instruct receiveInstruct() {
        try {
            String retval = bufferedReader.readLine();
            return Instruct.valueOf(retval);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}