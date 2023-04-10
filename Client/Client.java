import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client extends DataSender {
    Socket socket;
    Controller controller;

    boolean waitingForResponse;
    long sentTimeStamp;
    Dataflow response;

    public Client(Controller c) {
        super();
        controller = c;
        go();
    }

    public void go() {

        //server connection
        try {
            socket = new Socket("127.0.0.1", 5000);

            objectWriter = new ObjectOutputStream(socket.getOutputStream());
            objectReader = new ObjectInputStream(socket.getInputStream());

            sendData(new Dataflow(Instruct.SUCCESSFUL_CONNECTION));

            Dataflow data;
            boolean cont = true;
            while ((data = receiveData()) != null && cont) { //communication logic
                System.out.println("New Data: " + data.getInstruct().toString());
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
                        System.out.println("Set waitingForResponse to false");
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

        waitingForResponse = true;
        sendData(df);


        sentTimeStamp = System.currentTimeMillis();

        boolean waiting = true;
        while (waiting) {
            waiting = waitingForResponse;
            if (System.currentTimeMillis() > sentTimeStamp + allowedDelay)
                return ReqResult.CONN_FAIL;
        }

        return (ReqResult)response.getNext();
    }

    public ReqResult signupreq(String name, String username, String password, String confPassword) {
        Dataflow df = new Dataflow(Instruct.SIGNUP_ATTEMPT);
        df.add(name);
        df.add(username);
        df.add(password);
        df.add(confPassword);

        waitingForResponse = true;
        sendData(df);

        

        sentTimeStamp = System.currentTimeMillis();

        boolean waiting = true;
        while (waiting) {
            waiting = waitingForResponse;
            if (System.currentTimeMillis() > sentTimeStamp + allowedDelay)
                return ReqResult.CONN_FAIL;
            //System.out.println("waiting for response is " + waitingForResponse);
        }

        return (ReqResult)response.getNext();
    }
}