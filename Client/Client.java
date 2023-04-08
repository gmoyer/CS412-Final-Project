import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.lang.*;


public class Client extends DataSender {
    Socket socket;
    Controller controller;

    boolean waitingForResponse;
    long sentTimeStamp;
    Dataflow response;

    public static void main(String args[]) {
        (new Client()).go();
    }

    public Client() {
        super();
        waitingForResponse = false;
    }

    public void go() {

        //server connection
        try {
            socket = new Socket("127.0.0.1", 5000);

            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectReader = new ObjectInputStream(socket.getInputStream());

            sendData(new Dataflow(Instruct.SUCCESSFUL_CONNECTION));

            controller = new Controller(this);

            Dataflow data;
            boolean cont = true;
            while ((data = receiveData()) != null && cont) { //communication logic
                switch(data.getInstruct()) {
                    case SUCCESSFUL_CONNECTION:
                        System.out.println("Successful connection with server!");
                        break;
                    case QUIT:
                        cont = false;
                        break;
                    case AUTH_RESULT:
                        response = data;
                        waitingForResponse = false;
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

    public ReqResult signinreq(String username, String password) {
        Dataflow df = new Dataflow(Instruct.SIGNIN_ATTEMPT);
        df.add(username);
        df.add(password);
        sendData(df);

        waitingForResponse = true;

        sentTimeStamp = System.currentTimeMillis();

        while (waitingForResponse) {
            if (System.currentTimeMillis() > sentTimeStamp + allowedDelay)
                return ReqResult.CONN_FAIL;
        }

        return (ReqResult)response.getNext();
    }
}