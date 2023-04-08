import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//server side!!
//maintains connection with client and account
public class SocketThread extends DataSender implements Runnable {
    Socket conn; //server side socket
    Server server;

    AccountManager accountManager;

    public SocketThread(Socket c, Server s) {
        super();
        conn = c;
        server = s;
        accountManager = AccountManager.getInstance();
    }

    @Override
    public void run() {
        try {
            System.out.println("new server thread to handle new client");

            objectWriter = new ObjectOutputStream(conn.getOutputStream());
            objectReader = new ObjectInputStream(conn.getInputStream());

            sendData(new Dataflow(Instruct.SUCCESSFUL_CONNECTION));

            Dataflow line;
            boolean cont = true;
            while ((line = receiveData()) != null && cont) { //communication logic
                switch(line.getInstruct()) {
                    case SUCCESSFUL_CONNECTION:
                        System.out.println("Successful connection with client!");
                        break;
                    case QUIT:
                        cont = false;
                        break;
                    case SIGNIN_ATTEMPT:
                        signinAttempt((String)line.getNext(), (String)line.getNext());
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


    public void signinAttempt(String username, String password) {
        Dataflow df = new Dataflow(Instruct.AUTH_RESULT);

        df.add(ReqResult.BAD_AUTH);

        sendData(df);
    }

}