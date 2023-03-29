import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

//server side!!
public class SocketThread implements Runnable {
    Socket conn; //server side socket
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    ObjectOutputStream objectWriter;
    ObjectInputStream objectReader;

    public SocketThread(Socket c) {
        conn = c;
    }

    @Override
    public void run() {
        try {
            System.out.println("new server thread to handle new client");
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            printWriter = new PrintWriter(conn.getOutputStream());

            objectWriter = new ObjectOutputStream(conn.getOutputStream());
            objectReader = new ObjectInputStream(conn.getInputStream());

            Instruct line;
            boolean cont = true;
            while ((line = receiveInstruct()) != null && cont) {
                switch(line) {
                    case SUCCESSFUL_CONNECTION:
                        System.out.println("Successful connection with client!");
                        break;
                    case QUIT:
                        cont = false;
                        break;
                    default:
                        System.out.println("Something unexpected happened");
                        break;
                }
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
            return null;
        }
    }
}