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
    Controller controller;

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

            controller = new Controller(this);

            Instruct line;
            boolean cont = true;
            while ((line = receiveInstruct()) != null && cont) { //communication logic
                switch(line) {
                    case SUCCESSFUL_CONNECTION:
                        System.out.println("Successful connection with server!");
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

    private void sendInstruct(Instruct in) {
        printWriter.println(in);
        printWriter.flush();
    }

    private Instruct receiveInstruct() {
        try {
            String retval = bufferedReader.readLine();
            return Instruct.valueOf(retval);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean signinreq(String username, String password) {
        return false;
    }
}